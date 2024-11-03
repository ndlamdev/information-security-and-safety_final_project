package model.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BillStatus implements Comparator<BillStatus> {
    private Integer billId;
    private String status;
    private String describe;
    private LocalDateTime date;
    private Boolean canEdit;

    public BillStatus() { }

    public BillStatus(Integer billId, String status, String describe, Boolean canEdit) {
        this.billId = billId;
        this.status = status;
        this.describe = describe;
        this.date = LocalDateTime.now();
        this.canEdit = canEdit;
    }

    public Integer getBillId() {
        return billId;
    }

    public void setBillId(Integer billId) {
        this.billId = billId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Boolean getCanEdit() {
        return canEdit;
    }

    public int isCanEdit() {
        return canEdit == true ? 1 : 0;
    }

    public void setCanEdit(Boolean canEdit) {
        this.canEdit = canEdit;
    }


    @Override
    public int compare(BillStatus o1, BillStatus o2) {
        return o1.getDate().compareTo(o2.getDate());
    }
}
