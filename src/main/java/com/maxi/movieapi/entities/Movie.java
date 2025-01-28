package com.maxi.movieapi.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    @NotBlank(message = "Title is mandatory")
    @Size(max = 100)
    private String title;

    @Column(nullable = false)
    @NotBlank(message = "Director is mandatory")
    private String director;

    @Column(nullable = false)
    @NotBlank(message = "Studio is mandatory")
    private String studio;

    @ElementCollection
    @CollectionTable(name = "movie_cast")
    private Set<String> movieCast;

    @Column(nullable = false)
    @NotNull(message = "Release Year is mandatory")
    @Min(value = 1888, message = "First movie ever was in 1888")
    private Integer releaseYear;

    @Column(nullable = false)
    @NotBlank(message = "Poster is mandatory")
    private String poster;
}