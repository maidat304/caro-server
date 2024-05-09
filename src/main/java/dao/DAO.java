package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAO {
    protected Connection con;

    public DAO() {
        final String DATABASE_NAME = "caro_game";
        final String jdbcURL = "jdbc:mysql://localhost:3307/" + DATABASE_NAME + "?useSSL=false&allowPublicKeyRetrieval=true";
        final String JDBC_USER = "root";
        final String JDBC_PASSWORD = "admin123";

        try {
            // Đăng ký trình điều khiển JDBC của MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Thiết lập kết nối
            con = DriverManager.getConnection(jdbcURL, JDBC_USER, JDBC_PASSWORD);
            System.out.println("Kết nối đến cơ sở dữ liệu thành công.");
        } catch (ClassNotFoundException e) {
            // Xử lý lỗi khi không tìm thấy lớp trình điều khiển
            System.out.println("Không tìm thấy trình điều khiển JDBC của MySQL.");
            e.printStackTrace();
        } catch (SQLException e) {
            // Xử lý lỗi khi kết nối thất bại
            System.out.println("Lỗi kết nối đến cơ sở dữ liệu MySQL.");
            e.printStackTrace();
        }
    }
}
