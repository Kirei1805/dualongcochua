//package repository.book;
//
//import model.Book;
//import repository.connection.DBRepository;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class BookRepository {
//    // Lấy tất cả sách có trạng thái còn hoạt động
//    public static List<Book> findAll() {
//        List<Book> books = new ArrayList<>();
//        try (Connection conn = DBRepository.getConnection()) {
//            if (conn == null) {
//                System.out.println("Không thể kết nối đến cơ sở dữ liệu!");
//                return books;
//            }
//
//            Statement statement = conn.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM books WHERE status = 1");
//
//            while (resultSet.next()) {
//                int idBook = resultSet.getInt("id");
//                String title = resultSet.getString("title");
//                String author = resultSet.getString("author");
//                Double price = resultSet.getDouble("price");
//                String imageURL = resultSet.getString("ImageURL");
//                int quantity = resultSet.getInt("stock_quantity");
//                String description = resultSet.getString("book_description");
//                books.add(new Book(idBook, title, author, price, imageURL, quantity, description));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
//        }
//        return books;
//    }
//
//
//
//    // Lấy tất cả sách đã xóa
//    public static List<Book> findAllDelete() {
//        List<Book> books = new ArrayList<>();
//        String query = "SELECT * FROM books WHERE status = 0";
//        try (Connection conn = DBRepository.getConnection();
//             Statement statement = conn.createStatement()) {
//
//            if (conn == null) {
//                System.out.println("Kết nối cơ sở dữ liệu là null");
//                return books;
//            }
//
//            ResultSet resultSet = statement.executeQuery(query);
//            while (resultSet.next()) {
//                int idBook = resultSet.getInt("id");
//                String title = resultSet.getString("title");
//                String author = resultSet.getString("author");
//                Double price = resultSet.getDouble("price");
//                String imageURL = resultSet.getString("ImageURL");
//                int quantity = resultSet.getInt("stock_quantity");
//                String description = resultSet.getString("book_description");
//                books.add(new Book(idBook, title, author, price, imageURL, quantity, description));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
//        }
//        return books;
//    }
//
//    // Tìm sách theo tên
//    public static List<Book> findByName(String name) {
//        List<Book> books = new ArrayList<>();
//        String query = "SELECT * FROM books WHERE title LIKE ?";
//        try (Connection conn = DBRepository.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//
//            if (conn == null) {
//                System.out.println("Kết nối cơ sở dữ liệu là null");
//                return books;
//            }
//
//            preparedStatement.setString(1, "%" + name + "%");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int idBook = resultSet.getInt("id");
//                String title = resultSet.getString("title");
//                String author = resultSet.getString("author");
//                Double price = resultSet.getDouble("price");
//                String imageURL = resultSet.getString("ImageURL");
//                int quantity = resultSet.getInt("stock_quantity");
//                String description = resultSet.getString("book_description");
//                books.add(new Book(idBook, title, author, price, imageURL, quantity, description));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
//        }
//        return books;
//    }
//
//    // Tìm sách theo tác giả
//    public static List<Book> findByAuthor(String author) {
//        List<Book> books = new ArrayList<>();
//        String query = "SELECT * FROM books WHERE author LIKE ?";
//        try (Connection conn = DBRepository.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//
//            if (conn == null) {
//                System.out.println("Kết nối cơ sở dữ liệu là null");
//                return books;
//            }
//
//            preparedStatement.setString(1, "%" + author + "%");
//            ResultSet resultSet = preparedStatement.executeQuery();
//            while (resultSet.next()) {
//                int idBook = resultSet.getInt("id");
//                String title = resultSet.getString("title");
//                String authorName = resultSet.getString("author");
//                Double price = resultSet.getDouble("price");
//                String imageURL = resultSet.getString("ImageURL");
//                int quantity = resultSet.getInt("stock_quantity");
//                String description = resultSet.getString("book_description");
//                books.add(new Book(idBook, title, authorName, price, imageURL, quantity, description));
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
//        }
//        return books;
//    }
//
//    public static List<Book> findByAuthorFr() {
//        List<Book> books = new ArrayList<>();
//        try {
//            String query = "SELECT * FROM books WHERE author NOT IN ('Nam Cao', 'Vũ Trọng Phụng', 'Nguyễn Nhật Ánh')";
//            PreparedStatement preparedStatement = DBRepository.getConnection().prepareStatement(query);
//            ResultSet resultSet = preparedStatement.executeQuery();
//
//            while (resultSet.next()) {
//                int idBook = resultSet.getInt("id");
//                String title = resultSet.getString("title");
//                String authorName = resultSet.getString("author");
//                Double price = resultSet.getDouble("price");
//                String imageURL = resultSet.getString("ImageURL");
//                int quantity = resultSet.getInt("stock_quantity");
//                String description = resultSet.getString("book_description");
//                books.add(new Book(idBook, title, authorName, price,imageURL, quantity, description));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return books;
//    }
//
//    // Tìm sách theo ID
//    public Book findById(int id) {
//        Book book = null;
//        String query = "SELECT id, title, author, price, ImageURL, stock_quantity, book_description FROM books WHERE id = ?";
//        try (Connection conn = DBRepository.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//
//            if (conn == null) {
//                System.out.println("Kết nối cơ sở dữ liệu là null");
//                return null;
//            }
//
//            preparedStatement.setInt(1, id);
//            ResultSet rs = preparedStatement.executeQuery();
//            if (rs.next()) {
//                String title = rs.getString("title");
//                String author = rs.getString("author");
//                Double price = rs.getDouble("price");
//                String image = rs.getString("ImageURL");
//                int stock = rs.getInt("stock_quantity");
//                String description = rs.getString("book_description");
//                book = new Book(id, title, author, price, image, stock, description);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
//        }
//        return book;
//    }
//
//    // Cập nhật thông tin sách
//    public void update(Book book) {
//        String query = "UPDATE books SET title = ?, author = ?, price = ?, ImageURL = ?, stock_quantity = ?, book_description = ? WHERE id = ?";
//        try (Connection conn = DBRepository.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//
//            if (conn == null) {
//                System.out.println("Kết nối cơ sở dữ liệu là null");
//                return;
//            }
//
//            preparedStatement.setString(1, book.getTitle());
//            preparedStatement.setString(2, book.getAuthor());
//            preparedStatement.setDouble(3, book.getPrice());
//            preparedStatement.setString(4, book.getImageURL());
//            preparedStatement.setInt(5, book.getStockQuantity());
//            preparedStatement.setString(6, book.getBookDescription());
//            preparedStatement.setInt(7, book.getId());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi cập nhật dữ liệu: " + e.getMessage());
//        }
//    }
//
//    // Xóa sách (cập nhật trạng thái là 0)
//    public void delete(int id) {
//        String query = "UPDATE books SET status = 0 WHERE id = ?";
//        try (Connection conn = DBRepository.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//
//            if (conn == null) {
//                System.out.println("Kết nối cơ sở dữ liệu là null");
//                return;
//            }
//
//            preparedStatement.setInt(1, id);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi xóa sách: " + e.getMessage());
//        }
//    }
//
//    // Thêm sách mới
//    public void add(Book book) {
//        String query = "INSERT INTO books (title, author, price, ImageURL, stock_quantity, book_description) VALUES (?, ?, ?, ?, ?, ?)";
//        try (Connection conn = DBRepository.getConnection();
//             PreparedStatement preparedStatement = conn.prepareStatement(query)) {
//
//            if (conn == null) {
//                System.out.println("Kết nối cơ sở dữ liệu là null");
//                return;
//            }
//
//            preparedStatement.setString(1, book.getTitle());
//            preparedStatement.setString(2, book.getAuthor());
//            preparedStatement.setDouble(3, book.getPrice());
//            preparedStatement.setString(4, book.getImageURL());
//            preparedStatement.setInt(5, book.getStockQuantity());
//            preparedStatement.setString(6, book.getBookDescription());
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//            System.out.println("Lỗi khi thêm sách: " + e.getMessage());
//        }
//    }
//
//    public List<Book> findByPrice(double minPrice, double maxPrice) {
//        String sql = "SELECT * FROM books WHERE price BETWEEN ? AND ?";
//        try (Connection conn = DBRepository.getConnection();
//             PreparedStatement stmt = conn.prepareStatement(sql)) {
//            stmt.setDouble(1, minPrice);
//            stmt.setDouble(2, maxPrice);
//            ResultSet rs = stmt.executeQuery();
//            List<Book> books = new ArrayList<>();
//            while (rs.next()) {
//                books.add(new Book(rs.getInt("id"), rs.getString("title"), rs.getString("author"),
//                        rs.getDouble("price"), rs.getString("imageURL"), rs.getInt("stock_quantity"), rs.getString("book_description")));
//            }
//            return books;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return new ArrayList<>();
//        }
//    }
//}
package repository.book;

