package com.samuelito.app.controller;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.samuelito.app.domain.service.IUploadFileService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class ImageController {

	@Autowired
	private IUploadFileService uploadFileService;

	@Secured("ROLE_USER")
	@GetMapping("/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFotoCliente(@PathVariable String filename) {
		Resource res = null;
		try {
			res = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			log.error("Error de URL", e);
		}

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + res.getFilename() + "\"")
				.body(res);

	}
	
	@GetMapping({"/", ""})
	public String index() {
		return "redirect:/clientes/listar";
	}

}
