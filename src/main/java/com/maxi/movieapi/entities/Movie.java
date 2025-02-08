package com.maxi.movieapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

/**
 * Representa una entidad de película en el sistema.
 * Esta clase se utiliza para almacenar información sobre una película, incluyendo su título, director, estudio, reparto, año de lanzamiento y póster.
 * Está mapeada a la tabla "movies" en la base de datos.
 *
 * @author Maxi
 * @version 1.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "movies")
public class Movie {

    /**
     * Identificador único de la película.
     * Este campo es generado automáticamente por la base de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * El título de la película.
     * Este campo es obligatorio y no debe exceder los 100 caracteres.
     */
    @Column(nullable = false, length = 100)
    @NotBlank(message = "El título es obligatorio")
    @Size(max = 100, message = "El título no debe exceder los 100 caracteres")
    private String title;

    /**
     * El director de la película.
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    @NotBlank(message = "El director es obligatorio")
    private String director;

    /**
     * El estudio que produjo la película.
     * Este campo es obligatorio.
     */
    @Column(nullable = false)
    @NotBlank(message = "El estudio es obligatorio")
    private String studio;

    /**
     * El conjunto de actores o miembros del reparto de la película.
     * Este campo se almacena en una tabla separada llamada "movie_cast".
     */
    @ElementCollection
    @CollectionTable(name = "movie_cast", joinColumns = @JoinColumn(name = "movie_id"))
    @Column(name = "actor")
    private Set<String> movieCast;

    /**
     * El año de lanzamiento de la película.
     * Este campo es obligatorio y debe ser un año válido (entre 1888 y 2100).
     */
    @Column(nullable = false, name = "release_year")
    @NotNull(message = "El año de lanzamiento es obligatorio")
    @Min(value = 1888, message = "La primera película se realizó en 1888")
    @Max(value = 2100, message = "El año de lanzamiento debe ser menor a 2100")
    private Integer releaseYear;

    /**
     * La URL o ruta del póster de la película.
     * Este campo es opcional.
     */
    @Column(nullable = true)
    private String poster;
}