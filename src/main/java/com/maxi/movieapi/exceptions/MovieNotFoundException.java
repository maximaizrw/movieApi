package com.maxi.movieapi.exceptions;

public class MovieNotFoundException extends RuntimeException {
    public MovieNotFoundException(String message) {
      super(message);
    }
}