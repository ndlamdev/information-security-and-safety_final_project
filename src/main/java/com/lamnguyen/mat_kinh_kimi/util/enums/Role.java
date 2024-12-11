/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 6:39 AM - 11/12/2024
 * User: lam-nguyen
 **/

package com.lamnguyen.mat_kinh_kimi.util.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN(0);
    private final int value;

    Role(int value) {
        this.value = value;
    }
}
