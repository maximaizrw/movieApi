package com.maxi.movieapi.exceptions;

public class MovieSaveException extends RuntimeException {
    public MovieSaveException(String message) {
        super(message);
    }
}
