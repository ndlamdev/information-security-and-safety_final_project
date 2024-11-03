package db;

import model.bean.Product;
import model.bean.User;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.sql.SQLException;
import java.util.List;


public class JDBIConnector {
    private static Jdbi jdbi;

    private static void makeConnect() {
        MysqlDataSource dataSource = new MysqlDataSource();
        String url = "jdbc:mysql://" + DBProperties.getDbHost() + ":" + DBProperties.getDbPort() + "/" + DBProperties.getDbName() + "?useUnicode=yes&characterEncoding=UTF-8";
        dataSource.setURL(url);
        dataSource.setUser(DBProperties.getUsername());
        dataSource.setPassword(DBProperties.getPassword());
        try {
            dataSource.setUseCompression(true); //Lưu user
            dataSource.setAutoReconnect(true); //Tự động kết nối lại
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new RuntimeException(throwables);
        }
        jdbi = Jdbi.create(dataSource);
    }


    private JDBIConnector() {
    }

    public static Jdbi get() {
        if (jdbi == null) makeConnect();

        return jdbi;
    }

    public static void main(String[] args) throws Exception {
        Jdbi connector = JDBIConnector.get();

        List<User> users = connector.withHandle(handel -> // lambda function
                handel.createQuery("SELECT * FROM users LIMIT 1 OFFSET 1")
//                        .bind(0, "Nguyễn Đình Lam") //Dấu chấm hỏi đầu tiên có giá trị là admin. Câu lệnh này là where username = "admin"
                        .mapToBean(User.class) // lấy tên cột trong database so sánh với tên cột của User. Nếu tên cột user giống với tên cột database thì giá trị của biến đó trong User sẽ là giá trị của cột đó trong database
                        .list());
        System.out.println(users);
    }
}



