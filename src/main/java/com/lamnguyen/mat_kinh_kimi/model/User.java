package com.lamnguyen.mat_kinh_kimi.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Setter
@Getter
@ToString
public class User {
    private Integer id, role;
    private String avatar, fullName, sex, email, password, verify;
    private LocalDate birthDay;
    private LocalDateTime registrationTime;
    private boolean lock;

    public User() {
    }

    public boolean isAdmin() {
        return role == 0;
    }

    /**
     * @return -1 Nếu mã không khớp, 0 Nếu như hết thời gian, 1 Nếu verify thành công
     **/
    public int isVerify(String hashCode) {
        if (timeOut()) return 0;
        return BCrypt.checkpw(verify, hashCode) ? 1 : -1;
    }

    private boolean timeOut() {
        return registrationTime.until(LocalDateTime.now(), ChronoUnit.MINUTES) > 10;
    }
}
