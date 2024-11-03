package model.bean;

import java.util.ArrayList;
import java.util.List;

public class Bill {
    private Integer id, userId, codeProvince, codeDistrict, codeWard;
    private String userName, phoneNumber, address, email;
    private Boolean transfer;
    private Double transportFee;
    private List<BillStatus> statuses;
    private List<BillDetail> details;

    private String addressDetail;

    public Integer getId() {
        return id;
    }

    public Bill() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getTransportFee() {
        return transportFee;
    }

    public void setTransportFee(Double transportFee) {
        this.transportFee = transportFee;
    }

    public Boolean isTransfer() {
        return transfer;
    }

    public List<BillStatus> getStatuses() {
        return statuses;
    }

    public void setTransfer(Boolean transfer) {
        this.transfer = transfer;
    }

    public List<BillDetail> getDetails() {
        return details;
    }

    public void addStatus(BillStatus status) {
        if (statuses == null) {
            statuses = new ArrayList<>();
        }

        statuses.add(status);
    }

    public void addDetail(BillDetail detail) {
        if (details == null) {
            details = new ArrayList<BillDetail>();
        }

        details.add(detail);
    }

    public Integer getCodeProvince() {
        return codeProvince;
    }

    public void setCodeProvince(Integer codeProvince) {
        this.codeProvince = codeProvince;
    }

    public Integer getCodeDistrict() {
        return codeDistrict;
    }

    public void setCodeDistrict(Integer codeDistrict) {
        this.codeDistrict = codeDistrict;
    }

    public Integer getCodeWard() {
        return codeWard;
    }

    public void setCodeWard(Integer codeWard) {
        this.codeWard = codeWard;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", userId=" + userId +
                ", codeProvince=" + codeProvince +
                ", codeDistrict=" + codeDistrict +
                ", codeWard=" + codeWard +
                ", userName='" + userName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", transfer=" + transfer +
                ", transportFee=" + transportFee +
                ", statuses=" + statuses +
                ", details=" + details +
                '}';
    }

    public double totalBill() {
        double total = 0;
        for (BillDetail bd : this.getDetails()) {
            total += bd.getPrice() * bd.getQuantity();
        }
        return total + transportFee;
    }

    public void setStatuses(List<BillStatus> statuses) {
        this.statuses = statuses;
    }

    public void setDetails(List<BillDetail> details) {
        this.details = details;
    }

    public BillStatus getLastStatus() {
        return this.statuses.get(this.statuses.size()-1);
    }

    public boolean canEdit() {
        return getLastStatus().getCanEdit();
    }

    public Boolean getTransfer() {
        return transfer;
    }

    public String getAddressDetail() {
        return addressDetail;
    }

    public void setAddressDetail(String addressDetail) {
        this.addressDetail = addressDetail;
    }
}
