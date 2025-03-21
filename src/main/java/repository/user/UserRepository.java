package repository.user;




import model.Role;
import model.User;
import repository.connection.DBRepository;




import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;




public class UserRepository {
    private static final Logger LOGGER = Logger.getLogger(UserRepository.class.getName());


    private static final String INSERT_USER_SQL =
            "INSERT INTO users (username, password, email, role_id, status) VALUES (?, ?, ?, ?, 1)";


    private static final String GET_USER_BY_USERNAME_PASSWORD =
            "SELECT * FROM users WHERE username = ? AND status = 1"; // Chỉ lấy user chưa bị xóa


    private static final String GET_ALL_USERS =
            "SELECT id, username, email, role_id FROM users WHERE status = 1"; // Chỉ lấy user chưa bị xóa


    private static final String UPDATE_USER_STATUS =
            "UPDATE users SET status = ? WHERE id = ?";
    public void saveUser(User user) {
        Connection connection = DBRepository.getConnection();
        try (
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, password, email) VALUES (?, ?, ?)")) {
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT id, username, email, role_id, status FROM Users WHERE status = 1";
        Connection conn = DBRepository.getConnection();


        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                User user = new User(
                        rs.getInt("id"),
                        rs.getString("username"),
                        "", // Không lấy password để bảo mật
                        rs.getString("email"),
                        rs.getInt("role_id"),
                        rs.getInt("status")
                );
                users.add(user);
            }
            LOGGER.info("Đã lấy " + users.size() + " người dùng từ database.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi lấy danh sách user: ", e);
        }
        return users;
    }


    public User login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND status = 1";


        try (Connection connection = DBRepository.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            if (connection == null) {
                LOGGER.severe("Không thể kết nối đến database!");
                return null;
            }
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);


            ResultSet resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                return new User(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("email"),
                        resultSet.getInt("role_id"),
                        resultSet.getInt("status")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Lỗi khi đăng nhập: ", e);
        }
        return null;
    }




}
