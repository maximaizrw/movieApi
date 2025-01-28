package com.maxi.movieapi.dto;

import java.util.List;

public record MoviePageResponseDto(List<MovieDto> movies, int pageNumber, long pageSize, int totalElements, int totalPages, boolean isLast) {

}
