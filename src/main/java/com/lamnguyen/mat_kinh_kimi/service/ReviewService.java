package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.model.Product;
import com.lamnguyen.mat_kinh_kimi.model.Review;
import com.lamnguyen.mat_kinh_kimi.model.ReviewImage;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.repository.impl.ReviewRepositoryImpl;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.Map.Entry;

public class ReviewService {
    private static ReviewService instance;
    private final ReviewRepositoryImpl REVIEW_REPOSITORY;

    private ReviewService() {
        REVIEW_REPOSITORY = ReviewRepositoryImpl.getInstance();
    }

    public static ReviewService getInstance() {
        return (instance = instance == null ? new ReviewService() : instance);
    }

    public Map<Integer, InfReview> getInfReview(List<Product> products) {
        Map<Integer, List<Integer>> reviews = REVIEW_REPOSITORY.getInfReview(products);
        Map<Integer, InfReview> result = new HashMap<>();
        for (Entry<Integer, List<Integer>> entry : reviews.entrySet()) {
            int averageStarNumber = averageStarNumber(entry.getValue());
            result.put(entry.getKey(), new InfReview(averageStarNumber, entry.getValue().size()));
        }

        return result;
    }

    public int averageStarNumber(List<Integer> stars) {
        int sum = 0, size = stars.size();
        if (size == 0) return 5;
        double residual;
        for (Integer star : stars) {
            sum += star;
        }

        residual = sum / (double) size - (double) sum / size;
        return residual >= 0.5 ? sum / size + 1 : sum / size;
    }

    public List<Review> getReviews(int productId) {
        List<Review> reviews = REVIEW_REPOSITORY.getReviews(productId);
        setUser(reviews);
        setImage(reviews);
        return reviews;
    }

    private void setUser(List<Review> reviews) {
        UserService userService = UserService.getInstance();
        Map<Integer, User> mapUsers = userService.getUserForReviewProduct(reviews);
        for (Review review : reviews) {
            review.setUser(mapUsers.get(review.getId()));
        }
    }

    private void setImage(List<Review> reviews) {
        ReviewImageService reviewImageService = ReviewImageService.getInstance();
        Map<Integer, List<String>> mapReviewImage = reviewImageService.mapReviewImage(reviews);
        for (Review review : reviews) {
            review.setImages(mapReviewImage.get(review.getId()));
        }
    }

    public int insertTempReview(int userId, int productId) {
        ReviewRepositoryImpl reviewDAO = REVIEW_REPOSITORY;
        int reviewId = reviewDAO.getReviewId(userId, productId);
        if (reviewId != -1) return reviewId;
        while (true) {
            reviewId = reviewDAO.nextReviewId();
            Review review = new Review();
            review.setId(reviewId);
            review.setUserId(userId);
            review.setProductId(productId);
            if (reviewDAO.insertTempReview(review) != 0) break;
        }

        return reviewId;
    }

    public void removeReviewTemp(int reviewId) {
        REVIEW_REPOSITORY.remove(reviewId);
    }

    public void removeReview(int reviewId) {
        ReviewImageService service = ReviewImageService.getInstance();
        service.removeImageReviews(reviewId);
        REVIEW_REPOSITORY.remove(reviewId);
    }

    public void update(int reviewId, int star, String comment) {
        REVIEW_REPOSITORY.update(reviewId, star, comment);
    }

    public Review getReview(int userId, int productId) {
        Review review = REVIEW_REPOSITORY.getReview(userId, productId);
        if (review == null) return null;
        List<ReviewImage> reviewImages = ReviewImageService.getInstance().getReviewImage(review.getId());
        review.setReviewImages(reviewImages);
        return review;
    }
}

@Setter
@Getter
class InfReview {
    private int starNumber, totalReview;

    public InfReview(int starNumber, int totalReview) {
        this.starNumber = starNumber;
        this.totalReview = totalReview;
    }

    public InfReview() {
    }
}
