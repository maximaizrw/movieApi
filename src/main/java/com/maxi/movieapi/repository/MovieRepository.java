package com.maxi.movieapi.repository;

import com.maxi.movieapi.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para la entidad {@link Movie}.
 * Proporciona métodos para acceder y manipular datos de películas en la base de datos.
 * Extiende {@link JpaRepository} para obtener métodos CRUD básicos y de paginación.
 *
 * @author Maxi
 * @version 1.0
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
