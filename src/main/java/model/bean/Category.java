package model.bean;

public class Category {
    private Integer id, categoryGroupId;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryGroupId() {
        return categoryGroupId;
    }

    public void setCategoryGroupId(Integer categoryGroupId) {
        this.categoryGroupId = categoryGroupId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryGroupId=" + categoryGroupId +
                ", name='" + name + '\'' +
                '}';
    }
}
