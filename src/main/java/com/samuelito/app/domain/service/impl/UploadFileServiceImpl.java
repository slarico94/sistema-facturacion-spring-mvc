package com.samuelito.app.domain.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.samuelito.app.domain.service.IUploadFileService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UploadFileServiceImpl implements IUploadFileService {

	private static final String UPLOADS_FOLDER = "uploads";

	@Override
	public Resource load(String filename) throws MalformedURLException {
		Path pathFoto = getPath(filename);
		log.info("Path: {}", pathFoto);
		Resource res = new UrlResource(pathFoto.toUri());
		if (!res.exists() || !res.isReadable()) {
			throw new RuntimeException("No se puede cargar la imagen: " + pathFoto.toString());
		}
		return res;
	}

	@Override
	public String copy(MultipartFile file) throws IOException {
		// String rootPath = "/tmp/uploads";
		String uniqueFilename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		Path rootPath = getPath(uniqueFilename);
		log.info("rootPath: {}", rootPath);
		// byte[] bytes = foto.getBytes();
		// Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
		// Files.write(rutaCompleta, bytes);
		// Forma alternativa de guardar archivos con files.copy
		Files.copy(file.getInputStream(), rootPath);
		return uniqueFilename;
	}

	@Override
	public boolean delete(String filename) {
		Path rootPath = getPath(filename);
		File archivo = rootPath.toFile();
		if (archivo.exists() && archivo.canRead()) {
			if (archivo.delete()) {
				return true;
			}
		}
		return false;
	}

	public Path getPath(String filename) {
		return Paths.get(UPLOADS_FOLDER).resolve(filename).toAbsolutePath();
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(Paths.get(UPLOADS_FOLDER).toFile());

	}

	@Override
	public void init() throws IOException {
		Files.createDirectories(Paths.get(UPLOADS_FOLDER));
		
	}

}
