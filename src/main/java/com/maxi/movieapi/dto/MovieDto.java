package com.maxi.movieapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MovieDto {
    private Long id;

    @NotBlank(message = "Title is mandatory")
    private String title;

    @NotBlank(message = "Director is mandatory")
    private String director;

    @NotBlank(message = "Studio is mandatory")
    private String studio;

    private Set<String> movieCast;

    @NotNull(message = "Release Year is mandatory")
    private Integer releaseYear;

    @NotBlank(message = "Poster is mandatory")
    private String poster;

    @NotBlank(message = "Poster Url is mandatory")
    private String posterUrl;

}
