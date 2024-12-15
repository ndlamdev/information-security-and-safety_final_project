/**
 * Author: Nguyen Dinh Lam
 * Email: kiminonawa1305@gmail.com
 * Phone number: +84 855354919
 * Create at: 2:10 PM - 15/12/2024
 * User: kimin
 **/

package com.lamnguyen.mat_kinh_kimi.domain.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Signature {
    private String algorithm, signature;
}
