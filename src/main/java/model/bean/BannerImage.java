package model.bean;

public class BannerImage {
    private Integer id;
    private String description, urlImage;

    public BannerImage() {
    }

    public BannerImage(Integer id, String description, String urlImage) {
        this.id = id;
        this.description = description;
        this.urlImage = urlImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    @Override
    public String toString() {
        return "BannerImage{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", urlImage='" + urlImage + '\'' +
                '}';
    }
}
