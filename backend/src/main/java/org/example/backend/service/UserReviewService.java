package org.example.backend.service;

import org.example.backend.model.Movie;
import org.example.backend.model.review.Review;
import org.example.backend.model.review.ReviewInput;
import org.example.backend.model.review.ReviewRequest;
import org.example.backend.model.review.ReviewResponseDto;
import org.example.backend.model.user.User;
import org.example.backend.repository.MovieRepository;
import org.example.backend.repository.UserRepository;
import org.example.backend.repository.UserReviewRepository;
import org.example.backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserReviewService {

    @Autowired
    private UserReviewRepository reviewRepo;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private MovieRepository movieRepo;

    public ReviewResponseDto creatReview(@AuthenticationPrincipal UserPrincipal userPrincipal, ReviewRequest request) {
        String userId = userPrincipal.getId();
        long tmdbId = request.getTmdbId();

        LocalDateTime now = LocalDateTime.now();
        Review review = Review.builder()
                .userId(userId)
                .tmdbId(tmdbId)
                .rating(request.getReviewInput().getRating())
                .title(request.getReviewInput().getTitle())
                .comment(request.getReviewInput().getComment())
                .createdAt(now)
                .updatedAt(now)
                .build();
        Review savedReview = reviewRepo.save(review);

        User user = userRepo.findById(userId).orElseThrow();
        String username = user.getUsername();

        String movieTitel = movieRepo.findByTmdbId(request.getTmdbId()).getTitle();
        String posterUrl = movieRepo.findByTmdbId(request.getTmdbId()).getPosterUrl();
        double tmdbRating = movieRepo.findByTmdbId(request.getTmdbId()).getRating();

        return new ReviewResponseDto(
                savedReview.getRating(),
                savedReview.getTitle(),
                savedReview.getComment(),
                username,
                movieTitel,
                posterUrl,
                tmdbRating,
                savedReview.getCreatedAt()
        );
    }

    public List<ReviewResponseDto> getReviewsByTmdbId(long tmdbId) {
        List<Review> reviews = reviewRepo.findAllByTmdbId(tmdbId);

        Movie movie = movieRepo.findByTmdbId(tmdbId);
        String movieTitle = movie.getTitle();
        String posterUrl = movie.getPosterUrl();
        double tmdbRating = movie.getRating();

        // 将每条评论映射为 Response DTO
        return reviews.stream().map(review -> {
            User user = userRepo.findById(review.getUserId()).orElseThrow();
            String username = user.getUsername();

            return new ReviewResponseDto(
                    review.getRating(),
                    review.getTitle(),
                    review.getComment(),
                    username,
                    movieTitle,
                    posterUrl,
                    tmdbRating,
                    review.getCreatedAt()
            );
        }).toList();
    }
}
