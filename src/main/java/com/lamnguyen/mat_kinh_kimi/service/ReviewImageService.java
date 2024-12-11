package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.ReviewImageRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.Review;
import com.lamnguyen.mat_kinh_kimi.model.ReviewImage;

import java.util.List;
import java.util.Map;

public class ReviewImageService {
    private static ReviewImageService instance;
    private final ReviewImageRepositoryImpl REVIEW_IMAGE_REPOSITORY;

    private ReviewImageService() {
        REVIEW_IMAGE_REPOSITORY = ReviewImageRepositoryImpl.getInstance();
    }

    public static ReviewImageService getInstance() {
        return (instance = instance == null ? new ReviewImageService() : instance);
    }

    public Map<Integer, List<String>> mapReviewImage(List<Review> reviews) {
        return REVIEW_IMAGE_REPOSITORY.mapReviewImage(reviews);
    }

    public void removeImageReviews(int reviewId) {
        REVIEW_IMAGE_REPOSITORY.removeImageReviews(reviewId);
    }

    public void removeImageReview(int reviewImageId) {
        REVIEW_IMAGE_REPOSITORY.removeImageReview(reviewImageId);
    }

    public int insert(String reviewId, String subFilePath) {
        int nextId = REVIEW_IMAGE_REPOSITORY.nextId();
        while (true) {
            if (ReviewImageRepositoryImpl.getInstance().insert(nextId, reviewId, subFilePath)) return nextId;
            nextId = REVIEW_IMAGE_REPOSITORY.nextId();
        }
    }

    public List<ReviewImage> getReviewImage(Integer id) {
        return REVIEW_IMAGE_REPOSITORY.getReviewImage(id);
    }
}
