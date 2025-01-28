package com.maxi.movieapi.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.maxi.movieapi.service.FileService;

import java.io.IOException;
import java.io.InputStream;

// Marca la clase como un controlador REST para manejar solicitudes HTTP relacionadas con archivos.
@RestController
@RequestMapping("/file") // Define la ruta base "/file" para los endpoints de este controlador.
public class FileController {

    // Servicio para manejar operaciones relacionadas con archivos.
    private final FileService FileService;

    // Constructor que inyecta la dependencia de FileService.
    public FileController(FileService FileService) {
        this.FileService = FileService;
    }

    // Inyecta el valor de la propiedad 'project.poster' desde application.properties o application.yml.
    @Value("${project.poster}")
    private String path;

    /**
     * Endpoint para manejar la carga de archivos.
     * @param file Archivo recibido como parte de la solicitud.
     * @return Respuesta HTTP indicando si la carga fue exitosa o no.
     * @throws IOException Si ocurre un error al guardar el archivo.
     */
    @PostMapping("/upload") // Define que este método responderá a solicitudes POST en "/file/upload".
    public ResponseEntity<?> uploadFileHandler(@RequestPart MultipartFile file) throws IOException {
        // Llama al servicio para guardar el archivo en el sistema de archivos.
        String uploadFileName = FileService.uploadFile(path, file);
        // Retorna una respuesta indicando éxito y el nombre del archivo cargado.
        return ResponseEntity.ok("File uploaded successfully: " + uploadFileName);
    }

    /**
     * Endpoint para servir un archivo desde el servidor al cliente.
     * @param fileName Nombre del archivo a ser servido.
     * @param response Objeto de respuesta HTTP para enviar el archivo al cliente.
     * @throws IOException Si ocurre un error al leer o enviar el archivo.
     */
    @GetMapping("/{fileName}") // Define que este método responderá a solicitudes GET en "/file/{fileName}".
    public void serverFileHandler(@PathVariable String fileName, HttpServletResponse response) throws IOException {
        // Obtiene el archivo solicitado como un flujo de entrada (InputStream).
        InputStream resourceFile = FileService.getResourceFile(path, fileName);
        // Establece el tipo de contenido de la respuesta como una imagen PNG.
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        // Copia el contenido del archivo al flujo de salida de la respuesta.
        StreamUtils.copy(resourceFile, response.getOutputStream());
    }
}
