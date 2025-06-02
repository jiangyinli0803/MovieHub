package org.example.backend.model;


import java.util.List;

public record MovieListResponse (
        int page,
        List<MovieDto> results,
        int total_pages,
        int total_results
){
}
