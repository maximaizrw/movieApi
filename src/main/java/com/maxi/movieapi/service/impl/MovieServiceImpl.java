package com.maxi.movieapi.service.impl;

import com.maxi.movieapi.dto.MovieDto;
import com.maxi.movieapi.dto.MoviePageResponseDto;
import com.maxi.movieapi.entities.Movie;
import com.maxi.movieapi.repository.MovieRepository;
import com.maxi.movieapi.service.MovieService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;
    private final FileServiceImpl fileService;

    @Value("${project.poster}")
    private String path;

    @Value("${base.url}")
    private String baseUrl;

    public MovieServiceImpl(MovieRepository movieRepository, FileServiceImpl fileService) {
        this.movieRepository = movieRepository;
        this.fileService = fileService;
    }

    @Transactional(readOnly = true)
    @Override
    public List<MovieDto> getAllMovies() {
        return movieRepository.findAll().stream().map(movie -> MovieDto.builder().
                id(movie.getId()).
                title(movie.getTitle()).
                director(movie.getDirector()).
                studio(movie.getStudio()).
                movieCast(movie.getMovieCast()).
                releaseYear(movie.getReleaseYear()).
                poster(movie.getPoster()).
                posterUrl(baseUrl + "/file/" + movie.getPoster()).
                build()).toList();
    }

    @Override
    public MoviePageResponseDto getAllMoviesByPage(int page, int size) {
        // Validación de parámetros
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
        }

        // Creación del objeto Pageable
        Pageable pageable = PageRequest.of(page, size);

        // Obtención de datos desde el repositorio
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<Movie> movies = moviePage.getContent();

        // Transformación de entidades a DTOs
        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> new MovieDto(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getDirector(),
                        movie.getStudio(),
                        movie.getMovieCast(),
                        movie.getReleaseYear(),
                        movie.getPoster(),
                        baseUrl + "/file/" + movie.getPoster()
                ))
                .toList();

        // Creación de la respuesta
        return new MoviePageResponseDto(
                movieDtos,
                moviePage.getTotalPages(),
                moviePage.getTotalElements(),
                moviePage.getSize(),
                moviePage.getNumber(),
                moviePage.isLast()
        );
    }

    @Override
    public MoviePageResponseDto getAllMoviesByPageAndSorting(int page, int size, String sortBy, String order) {
        // 1. Validar parámetros
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page must be >= 0 and size must be > 0");
        }

        // 2. Crear objeto Sort (corregido)
        Sort.Direction direction = order.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy); // Usar sortBy como campo y order como dirección

        // 3. Crear Pageable con el sort correcto
        Pageable pageable = PageRequest.of(page, size, sort);

        // 4. Obtener datos del repositorio
        Page<Movie> moviePage = movieRepository.findAll(pageable);
        List<Movie> movies = moviePage.getContent();

        // 5. Mapear a DTOs (sin cambios)
        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> new MovieDto(
                        movie.getId(),
                        movie.getTitle(),
                        movie.getDirector(),
                        movie.getStudio(),
                        movie.getMovieCast(),
                        movie.getReleaseYear(),
                        movie.getPoster(),
                        baseUrl + "/file/" + movie.getPoster()
                ))
                .toList();

        // 6. Retornar respuesta
        return new MoviePageResponseDto(
                movieDtos,
                moviePage.getTotalPages(),
                moviePage.getTotalElements(),
                moviePage.getSize(),
                moviePage.getNumber(),
                moviePage.isLast()
        );
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<MovieDto> getMovieById(Long id) {
        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isPresent()){
            Movie movie = movieOptional.get();
            String posterUrl = baseUrl + "/file/" + movie.getPoster();
            return Optional.of(new MovieDto(
                    movie.getId(),
                    movie.getTitle(),
                    movie.getDirector(),
                    movie.getStudio(),
                    movie.getMovieCast(),
                    movie.getReleaseYear(),
                    movie.getPoster(),
                    posterUrl
            ));
        } else {
            return Optional.empty();
        }
    }

    @Transactional
    @Override
    public Optional<MovieDto> addMovie(MovieDto movieDto, MultipartFile file) throws IOException {


        String uploadFileName = fileService.uploadFile(path, file);
        movieDto.setPoster(uploadFileName);

        Movie movie = new Movie(
                null,
                movieDto.getTitle(),
                movieDto.getDirector(),
                movieDto.getStudio(),
                movieDto.getMovieCast(),
                movieDto.getReleaseYear(),
                movieDto.getPoster()
        );
        Movie savedMovie = movieRepository.save(movie);

        String posterUrl = baseUrl + "/file/" + uploadFileName;

        MovieDto response = new MovieDto(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        );

        return Optional.of(response);
    }

    @Transactional
    @Override
    public Optional<MovieDto> updateMovie(Long id, MovieDto movieDto, MultipartFile file) throws IOException {

        Optional<Movie> movieOptional = movieRepository.findById(id);
        if (movieOptional.isEmpty()){
            return null;
        }

        Movie movie = movieOptional.get();
        String uploadFileName = fileService.uploadFile(path, file);
        movieDto.setPoster(uploadFileName);

        movie.setTitle(movieDto.getTitle());
        movie.setDirector(movieDto.getDirector());
        movie.setStudio(movieDto.getStudio());
        movie.setMovieCast(movieDto.getMovieCast());
        movie.setReleaseYear(movieDto.getReleaseYear());
        movie.setPoster(movieDto.getPoster());

        Movie savedMovie = movieRepository.save(movie);

        String posterUrl = baseUrl + "/file/" + uploadFileName;

        return Optional.of(new MovieDto(
                savedMovie.getId(),
                savedMovie.getTitle(),
                savedMovie.getDirector(),
                savedMovie.getStudio(),
                savedMovie.getMovieCast(),
                savedMovie.getReleaseYear(),
                savedMovie.getPoster(),
                posterUrl
        ));
    }

    @Transactional
    @Override
    public void deleteMovie(Long id) throws IOException {
        Movie movie = movieRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id)); // O una excepción personalizada
        fileService.deleteFile(path, movie.getPoster());
        movieRepository.delete(movie); // También puedes usar deleteById(id) aquí
    }



}
