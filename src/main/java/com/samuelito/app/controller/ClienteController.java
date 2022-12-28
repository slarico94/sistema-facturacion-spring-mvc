package com.samuelito.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samuelito.app.domain.Cliente;
import com.samuelito.app.domain.service.IClienteService;
import com.samuelito.app.domain.service.IUploadFileService;
import com.samuelito.app.util.paginator.PageRender;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/clientes")
@SessionAttributes("cliente")
public class ClienteController {

	@Autowired
	@Qualifier("clienteServiceImpl")
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		// binder.registerCustomEditor(LocalDateTime.class, createdAtEditor);
	}

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable Long id, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.getClienteById(id);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/clientes/listar";
		}
		model.addAttribute("cliente", cliente);
		model.addAttribute("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}

	@GetMapping("/listar")
	public String listarClientes(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 5);
		Page<Cliente> clientes = clienteService.getAllClientesPageable(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>("/clientes/listar", clientes);
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@GetMapping("/form")
	public String crear(Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("titulo", "Formulario de cliente");
		model.addAttribute("cliente", cliente);
		return "form";
	}

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

	@GetMapping("/form/{id}")
	public String editar(@PathVariable Long id, RedirectAttributes flash, Model model) {
		Cliente cliente = clienteService.getClienteById(id);
		model.addAttribute("titulo", "Editar cliente!");
		model.addAttribute("cliente", cliente);
		flash.addFlashAttribute("success", "Cliente editado con éxito");
		return "form";
	}

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

}
