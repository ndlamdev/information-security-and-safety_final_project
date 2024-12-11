package com.lamnguyen.mat_kinh_kimi.domain.dto;

public class UserManage {
    private Integer id, role;
    private String avatar, fullName, sex, email, verify;
    private long countOrder, sumPrice;
    private boolean lock;

    public boolean isLock() {
        return lock;
    }

    public void setLock(boolean lock) {
        this.lock = lock;
    }

    public UserManage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public long getCountOrder() {
        return countOrder;
    }

    public void setCountOrder(long countOrder) {
        this.countOrder = countOrder;
    }

    public long getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(long sumPrice) {
        this.sumPrice = sumPrice;
    }

    @Override
    public String toString() {
        return "UserManage{" +
                "id=" + id +
                ", role=" + role +
                ", avatar='" + avatar + '\'' +
                ", fullName='" + fullName + '\'' +
                ", sex='" + sex + '\'' +
                ", email='" + email + '\'' +
                ", verify='" + verify + '\'' +
                ", countOrder=" + countOrder +
                ", sumPrice=" + sumPrice +
                '}';
    }
}
