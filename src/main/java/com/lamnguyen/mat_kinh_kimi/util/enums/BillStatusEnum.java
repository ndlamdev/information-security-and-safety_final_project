/**
 * Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 3:58 PM - 13/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.util.enums;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum BillStatusEnum {
    WAIL_CONFIRM(0, "Chờ xác nhận"), CONFIRM_SUCCESS(1, "Xác nhận thành công"), TRANSFORM(2, "Vận chuyển"), SUCCESS(3, "Thành công"), CANCEL(-1, "Đã hủy"), NOT_SIGN(-2, "Chưa ký");

    final String status;
    final int step;

    BillStatusEnum(int step, String status) {
        this.step = step;
        this.status = status;
    }

    public BillStatusJson nextStep() {
        return mapToJson(Arrays.stream(BillStatusEnum.values()).filter(billStatus -> billStatus.step == this.step + 1).findFirst().orElse(null));
    }

    public List<BillStatusJson> listAfterThisStep() {
        return Arrays.stream(BillStatusEnum.values()).filter(billStatus -> billStatus.step > this.step).map(BillStatusEnum::mapToJson).toList();
    }

    public static List<BillStatusJson> getAllStatus() {
        return Arrays.stream(BillStatusEnum.values()).map(BillStatusEnum::mapToJson).toList();
    }

    public static BillStatusEnum findEnumByStatus(String status) {
        return Arrays.stream(BillStatusEnum.values()).filter(billStatus -> billStatus.status.equals(status)).findFirst().get();
    }

    private static BillStatusJson mapToJson(BillStatusEnum e) {
        if (e == null) return null;
        return BillStatusJson.builder()
                .status(e.getStatus())
                .step(e.getStep())
                .name(e.name())
                .build();
    }

    @Getter
    @Setter
    @Builder
    public static class BillStatusJson {
        private String status;
        private int step;
        private String name;
    }
}
