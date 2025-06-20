package org.example.backend.controller;

import org.example.backend.model.review.ReviewInput;
import org.example.backend.model.review.ReviewRequest;
import org.example.backend.model.review.ReviewResponseDto;
import org.example.backend.security.UserPrincipal;
import org.example.backend.service.UserReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class UserReviewController {
    @Autowired
    private UserReviewService userReviewService;


    @PostMapping("/add")
    public ReviewResponseDto creatReview(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestBody ReviewRequest request) {
        return userReviewService.creatReview(userPrincipal, request);

    }

    @GetMapping("/{tmdbId}")
    public List<ReviewResponseDto> getAllReviewsForMovie(@PathVariable long tmdbId) {
        return userReviewService.getReviewsByTmdbId(tmdbId);
    }
}
