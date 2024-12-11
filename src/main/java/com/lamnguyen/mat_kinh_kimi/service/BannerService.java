package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.BannerRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.BannerImage;

import java.util.List;

public class BannerService {
    private static BannerService instance;
    private final BannerRepositoryImpl BANNER_REPOSITORY;

    private BannerService() {
        BANNER_REPOSITORY = BannerRepositoryImpl.getInstance();
    }

    public static BannerService getInstance() {
        return (instance = instance != null ? instance : new BannerService());
    }

    /*
        get slide show images
     */
    public List<BannerImage> getSlideShowImages() {
        return BANNER_REPOSITORY.getSlideShowImages();
    }

    public BannerImage getBannerByDescription(String descriptionBanner) {
        return BANNER_REPOSITORY.getBannerByDescription(descriptionBanner);
    }

    /*
    upload banner image
     */
    public void uploadBannerImage(BannerImage bannerImage) {
        BANNER_REPOSITORY.updateBannerImage(bannerImage);
    }

    /*
    insert slide image
     */
    public void insertSlideShowImages(BannerImage bannerImage) {
        BANNER_REPOSITORY.insertSlideImage(bannerImage);
    }

    public int nextIdOfSlide() {
        return BANNER_REPOSITORY.nextId() + 1;
    }

    public int countSlide() {
        return BANNER_REPOSITORY.countSlide();
    }

    /*
    remove slide
     */
    public void removeSlide(BannerImage bannerImage) {
        BANNER_REPOSITORY.removeSlide(bannerImage);
    }
}
