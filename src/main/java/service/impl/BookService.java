//package service.impl;
//
//import model.Book;
////import repository.book.BookRepository;
//import repository.BookRepository;
//import service.IBookService;
//import service.IService;
//
//import java.util.List;
//
//public class BookService implements IService<Book>, IBookService {
//    private static final BookRepository bookRepository = new BookRepository(); // Đánh dấu là final
//
//    @Override
//    public List<Book> getAll() {
//        List<Book> books = BookRepository.findAll(); // Gọi phương thức static đúng cách
//        if (books == null) {
//            System.out.println("Không thể lấy dữ liệu sách.");
//        }
//        return books;
//    }
//
//    @Override
//    public void remove(int id) {
//        if (id <= 0) {
//            throw new IllegalArgumentException("ID không hợp lệ.");
//        }
//        bookRepository.delete(id);
//    }
//
//    @Override
//    public void update(int id, Book book) {
//        if (book == null || id <= 0) {
//            throw new IllegalArgumentException("Dữ liệu sách hoặc ID không hợp lệ.");
//        }
//        bookRepository.update(book);
//    }
//
//    @Override
//    public void update(Book book) {
//        if (book == null) {
//            throw new IllegalArgumentException("Dữ liệu sách không hợp lệ.");
//        }
//        bookRepository.update(book);
//    }
//
//    @Override
//    public Book findById(int id) {
//        if (id <= 0) {
//            throw new IllegalArgumentException("ID không hợp lệ.");
//        }
//        return bookRepository.findById(id);
//    }
//
//    @Override
//    public List<Book> findByName(String name) {
//        if (name == null || name.trim().isEmpty()) {
//            throw new IllegalArgumentException("Tên sách không hợp lệ.");
//        }
//        return bookRepository.findByName(name);
//    }
//
//    @Override
//    public void add(Book book) {
//        if (book == null) {
//            throw new IllegalArgumentException("Dữ liệu sách không hợp lệ.");
//        }
//        bookRepository.add(book);
//    }
//
//    @Override
//    public List<Book> findByAuthor(String name) {
//        if (name == null || name.trim().isEmpty()) {
//            throw new IllegalArgumentException("Tên tác giả không hợp lệ.");
//        }
//        return bookRepository.findByAuthor(name);
//    }
//
//    @Override
//    public List<Book> findByAuthorFr() {
//        return bookRepository.findByAuthorFr();
//    }
//
//    @Override
//    public void updateBook(Book book) {
//        if (book == null) {
//            throw new IllegalArgumentException("Dữ liệu sách không hợp lệ.");
//        }
//        bookRepository.update(book);
//    }
//
//    @Override
//    public List<Book> findByPrice(double minPrice, double maxPrice) {
//        if (minPrice < 0 || maxPrice < 0) {
//            throw new IllegalArgumentException("Giá không hợp lệ.");
//        }
//        return bookRepository.findByPrice(minPrice, maxPrice);
//    }
//
//    // Thêm phương thức cập nhật stockQuantity
//    public void updateStockQuantity(int bookId, int quantity) {
//        if (bookId <= 0 || quantity <= 0) {
//            throw new IllegalArgumentException("ID sách hoặc số lượng không hợp lệ.");
//        }
//        Book book = findById(bookId);
//        if (book != null) {
//            int newStock = book.getStockQuantity() - quantity;
//            if (newStock < 0) {
//                throw new IllegalStateException("Không đủ hàng trong kho cho sách ID: " + bookId);
//            }
//            book.setStockQuantity(newStock);
//            update(book);
//        }
//    }
//}
package service.impl;

import model.Book;
import repository.book.BookRepository;
import service.IBookService;
import service.IService;

import java.util.List;

public class BookService implements IService<Book>, IBookService {
    private static final BookRepository bookRepository = new BookRepository();

    @Override
    public List<Book> getAll() {
        // Gọi getBooksByPage với trang đầu tiên và kích thước trang lớn để lấy tất cả sách
        List<Book> books = bookRepository.getBooksByPage(1, Integer.MAX_VALUE);
        if (books == null || books.isEmpty()) {
            System.out.println("Không thể lấy dữ liệu sách.");
        }
        return books;
    }

    @Override
    public void remove(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không hợp lệ.");
        }
        bookRepository.delete(id);
    }

    @Override
    public void update(int id, Book book) {
        if (book == null || id <= 0) {
            throw new IllegalArgumentException("Dữ liệu sách hoặc ID không hợp lệ.");
        }
        bookRepository.update(book);
    }

    @Override
    public void update(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Dữ liệu sách không hợp lệ.");
        }
        bookRepository.update(book);
    }

    @Override
    public Book findById(int id) {
        if (id <= 0) {
            throw new IllegalArgumentException("ID không hợp lệ.");
        }
        return bookRepository.findById(id);
    }

    @Override
    public List<Book> findByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên sách không hợp lệ.");
        }
        // Giả sử findByName tương đương với findByTitle
        return bookRepository.findByTitle(name, 1, Integer.MAX_VALUE);
    }

    @Override
    public void add(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Dữ liệu sách không hợp lệ.");
        }
        bookRepository.add(book);
    }

    @Override
    public List<Book> findByAuthor(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tác giả không hợp lệ.");
        }
        return bookRepository.findByAuthor(name, 1, Integer.MAX_VALUE);
    }

    @Override
    public List<Book> findByAuthorFr() {
        // Giả sử findByAuthorFr là một phương thức đặc biệt trong BookRepository
        return bookRepository.findByAuthorFr();
    }

    @Override
    public void updateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Dữ liệu sách không hợp lệ.");
        }
        bookRepository.update(book);
    }

    @Override
    public List<Book> findByPrice(double minPrice, double maxPrice) {
        if (minPrice < 0 || maxPrice < 0) {
            throw new IllegalArgumentException("Giá không hợp lệ.");
        }
        return bookRepository.findByPrice(minPrice, maxPrice, 1, Integer.MAX_VALUE);
    }

    public void updateStockQuantity(int bookId, int quantity) {
        if (bookId <= 0 || quantity <= 0) {
            throw new IllegalArgumentException("ID sách hoặc số lượng không hợp lệ.");
        }
        Book book = findById(bookId);
        if (book != null) {
            int newStock = book.getStockQuantity() - quantity;
            if (newStock < 0) {
                throw new IllegalStateException("Không đủ hàng trong kho cho sách ID: " + bookId);
            }
            book.setStockQuantity(newStock);
            update(book);
        }
    }

    // Thêm các phương thức cần thiết cho BookServlet
    public List<Book> findByTitle(String title, int page, int pageSize) {
        return bookRepository.findByTitle(title, page, pageSize);
    }

    public int getTotalBooksByTitle(String title) {
        return bookRepository.getTotalBooksByTitle(title);
    }

    public List<Book> findByAuthor(String author, int page, int pageSize) {
        return bookRepository.findByAuthor(author, page, pageSize);
    }

    public int getTotalBooksByAuthor(String author) {
        return bookRepository.getTotalBooksByAuthor(author);
    }

    public List<Book> findByPrice(double minPrice, double maxPrice, int page, int pageSize) {
        return bookRepository.findByPrice(minPrice, maxPrice, page, pageSize);
    }

    public int getTotalBooksByPrice(double minPrice, double maxPrice) {
        return bookRepository.getTotalBooksByPrice(minPrice, maxPrice);
    }

    public List<Book> getBooksByPage(int page, int pageSize) {
        return bookRepository.getBooksByPage(page, pageSize);
    }

    public int getTotalBooks() {
        return bookRepository.getTotalBooks();
    }
}