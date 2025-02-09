package com.maxi.movieapi.service.impl;

import com.maxi.movieapi.service.FileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileServiceImpl implements FileService {

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //Obtener el nombre del archivo
        String fileName = file.getOriginalFilename();

        //Obtener la ruta del archivo
        String filePath = path + File.separator + fileName;

        //Crear el objeto archivo
        File f = new File(path);

        //Si el directorio no existe, se crea
        if (!f.exists()) {
            f.mkdirs();
        }

        //Copiar el archivo al directorio
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Override
    public InputStream getResourceFile(String path, String filename) throws FileNotFoundException {
        try {
            String filepath = path + File.separator + filename;
            return new FileInputStream(filepath);
        } catch (IOException e) {
            throw new FileNotFoundException("No se encontró el archivo");
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Override
    public void deleteFile(String path, String filename) throws IOException {
        Path filepath = Paths.get(path, filename);

        if (Files.exists(filepath)) {
            Files.delete(filepath);
        } else {
            throw new FileNotFoundException("No se encontró el archivo");
        }
    }
}
