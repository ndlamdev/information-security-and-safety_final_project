package model.service;

import model.DAO.ReviewDAO;
import model.bean.*;

import java.util.*;
import java.util.Map.Entry;

public class ReviewService {
    private static ReviewService instance;

    public static ReviewService getInstance() {
        return instance == null ? new ReviewService() : instance;
    }

    public Map<Integer, InfReview> getInfReview(List<Product> products) {
        ReviewDAO reviewDAO = ReviewDAO.getInstance();
        Map<Integer, List<Integer>> reviews = reviewDAO.getInfReview(products);
        Map<Integer, InfReview> result = new HashMap<Integer, InfReview>();
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

        residual = sum / (double) size - sum / size;
        return residual >= 0.5 ? sum / size + 1 : sum / size;
    }

    public List<Review> getReviews(int productId) {
        List<Review> reviews = ReviewDAO.getInstance().getReviews(productId);
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
        ReviewDAO reviewDAO = ReviewDAO.getInstance();
        int reviewId = reviewDAO.getReviewId(userId, productId);
        if(reviewId != -1) return reviewId;
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
        ReviewDAO reviewDAO = new ReviewDAO();
        reviewDAO.remove(reviewId);
    }

    public void removeReview(int reviewId) {
        ReviewDAO reviewDAO = ReviewDAO.getInstance();
        ReviewImageService service = ReviewImageService.getInstance();
        service.removeImageReviews(reviewId);
        reviewDAO.remove(reviewId);
    }

    public void update(int reviewId, int star, String comment) {
        ReviewDAO.getInstance().update(reviewId, star, comment);
    }

    public Review getReview(int userId, int productId) {
        Review review = ReviewDAO.getInstance().getReview(userId, productId);
        if(review == null) return null;
        List<ReviewImage> reviewImages = ReviewImageService.getInstance().getReviewImage(review.getId());
        review.setReviewImages(reviewImages);
        return review;
    }
}

class InfReview {
    private int starNumber, totalReview;

    public InfReview(int starNumber, int totalReview) {
        this.starNumber = starNumber;
        this.totalReview = totalReview;
    }

    public InfReview() {
    }

    public int getStarNumber() {
        return starNumber;
    }

    public void setStarNumber(int starNumber) {
        this.starNumber = starNumber;
    }

    public int getTotalReview() {
        return totalReview;
    }

    public void setTotalReview(int totalReview) {
        this.totalReview = totalReview;
    }


}
