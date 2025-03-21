//package repository.connection;
//
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//
//public class DBRepository  {
//    private static final String jdbcURL = "jdbc:mysql://localhost:3306/bookstoredb";
//    private static final String jdbcUsername = "root";
//    private static final String jdbcPassword = "loiphan123";
//    private static Connection connection;
//
//    static {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//        } catch (SQLException | ClassNotFoundException e) {
//            System.out.println(e.getMessage());
//        }
//    }
//
//    public static Connection getConnection() {
//        try {
//            if (connection == null || connection.isClosed()) {
//                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
//}
package repository.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBRepository {
    private static final String jdbcURL = "jdbc:sqlserver://localhost\\LOI:1433;database=bookstoreDB;encrypt=true;trustServerCertificate=true";
    private static final String jdbcUsername = "sa";
    private static final String jdbcPassword = "123";
    private static Connection connection;

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            System.out.println("Driver JDBC đã được tải thành công!");
            connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
            System.out.println("Kết nối ban đầu thành công tới SQL Server!");
        } catch (SQLException e) {
            System.out.println("SQLException khi kết nối ban đầu: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver JDBC: " + e.getMessage());
        }
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
                System.out.println("Driver JDBC đã được tải lại thành công!");
                connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
                System.out.println("Kết nối lại thành công tới SQL Server!");
            }
        } catch (SQLException e) {
            System.out.println("SQLException khi kiểm tra hoặc tạo lại kết nối: " + e.getMessage());
            connection = null;
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy driver JDBC: " + e.getMessage());
            connection = null;
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Đã đóng kết nối tới SQL Server!");
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi đóng kết nối: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Connection conn = DBRepository.getConnection();
        if (conn != null) {
            System.out.println("Kết nối thành công tới SQL Server!");
            DBRepository.closeConnection();
        } else {
            System.out.println("Kết nối thất bại!");
        }
    }
}

