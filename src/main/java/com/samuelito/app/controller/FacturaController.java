package com.samuelito.app.controller;

import java.util.List;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samuelito.app.domain.Cliente;
import com.samuelito.app.domain.Factura;
import com.samuelito.app.domain.ItemFactura;
import com.samuelito.app.domain.Producto;
import com.samuelito.app.domain.service.IClienteService;
import com.samuelito.app.domain.service.IFacturaService;
import com.samuelito.app.domain.service.IProductoService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
@Slf4j
public class FacturaController {

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IProductoService productoService;

	@Autowired
	private IFacturaService facturaService;

	@GetMapping("/ver/{id}")
	public String ver(@PathVariable("id") Long idFactura, Model model, RedirectAttributes flash) {
		Factura factura = facturaService.findFacturaById(idFactura);
		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/clientes/listar";
		}
		model.addAttribute("titulo", "Factura: ".concat(factura.getDescripcion()));
		model.addAttribute("factura", factura);
		return "factura/ver";
	}

	@GetMapping("/form/{idCliente}")
	public String crear(@PathVariable Long idCliente, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.getClienteById(idCliente);
		if (cliente == null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/clientes/listar";
		}
		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute("factura", factura);
		model.addAttribute("titulo", "Crear factura");
		return "factura/form";
	}

	@GetMapping(value = "/cargar-productos", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Producto> cargarProductos(@RequestParam(name = "term") String termino) {
		List<Producto> response = productoService.findByNombre(termino);
		return response;
	}

	@PostMapping("/form")
	public String guardar(@Valid Factura factura, BindingResult result, Model model,
			@RequestParam(name = "item_id[]", required = false) Long[] itemId,
			@RequestParam(name = "cantidad[]", required = false) Integer[] cantidad, RedirectAttributes flash,
			SessionStatus status) {
		if (result.hasErrors()) {
			model.addAttribute("titulo", "Crear factura");
			return "factura/form";
		}
		if (itemId == null || itemId.length == 0) {
			model.addAttribute("titulo", "Crear factura");
			model.addAttribute("error", "Error: la factura debe de contener lineas!");
			return "factura/form";
		}
		IntStream.range(0, itemId.length).forEach(i -> {
			Producto producto = productoService.findProductoById(itemId[i]);
			ItemFactura linea = new ItemFactura();
			linea.setCantidad(cantidad[i]);
			linea.setProducto(producto);
			factura.addItemFactura(linea);
			log.info("ID: {}, cantidad: {}", itemId[i], cantidad[i]);
		});
		facturaService.saveFactura(factura);
		status.setComplete();
		flash.addFlashAttribute("success", "Factura creada con éxito");
		return "redirect:/clientes/ver/" + factura.getCliente().getIdCliente();
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable("id") Long idFactura, Model model, RedirectAttributes flash) {
		Factura factura = facturaService.findFacturaById(idFactura);
		if (factura == null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos");
			return "redirect:/clientes/listar";
		}
		facturaService.deleteFacturaById(idFactura);
		flash.addFlashAttribute("success", "Factura eliminada con exito");
		return "redirect:/clientes/ver/" + factura.getCliente().getIdCliente();
	}
}
