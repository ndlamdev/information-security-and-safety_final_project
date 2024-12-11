package com.lamnguyen.mat_kinh_kimi.repository.impl;

import com.lamnguyen.mat_kinh_kimi.model.Review;
import com.lamnguyen.mat_kinh_kimi.model.User;
import com.lamnguyen.mat_kinh_kimi.config.db.JDBIConnector;
import com.lamnguyen.mat_kinh_kimi.domain.dto.UserManage;
import com.lamnguyen.mat_kinh_kimi.repository.Repository;
import org.mindrot.jbcrypt.BCrypt;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl extends Repository {
    private static UserRepositoryImpl instance;

    private UserRepositoryImpl() {
    }

    public static UserRepositoryImpl getInstance() {
        return (instance = instance != null ? instance : new UserRepositoryImpl());
    }

    public int insertUser(User user, String codeVerify) {
        return connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO `users` (`avatar`, `fullName`, `sex`, `birthday`, `email`, `password`, `role`, `verify`, `lock`, `registrationTime`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")
                        .bind(0, user.getAvatar())
                        .bind(1, user.getFullName())
                        .bind(2, user.getSex())
                        .bind(3, user.getBirthDay())
                        .bind(4, user.getEmail())
                        .bind(5, user.getPassword())
                        .bind(6, user.getRole())
                        .bind(7, codeVerify)
                        .bind(8, 0)
                        .bind(9, LocalDateTime.now())
                        .execute()
        );
    }

    public int getTotalUserCount(int id, String fullName, int role, int lock) {
        connector = JDBIConnector.get();
        String baseQuery = "SELECT COUNT(*) FROM users as u WHERE fullName LIKE :fullName ";
        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        if (role != -1) {
            queryBuilder.append(" AND u.role = :role ");
        }

        if (lock != -1) {
            queryBuilder.append(" AND `lock` = :lock ");
        }

        if (id != -1) {
            queryBuilder.append(" AND `id` = :id ");
        }

        return connector.withHandle(handle ->
                handle.createQuery(queryBuilder.toString())
                        .bind("id", id)
                        .bind("fullName", "%" + fullName + "%")
                        .bind("role", role)
                        .bind("lock", lock)
                        .mapTo(Integer.class)
                        .one()
        );
    }

    public List<UserManage> getPageUser(int page, int size, int id, String name, int role, int lock) {
        connector = JDBIConnector.get();
        String baseQuery = "SELECT id, fullName, sex, avatar, email, role, verify, u.lock, (select count(*) from bills where userId = u.id) as countOrder, (select sum(price) from bill_details join bills on bills.id = bill_details.billId where userId = u.id) as sumPrice FROM users as u WHERE fullName LIKE :fullName ";
        StringBuilder queryBuilder = new StringBuilder(baseQuery);
        if (role != -1) {
            queryBuilder.append(" AND u.role = :role ");
        }

        if (lock != -1) {
            queryBuilder.append(" AND `lock` = :lock ");
        }

        if (id != -1) {
            queryBuilder.append(" AND `id` = :id ");
        }


        queryBuilder.append("LIMIT :start, :size");
        List<UserManage> users = connector.withHandle(handle ->
                handle.createQuery(queryBuilder.toString())
                        .bind("id", id)
                        .bind("fullName", "%" + name + "%")
                        .bind("role", role)
                        .bind("lock", lock)
                        .bind("start", (page - 1) * size)
                        .bind("size", size)
                        .mapToBean(UserManage.class)
                        .list()
        );
        return users;
    }

    public boolean updateRoleUser(int id, int role) {
        connector = JDBIConnector.get();

        User u = connector.withHandle(handle ->
                handle.createQuery("SELECT u.lock" +
                                " FROM users AS u " +
                                "WHERE u.id = ? ")
                        .bind(0, id)
                        .mapToBean(User.class).findFirst().orElse(null));

        if (u == null) return false;
        int rs = connector.withHandle(handle ->
                handle.createUpdate("Update users set users.role = ? where id = ?")
                        .bind(0, role)
                        .bind(1, id).execute()

        );
        if (rs > 0) return true;
        return false;
    }

    public boolean updateBlockUser(int id) {
        connector = JDBIConnector.get();

        User u = connector.withHandle(handle ->
                handle.createQuery("SELECT u.lock" +
                                " FROM users AS u " +
                                "WHERE u.id = ? ")
                        .bind(0, id)
                        .mapToBean(User.class).findFirst().orElse(null));

        if (u == null) return false;
        int rs = connector.withHandle(handle ->
                handle.createUpdate("Update users set users.lock = ? where id = ?")
                        .bind(0, !u.isLock())
                        .bind(1, id).execute()

        );
        if (rs > 0) return true;
        return false;
    }

    public int verifyAccountByEmail(String email) {
        connector = JDBIConnector.get();
        return connector.withHandle(handle ->
                handle.createUpdate("Update users set verify = 1 where email = ?")
                        .bind(0, email).execute()

        );
    }

    public int updateProdile(User user) {
        connector = JDBIConnector.get();
        return connector.withHandle(handle ->
                handle.createUpdate("Update users set fullName = ?, sex = ?, birthday =?, avatar = ? where email = ?")
                        .bind(0, user.getFullName())
                        .bind(1, user.getSex())
                        .bind(2, user.getBirthDay())
                        .bind(3, user.getAvatar())
                        .bind(4, user.getEmail())
                        .execute()

        );
    }

    public User login(String email, String password) {
        connector = JDBIConnector.get();
        User user = connector.withHandle(handle ->
                handle.createQuery("SELECT u.id, u.birthday, u.fullName, u.sex, u.avatar, u.email, u.`password`, u.role, u.verify FROM users AS u WHERE u.email = ?  AND u.lock = ?")
                        .bind(0, email)
                        .bind(1, 0)
                        .mapToBean(User.class)
                        .findFirst().orElse(null)
        );

        if (user == null) return null;
        String hashPass = user.getPassword();
        if (BCrypt.checkpw(password, hashPass)) {
            user.setPassword(null);
            return user;
        }
        return null;
    }

    public boolean containsEmail(String email) {
        connector = JDBIConnector.get();
        return connector.withHandle(handle ->
                handle.createQuery("SELECT u.email " +
                                "FROM users AS u " +
                                "WHERE u.email = ? " +
                                "ORDER BY id DESC")
                        .bind(0, email)
                        .mapTo(String.class).findFirst().orElse(null)
        ) != null;
    }

    public Map<Integer, User> getUserForReviewProduct(List<Review> reviews) {
        Map<Integer, User> mapUsers = new HashMap<>();
        User user = null;
        for (Review review : reviews) {
            int userId = review.getUserId();
            user = connector.withHandle(handle ->
                    handle.createQuery("SELECT u.fullName, u.avatar " +
                                    "FROM users AS u " +
                                    "WHERE u.id = ?")
                            .bind(0, userId)
                            .mapToBean(User.class)
                            .findFirst().orElse(null)
            );

            mapUsers.put(review.getId(), user);
        }

        return mapUsers;
    }

    private User getUserForVerify(String email) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT u.id, u.verify, u.registrationTime " +
                                "FROM users AS u " +
                                "WHERE u.email = ? " +
                                "ORDER BY id DESC")
                        .bind(0, email)
                        .mapToBean(User.class).findFirst().orElse(null)
        );
    }

    public int verify(String email, String codeVerify) {
        User user = getUserForVerify(email);

        if (user == null) return -1;
        int result = user.isVerify(codeVerify);
        switch (result) {
            case 1 -> {
                connector.withHandle(handle ->
                        handle.createUpdate("UPDATE users SET verify = NULL " +
                                        "WHERE id = ?;")
                                .bind(0, user.getId())
                                .execute()
                );
            }
        }

        return result;
    }

    public void updateCodeVerify(String email, String code, LocalDateTime time) {
        connector.withHandle(handle ->
                handle.createUpdate("UPDATE users SET " +
                                "verify = ? " +
                                ", registrationTime = ? " +
                                "WHERE email = ?;")
                        .bind(0, code)
                        .bind(1, time)
                        .bind(2, email)
                        .execute()
        );
    }

    public int resetPassword(String email, String password) {
        return connector.withHandle(handle ->
                handle.createUpdate("UPDATE users SET " +
                                "`password` = ? " +
                                "WHERE email = ?;")
                        .bind(0, password)
                        .bind(1, email)
                        .execute()
        );
    }

    public List<User> getAllUsers() {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT * FROM users")
                        .mapToBean(User.class)
                        .list()
        );
    }


    public User getUser(String email) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT u.id, u.birthday, u.fullName, u.sex, u.avatar, u.email, u.`password`, u.role, u.verify FROM users AS u WHERE u.email = ?  AND u.lock = 0")
                        .bind(0, email)
                        .mapToBean(User.class)
                        .findFirst().orElse(null)
        );
    }

    public int insertUserNonVerify(User user) {
        return connector.withHandle(handle ->
                handle.createUpdate("INSERT INTO `users` (`avatar`, `fullName`, `sex`, `birthday`, `email`, `password`, `role`, `verify`, `lock`, `registrationTime`) VALUES (?, ?, ?, ?, ?, ?, ?, NULL, ?, ?)")
                        .bind(0, user.getAvatar())
                        .bind(1, user.getFullName())
                        .bind(2, user.getSex())
                        .bind(3, user.getBirthDay())
                        .bind(4, user.getEmail())
                        .bind(5, user.getPassword())
                        .bind(6, user.getRole())
                        .bind(7, 0)
                        .bind(8, LocalDateTime.now())
                        .execute()
        );
    }

    public User getUser(Integer userId) {
        return connector.withHandle(handle ->
                handle.createQuery("SELECT u.id, u.birthday, u.fullName, u.sex, u.avatar, u.email, u.`password`, u.role, u.verify FROM users AS u WHERE u.id = ?  AND u.lock = 0")
                        .bind(0, userId)
                        .mapToBean(User.class)
                        .findFirst().orElse(null)
        );
    }
}

