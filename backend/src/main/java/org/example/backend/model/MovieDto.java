package org.example.backend.model;

import java.util.List;

public record MovieDto(
        long id,
        String title,
        String original_language,
        String release_date,
        String overview,
        double vote_average,
        int vote_count,
        String poster_path,
        List<Integer> genre_ids
) {
}

/*
* "results": [
    {
      "adult": false,
      "backdrop_path": "/2Nti3gYAX513wvhp8IiLL6ZDyOm.jpg",
      "genre_ids": [
        10751,
        35,
        12,
        14
      ],
      "id": 950387,
      "original_language": "en",
      "original_title": "A Minecraft Movie",
      "overview": "Four misfits find themselves struggling with ordinary problems when they are suddenly pulled through a mysterious portal into the Overworld: a bizarre, cubic wonderland that thrives on imagination. To get back home, they'll have to master this world while embarking on a magical quest with an unexpected, expert crafter, Steve.",
      "popularity": 634.0126,
      "poster_path": "/yFHHfHcUgGAxziP1C3lLt0q2T4s.jpg",
      "release_date": "2025-03-31",
      "title": "A Minecraft Movie",
      "video": false,
      "vote_average": 6.511,
      "vote_count": 1471
    },
* */