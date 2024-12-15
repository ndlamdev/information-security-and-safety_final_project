package com.lamnguyen.mat_kinh_kimi.service;

import com.lamnguyen.mat_kinh_kimi.repository.impl.UserRepositoryImpl;
import com.lamnguyen.mat_kinh_kimi.model.Review;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.domain.dto.Page;
import com.lamnguyen.mat_kinh_kimi.domain.dto.UserManage;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class UserService {
    private static UserService instance;
    private final UserRepositoryImpl USER_REPOSITORY;

    public static UserService getInstance() {
        return (instance = instance != null ? instance : new UserService());
    }

    private UserService() {
        USER_REPOSITORY = UserRepositoryImpl.getInstance();
    }

    public User login(String email, String password) {
        return USER_REPOSITORY.login(email, password);
    }

    public boolean signup(User user, String codeVerify) {
        user.setPassword(hashPassword(user.getPassword()));
        int rs = USER_REPOSITORY.insertUser(user, codeVerify);
        return rs == 1;
    }

    public Page<UserManage> getPageUser(int page, int id, String fullName, int role, int lock) {
        List<UserManage> users = USER_REPOSITORY.getPageUser(page, 7, id, fullName, role, lock);
        int count = USER_REPOSITORY.getTotalUserCount(id, fullName, role, lock);
        return new Page<>(count, page, 7, users);
    }

    public boolean updateLockUser(int id) {
        return USER_REPOSITORY.updateBlockUser(id);

    }

    public boolean updateRoleUser(int id, int role) {
        return USER_REPOSITORY.updateRoleUser(id, role);

    }

    public void forgetPassword(String email) {
        // TODO
        /*Anh làm phần quen mật khẩu chơa. alo. nge , anh quên mất @@. chịu,*/
    }

    public boolean verifyByEmail(String email) {
        int rs = USER_REPOSITORY.verifyAccountByEmail(email);
        return rs == 1;
    }

    private String hashPassword(String pass) {
        return BCrypt.hashpw(pass, BCrypt.gensalt(12));
    }

    private boolean checkPassword(String hashPass, String pass) {
        return BCrypt.checkpw(pass, hashPass);
    }

    public boolean containsEmail(String email) {
        return USER_REPOSITORY.containsEmail(email);
    }

    public Map<Integer, User> getUserForReviewProduct(List<Review> reviews) {
        return USER_REPOSITORY.getUserForReviewProduct(reviews);
    }

    public int verify(String email, String codeVerify) {
        return USER_REPOSITORY.verify(email, codeVerify);
    }

    public void updateCodeVerify(String email, String code, LocalDateTime time) {
        USER_REPOSITORY.updateCodeVerify(email, code, time);
    }

    public int resetPassword(String email, String password) {
        return USER_REPOSITORY.resetPassword(email, password);
    }

    public User getUser(String email) {
        return USER_REPOSITORY.getUser(email);
    }

    public boolean signupWithGoogle(User user) {
        user.setPassword(hashPassword(user.getPassword()));
        return USER_REPOSITORY.insertUserNonVerify(user) == 0;
    }

    public User getUser(Integer userId) {
        return USER_REPOSITORY.getUser(userId);
    }

    public String getPublicKey(int userId) {
        return USER_REPOSITORY.getPublicKey(userId);
    }
}
