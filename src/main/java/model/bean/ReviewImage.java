package model.bean;

public class ReviewImage {
    private Integer id, reviewId;
    private String urlImage;

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ReviewImage{" +
                "reviewId=" + reviewId +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}
