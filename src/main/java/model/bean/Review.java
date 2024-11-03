package model.bean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Review {
    private Integer id, productId, billId, userId, numberStar;
    private String comment;
    private List<String> images;
    private User user;
    private LocalDateTime date;
    private List<ReviewImage> reviewImages;

    public Review() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getNumberStar() {
        return numberStar;
    }

    public void setNumberStar(Integer numberStar) {
        this.numberStar = numberStar;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public void addImage(String image) {
        images = images == null ? new ArrayList<>() : images;
        images.add(image);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", productId=" + productId +
                ", billId=" + billId +
                ", userId=" + userId +
                ", numberOfStar=" + numberStar +
                ", comment='" + comment + '\'' +
                ", images=" + images +
                ", user=" + user +
                ", date=" + date +
                '}';
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setReviewImages(List<ReviewImage> reviewImages) {
        this.reviewImages = reviewImages;
    }

    public List<ReviewImage> getReviewImages() {
        return reviewImages;
    }
}
