package model.service;

import model.DAO.ReviewImageDAO;
import model.bean.Review;
import model.bean.ReviewImage;

import java.util.List;
import java.util.Map;

public class ReviewImageService {
    private static ReviewImageService instance;

    public static ReviewImageService getInstance() {
        return instance == null ? new ReviewImageService() : instance;
    }

    public Map<Integer, List<String>> mapReviewImage(List<Review> reviews) {
        return ReviewImageDAO.getInstance().mapReviewImage(reviews);
    }

    public void removeImageReviews(int reviewId) {
        ReviewImageDAO.getInstance().removeImageReviews(reviewId);
    }

    public void removeImageReview(int reviewImageId) {
        ReviewImageDAO.getInstance().removeImageReview(reviewImageId);
    }

    public int insert(String reviewId, String subFilePath) {
        int nextId = ReviewImageDAO.getInstance().nextId();
        while (true) {
            if (ReviewImageDAO.getInstance().insert(nextId, reviewId, subFilePath)) return nextId;
            nextId =  ReviewImageDAO.getInstance().nextId();
        }
    }

    public List<ReviewImage> getReviewImage(Integer id) {
        return ReviewImageDAO.getInstance().getReviewImage(id);
    }
}
