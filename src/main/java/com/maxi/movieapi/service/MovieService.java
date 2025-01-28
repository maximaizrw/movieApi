package com.maxi.movieapi.service;

import com.maxi.movieapi.dto.MovieDto;
import com.maxi.movieapi.dto.MoviePageResponseDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    Optional<MovieDto> addMovie(MovieDto movieDto, MultipartFile file) throws IOException;
    Optional<MovieDto> getMovieById(Long id);
    List<MovieDto> getAllMovies();
    Optional<MovieDto> updateMovie(Long id, MovieDto movieDto, MultipartFile file) throws IOException;
    void deleteMovie(Long id) throws IOException;
    MoviePageResponseDto getAllMoviesByPage(int page, int size);
    MoviePageResponseDto getAllMoviesByPageAndSorting(int page, int size, String sortBy, String order);
}