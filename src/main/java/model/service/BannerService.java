package model.service;

import model.DAO.BannnerDAO;
import model.bean.BannerImage;
import model.bean.ReviewImage;

import java.util.List;
import java.util.Map;

public class BannerService {
    private static BannerService INSTANCE;
    private BannnerDAO bannnerDAO;

    public static BannerService getInstance() { return INSTANCE != null ? INSTANCE :  new BannerService(); }

    /*
        get slide show images
     */
    public List<BannerImage> getSlideShowImages() {
        return  BannnerDAO.getInstance().getSlideShowImages();
    }
    public BannerImage getBannerByDescription(String descriptionBanner) {
        return  BannnerDAO.getInstance().getBannerByDescription(descriptionBanner);
    }
    /*
    upload banner image
     */
    public void uploadBannerImage(BannerImage bannerImage) {
        BannnerDAO.getInstance().updateBannerImage(bannerImage);
    }
    /*
    insert slide image
     */
    public void insertSlideShowImages(BannerImage bannerImage){
        BannnerDAO.getInstance().insertSlideImage(bannerImage);
    }
    public int nextIdOfSlide() {return BannnerDAO.getInstance().nextId() + 1;}
    public int countSlide(){
        return BannnerDAO.getInstance().countSlide();
    }

    /*
    remove slide
     */
    public void removeSlide(BannerImage bannerImage) {BannnerDAO.getInstance().removeSlide(bannerImage);}
}
