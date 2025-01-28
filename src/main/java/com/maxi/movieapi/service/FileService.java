package com.maxi.movieapi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface FileService {
    // Subir archivo al servidor
    String uploadFile(String path, MultipartFile file) throws IOException;
    // Obtener archivo del servidor
    InputStream getResourceFile(String path, String filename) throws FileNotFoundException;
    // Eliminar archivo del servidor
    void deleteFile(String path, String filename) throws IOException;
}
