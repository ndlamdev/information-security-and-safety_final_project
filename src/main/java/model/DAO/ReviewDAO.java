package model.DAO;

import model.bean.Product;
import model.bean.Review;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReviewDAO extends DAO {

    private static ReviewDAO instance;

    public static ReviewDAO getInstance() {
        return instance == null ? new ReviewDAO() : instance;
    }

    /*
       get Map<productId, listOfStars> from class InfReview
       @param List<Product> products
       @return Map<Integer, List<Integer>>
     */
    public Map<Integer, List<Integer>> getInfReview(List<Product> products) {
        Map<Integer, List<Integer>> result = new HashMap<>();
        for (Product product : products) {
            int id = product.getId();
            List<Integer> stars = connector.withHandle(handle ->
                    handle.createQuery("SELECT r.numberStar " +
                                    "FROM reviews AS r " +
                                    "WHERE r.productId = ? " +
                                    "AND r.date IS NOT NULL;")
                            .bind(0, id)
                            .mapTo(Integer.class)
                            .list()
            );

            result.put(id, stars);
        }

        return result;
    }

    /*
        get information from Review by product id
        @param product id
        @return List<Review>
     */
    public List<Review> getReviews(int productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT r.id, r.userId, r.`comment`, r.numberStar, r.date " +
                                "FROM reviews AS r " +
                                "WHERE r.productId = ?;")
                        .bind(0, productId)
                        .mapToBean(Review.class)
                        .list()
        );
    }

    public int insertTempReview(Review review) {
        return connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO reviews(id, userId, productId) VALUES (?, ?, ?)")
                        .bind(0, review.getId())
                        .bind(1, review.getUserId())
                        .bind(2, review.getProductId())
                        .execute());
    }

    public void remove(int reviewId) {
        connector.withHandle(handle ->
                handle.createUpdate("DELETE FROM reviews WHERE id = :id")
                        .bind("id", reviewId)
                        .execute());
    }

    public int nextReviewId() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT MAX(r.id) FROM reviews AS r")
                        .mapTo(Integer.class)
                        .findFirst().orElse(0)
        ) + 1;
    }

    public void update(int reviewId, int star, String comment) {
        connector.withHandle(handle ->
                handle.createUpdate("UPDATE reviews SET " +
                        "numberStar = :star, " +
                        "`comment` = :comment, " +
                        "date = CURRENT_TIMESTAMP() " +
                        "WHERE id = :id")
                        .bind("id", reviewId)
                        .bind("comment", comment)
                        .bind("star", star)
                        .execute()
        );
    }

    public int getReviewId(int userId, int productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT id FROM reviews WHERE userId = :userId AND productId = :productId")
                        .bind("userId", userId)
                        .bind("productId", productId)
                        .mapTo(Integer.class)
                        .findFirst().orElse(-1)
        );
    }

    public Review getReview(int userId, int productId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT r.id, r.userId, r.`comment`, r.numberStar, r.date " +
                                "FROM reviews AS r " +
                                "WHERE r.userId = ? AND r.productId = ?;")
                        .bind(0, userId)
                        .bind(1, productId)
                        .mapToBean(Review.class)
                        .findFirst().orElse(null)
        );
    }
}
