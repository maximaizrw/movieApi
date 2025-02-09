package com.maxi.movieapi.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxi.movieapi.dto.MovieDto;
import com.maxi.movieapi.exceptions.MovieNotFoundException;
import com.maxi.movieapi.exceptions.MovieSaveException;
import com.maxi.movieapi.service.MovieService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/movies")
public class MovieController {

    private final MovieService movieService;
    private final ObjectMapper objectMapper;

    public MovieController(MovieService movieService, ObjectMapper objectMapper) {
        this.movieService = movieService;
        this.objectMapper = objectMapper;
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping
    public ResponseEntity<List<MovieDto>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllMovies());
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/paginate")
    public ResponseEntity<?> getAllMoviesByPage(@RequestParam int page, @RequestParam int size) {
        try {
            return ResponseEntity.ok(movieService.getAllMoviesByPage(page, size));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/all-movies-page-sort")
    public ResponseEntity<?> getAllMoviesByPageSort(@RequestParam int page, @RequestParam int size, @RequestParam String sortBy, @RequestParam String order) {
        try {
            return ResponseEntity.ok(movieService.getAllMoviesByPageAndSorting(page, size, sortBy, order));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable Long id) {
        return movieService.getMovieById(id)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new MovieNotFoundException("Movie not found with id: " + id));
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PostMapping
    public ResponseEntity<MovieDto> addMovie(@RequestPart MultipartFile file, @RequestPart @Valid String movieDto) throws IOException {
        MovieDto dto = objectMapper.readValue(movieDto, MovieDto.class);
        MovieDto savedMovie = movieService.addMovie(dto, file)
                .orElseThrow(() -> new MovieSaveException("Failed to save movie"));
        return new ResponseEntity<>(savedMovie, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @PutMapping("/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestPart MultipartFile file, @RequestPart String movieDto) throws IOException {
        MovieDto dto = objectMapper.readValue(movieDto, MovieDto.class);
        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        MovieDto updatedMovie = movieService.updateMovie(id, dto, file)
                .orElseThrow(() -> new RuntimeException("Failed to update movie"));
        return new ResponseEntity<>(updatedMovie, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('ROLE_USER')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMovie(@PathVariable Long id) throws IOException {
        try {
            movieService.deleteMovie(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e) { // Captura la excepción lanzada por el servicio
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
