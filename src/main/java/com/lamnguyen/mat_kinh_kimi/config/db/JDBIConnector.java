package com.lamnguyen.mat_kinh_kimi.config.db;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.io.Serializable;
import java.sql.SQLException;


public class JDBIConnector implements Serializable {
    private static Jdbi jdbi;

    private static void makeConnect() {
        MysqlDataSource dataSource = new MysqlDataSource();
        var url = "jdbc:mysql://" +
                DBProperties.getDbHost() +
                ":" +
                DBProperties.getDbPort() +
                "/" +
                DBProperties.getDbName() +
                "?useUnicode=yes&characterEncoding=UTF-8";
        dataSource.setURL(url);
        dataSource.setUser(DBProperties.getUsername());
        dataSource.setPassword(DBProperties.getPassword());
        try {
            dataSource.setUseCompression(true); //Lưu user
            dataSource.setAutoReconnect(true); //Tự động kết nối lại
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            throw new RuntimeException(e);
        }
        jdbi = Jdbi.create(dataSource);
    }


    private JDBIConnector() {
    }

    public static Jdbi get() {
        if (jdbi == null) makeConnect();

        return jdbi;
    }
}