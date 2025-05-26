package org.example.backend.model;

import jdk.jfr.Category;

public record Movie(
        int id,
        String title,
        double  rate,
        int year,
        String[] actors,
        String img,
        String description,
        Category category,
        String created_at

) {
}
