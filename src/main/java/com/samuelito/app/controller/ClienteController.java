package com.samuelito.app.controller;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samuelito.app.domain.Cliente;
import com.samuelito.app.domain.service.IClienteService;
import com.samuelito.app.domain.service.IUploadFileService;
import com.samuelito.app.util.paginator.PageRender;
import com.samuelito.app.view.xml.ClienteList;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/clientes")
@SessionAttributes("cliente")
@Slf4j
public class ClienteController {

	@Autowired
	@Qualifier("clienteServiceImpl")
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;
	
	@Autowired
	private MessageSource messageSource;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// binder.registerCustomEditor(LocalDateTime.class, createdAtEditor);
	}

	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findByIdClienteWithFacturaWithItemFacturaWithProducto(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/clientes/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}
	
	@GetMapping("/listar-json")
	public @ResponseBody List<Cliente> listarClientesRest() {
		return clienteService.getAllClientes();
	}
	
	@GetMapping("/listar-rest")
	public @ResponseBody ClienteList listarClientesRestXmlJson() {
		return new ClienteList(clienteService.getAllClientes());
	}

	@GetMapping("/listar")
	public String listarClientes(
			@RequestParam(name = "page", defaultValue = "0") int page, 
			Model model,
			Authentication authentication,
			HttpServletRequest request,
			Locale locale) {
		if (authentication != null) {
			log.info("Usuario autenticado: {}", authentication.getName());
		}
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			log.info("Usuario autenticado con security context holder: {}", auth.getName());
		}
		if (hasRole("ROLE_ADMIN")) {
			log.info("Hola {} tienes acceso de ADMIN", auth.getName());
		} else {
			log.info("Hola {} NO tienes acceso de ADMIN", auth.getName());
		}
		SecurityContextHolderAwareRequestWrapper securityContext = new SecurityContextHolderAwareRequestWrapper(request, "ROLE_");
		if (securityContext.isUserInRole("ADMIN")) {
			log.info("Hola {} tienes acceso de ADMIN usando SecurityContextHolderAwareRequestWrapper", auth.getName());
		} else {
			log.info("Hola {} NO tienes acceso de ADMIN usando SecurityContextHolderAwareRequestWrapper", auth.getName());
		}
		if (request.isUserInRole("ROLE_ADMIN")) {
			log.info("Hola {} tienes acceso de ADMIN usando HttpServletRequest", auth.getName());
		} else {
			log.info("Hola {} NO tienes acceso de ADMIN usando HttpServletRequest", auth.getName());
		}
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.getAllClientesPageable(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/clientes/listar", clientes);
		model.addAttribute("titulo", messageSource.getMessage("text.cliente.listar.titulo", null, locale));
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("titulo", "Formulario de cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}

	@Secured("ROLE_ADMIN")
	@PostMapping("/form")
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, RedirectAttributes flash, SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		if (!foto.isEmpty()) {
			if (cliente.getIdCliente() != null && cliente.getIdCliente() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadFileService.delete(cliente.getFoto());
			}
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			flash.addFlashAttribute("info", "Has subido correctamentes '" + uniqueFilename + "'");
			cliente.setFoto(uniqueFilename);

		}
		clienteService.saveCliente(cliente);
		status.setComplete();
		flash.addFlashAttribute("success", "Cliente creado con éxito");
		return "redirect:/clientes/listar";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id, RedirectAttributes flash, Model model) {
		Cliente cliente = clienteService.getClienteById(id);
		model.addAttribute("titulo", "Editar cliente!");
		model.addAttribute("cliente", cliente);
		flash.addFlashAttribute("success", "Cliente editado con éxito");
		return "form";
	}

	@Secured("ROLE_ADMIN")
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
		Cliente cliente = clienteService.getClienteById(id);
		clienteService.deleteClienteById(id);
		flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		if (uploadFileService.delete(cliente.getFoto())) {
			flash.addFlashAttribute("info", "Foto " + cliente.getFoto() + " eliminada con exito");
		}

		flash.addFlashAttribute("success", "Cliente eliminado con éxito");
		return "redirect:/clientes/listar";
	}
	
	private boolean hasRole(String role) {
		SecurityContext ctx = SecurityContextHolder.getContext();
		if (ctx == null) {
			return false;
		}
		Authentication auth = ctx.getAuthentication();
		if (auth == null) {
			return false;
		}
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		/*return authorities.stream().anyMatch(au -> {
			log.info("{} tu rol es: {}", auth.getName(), au.getAuthority());
			return au.getAuthority().equals(role);
		});*/
		return authorities.contains(new SimpleGrantedAuthority(role));
	}

}