import model.Book;
import repository.connection.DBRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepository {
    public List<Book> findByTitle(String title, int page, int pageSize) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE title LIKE ? COLLATE SQL_Latin1_General_CP1_CI_AI ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong findByTitle!");
                return books;
            }
            stmt.setString(1, "%" + title + "%");
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            System.out.println("Truy vấn findByTitle: " + query + ", title: " + title);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPrice(rs.getDouble("price"));
                    book.setImageURL(rs.getString("ImageURL"));
                    book.setStockQuantity(rs.getInt("stock_quantity"));
                    books.add(book);
                }
                System.out.println("Số sách tìm thấy với title '" + title + "': " + books.size());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong findByTitle: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    public int getTotalBooksByTitle(String title) {
        String query = "SELECT COUNT(*) FROM books WHERE title LIKE ? COLLATE SQL_Latin1_General_CP1_CI_AI";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong getTotalBooksByTitle!");
                return 0;
            }
            stmt.setString(1, "%" + title + "%");
            System.out.println("Truy vấn getTotalBooksByTitle: " + query + ", title: " + title);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Tổng số sách tìm thấy với title '" + title + "': " + count);
                    return count;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong getTotalBooksByTitle: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Không tìm thấy sách nào với title: " + title);
        return 0;
    }

    public List<Book> findByAuthor(String author, int page, int pageSize) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE author LIKE ? COLLATE SQL_Latin1_General_CP1_CI_AI ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong findByAuthor!");
                return books;
            }
            stmt.setString(1, "%" + author + "%");
            stmt.setInt(2, (page - 1) * pageSize);
            stmt.setInt(3, pageSize);
            System.out.println("Truy vấn findByAuthor: " + query + ", author: " + author);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPrice(rs.getDouble("price"));
                    book.setImageURL(rs.getString("ImageURL"));
                    book.setStockQuantity(rs.getInt("stock_quantity"));
                    books.add(book);
                }
                System.out.println("Số sách tìm thấy với author '" + author + "': " + books.size());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong findByAuthor: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    public int getTotalBooksByAuthor(String author) {
        String query = "SELECT COUNT(*) FROM books WHERE author LIKE ? COLLATE SQL_Latin1_General_CP1_CI_AI";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong getTotalBooksByAuthor!");
                return 0;
            }
            stmt.setString(1, "%" + author + "%");
            System.out.println("Truy vấn getTotalBooksByAuthor: " + query + ", author: " + author);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Tổng số sách tìm thấy với author '" + author + "': " + count);
                    return count;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong getTotalBooksByAuthor: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Không tìm thấy sách nào với author: " + author);
        return 0;
    }

    public List<Book> findByPrice(double minPrice, double maxPrice, int page, int pageSize) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM books WHERE price BETWEEN ? AND ? ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong findByPrice!");
                return books;
            }
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            stmt.setInt(3, (page - 1) * pageSize);
            stmt.setInt(4, pageSize);
            System.out.println("Truy vấn findByPrice: " + query + ", minPrice: " + minPrice + ", maxPrice: " + maxPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPrice(rs.getDouble("price"));
                    book.setImageURL(rs.getString("ImageURL"));
                    book.setStockQuantity(rs.getInt("stock_quantity"));
                    books.add(book);
                }
                System.out.println("Số sách tìm thấy với giá từ " + minPrice + " đến " + maxPrice + ": " + books.size());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong findByPrice: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    public int getTotalBooksByPrice(double minPrice, double maxPrice) {
        String query = "SELECT COUNT(*) FROM books WHERE price BETWEEN ? AND ?";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong getTotalBooksByPrice!");
                return 0;
            }
            stmt.setDouble(1, minPrice);
            stmt.setDouble(2, maxPrice);
            System.out.println("Truy vấn getTotalBooksByPrice: " + query + ", minPrice: " + minPrice + ", maxPrice: " + maxPrice);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    System.out.println("Tổng số sách tìm thấy với giá từ " + minPrice + " đến " + maxPrice + ": " + count);
                    return count;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong getTotalBooksByPrice: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Không tìm thấy sách nào với giá từ " + minPrice + " đến " + maxPrice);
        return 0;
    }

    public List<Book> getBooksByPage(int page, int pageSize) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM books ORDER BY id OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        int offset = (page - 1) * pageSize;

        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println("Truy vấn getBooksByPage: " + sql + ", page: " + page + ", pageSize: " + pageSize);
            stmt.setInt(1, offset);
            stmt.setInt(2, pageSize);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setId(rs.getInt("id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setPrice(rs.getDouble("price"));
                book.setImageURL(rs.getString("ImageURL")); // Sửa thành image_URL
                book.setStockQuantity(rs.getInt("stock_quantity")); // Sửa thành stock_quantity
                book.setBookDescription(rs.getString("book_description")); // Sửa thành book_description
                book.setStatus(rs.getInt("status"));
                books.add(book);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong getBooksByPage: " + e.getMessage());
            e.printStackTrace();
        }
        return books;
    }

    public int getTotalBooks() {
        String sql = "SELECT COUNT(*) FROM books";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                System.out.println("Tổng số sách: " + rs.getInt(1));
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong getTotalBooks: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }

    public Book findById(int id) {
        String query = "SELECT * FROM books WHERE id = ?";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong findById!");
                return null;
            }
            stmt.setInt(1, id);
            System.out.println("Truy vấn findById: " + query + ", id: " + id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setId(rs.getInt("id"));
                    book.setTitle(rs.getString("title"));
                    book.setAuthor(rs.getString("author"));
                    book.setPrice(rs.getDouble("price"));
                    book.setImageURL(rs.getString("ImageURL"));
                    book.setStockQuantity(rs.getInt("stock_quantity"));
                    book.setBookDescription(rs.getString("book_description"));
                    System.out.println("Tìm thấy sách với id: " + id);
                    return book;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong findById: " + e.getMessage());
            e.printStackTrace();
        }
        System.out.println("Không tìm thấy sách với id: " + id);
        return null;
    }

    public void add(Book book) {
        String query = "INSERT INTO books (title, author, price, image_url, stock_quantity) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong add!");
                return;
            }
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setDouble(3, book.getPrice());
            stmt.setString(4, book.getImageURL());
            stmt.setInt(5, book.getStockQuantity());
            System.out.println("Truy vấn add: " + query + ", book: " + book.getTitle());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Đã thêm sách: " + book.getTitle() + ", Số hàng ảnh hưởng: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong add: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void update(Book book) {
        String query = "UPDATE books SET title = ?, author = ?, price = ?, ImageURL = ?, stock_quantity = ? WHERE id = ?";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong update!");
                return;
            }
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setDouble(3, book.getPrice());
            stmt.setString(4, book.getImageURL());
            stmt.setInt(5, book.getStockQuantity());
            stmt.setInt(6, book.getId());
            System.out.println("Truy vấn update: " + query + ", book: " + book.getTitle());
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Đã cập nhật sách: " + book.getTitle() + ", Số hàng ảnh hưởng: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong update: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void delete(int id) {
        String query = "DELETE FROM books WHERE id = ?";
        try (Connection conn = DBRepository.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            if (conn == null) {
                System.out.println("Kết nối tới cơ sở dữ liệu thất bại trong delete!");
                return;
            }
            stmt.setInt(1, id);
            System.out.println("Truy vấn delete: " + query + ", id: " + id);
            int rowsAffected = stmt.executeUpdate();
            System.out.println("Đã xóa sách với id: " + id + ", Số hàng ảnh hưởng: " + rowsAffected);
        } catch (SQLException e) {
            System.out.println("Lỗi SQL trong delete: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Book> findByAuthorFr() {
        // Giả sử đây là một phương thức đặc biệt để tìm sách của tác giả Pháp
        return findByAuthor("France", 1, Integer.MAX_VALUE);
    }
}