package com.maxi.movieapi.service;

import com.maxi.movieapi.dto.MovieDto;
import com.maxi.movieapi.dto.MoviePageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define los servicios relacionados con la gestión de películas.
 * Proporciona métodos para agregar, obtener, actualizar, eliminar y paginar películas.
 */
public interface MovieService {

    /**
     * Agrega una nueva película al sistema.
     *
     * @param movieDto Los datos de la película a agregar.
     * @param file     El archivo de imagen (póster) de la película.
     * @return Un {@link Optional} que contiene el DTO de la película agregada si la operación fue exitosa,
     *         o vacío si ocurrió un error.
     * @throws IOException Si ocurre un error al manejar el archivo de imagen.
     */
    Optional<MovieDto> addMovie(MovieDto movieDto, MultipartFile file) throws IOException;

    /**
     * Obtiene una película por su identificador único.
     *
     * @param id El identificador único de la película.
     * @return Un {@link Optional} que contiene el DTO de la película si se encuentra, o vacío si no.
     */
    Optional<MovieDto> getMovieById(Long id);

    /**
     * Obtiene todas las películas del sistema.
     *
     * @return Una lista de DTOs de películas.
     */
    List<MovieDto> getAllMovies();

    /**
     * Actualiza los datos de una película existente.
     *
     * @param id       El identificador único de la película a actualizar.
     * @param movieDto Los nuevos datos de la película.
     * @param file     El nuevo archivo de imagen (póster) de la película (opcional).
     * @return Un {@link Optional} que contiene el DTO de la película actualizada si la operación fue exitosa,
     *         o vacío si ocurrió un error.
     * @throws IOException Si ocurre un error al manejar el archivo de imagen.
     */
    Optional<MovieDto> updateMovie(Long id, MovieDto movieDto, MultipartFile file) throws IOException;

    /**
     * Elimina una película del sistema.
     *
     * @param id El identificador único de la película a eliminar.
     * @throws IOException Si ocurre un error al eliminar el archivo de imagen asociado.
     */
    void deleteMovie(Long id) throws IOException;

    /**
     * Obtiene una página de películas.
     *
     * @param page El número de página (comenzando desde 0).
     * @param size El tamaño de la página (número de películas por página).
     * @return Un DTO que contiene la lista de películas y la información de paginación.
     */
    MoviePageResponseDto getAllMoviesByPage(int page, int size);

    /**
     * Obtiene una página de películas con ordenamiento.
     *
     * @param page   El número de página (comenzando desde 0).
     * @param size   El tamaño de la página (número de películas por página).
     * @param sortBy El campo por el cual ordenar las películas.
     * @param order  El orden de clasificación (ascendente o descendente).
     * @return Un DTO que contiene la lista de películas y la información de paginación.
     */
    MoviePageResponseDto getAllMoviesByPageAndSorting(int page, int size, String sortBy, String order);
}