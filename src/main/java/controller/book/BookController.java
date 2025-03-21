//package controller.book;
//
//import model.Book;
//import service.impl.BookService;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//@WebServlet(name = "BookController", urlPatterns = {"/book", "/"})
//public class BookController extends HttpServlet {
//    private final BookService bookService = new BookService();
//    private static final int BOOKS_PER_PAGE = 16; // Số sách trên mỗi trang
//
//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        req.setCharacterEncoding("UTF-8"); // Đảm bảo mã hóa UTF-8
//        resp.setCharacterEncoding("UTF-8");
//        String action = req.getParameter("type");
//        String searchQuery = req.getParameter("query") != null ? req.getParameter("query").trim() : "";
//        String minPriceStr = req.getParameter("minPrice");
//        String maxPriceStr = req.getParameter("maxPrice");
//
//        // Debug tham số nhận được
//        System.out.println("Received parameters: type=" + action + ", query=" + searchQuery +
//                ", minPrice=" + minPriceStr + ", maxPrice=" + maxPriceStr);
//
//        // Lấy trang hiện tại, mặc định là 1 nếu không có
//        int currentPage = 1;
//        try {
//            currentPage = Integer.parseInt(req.getParameter("page"));
//            if (currentPage < 1) currentPage = 1;
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid page parameter: " + e.getMessage());
//        }
//
//        List<Book> allBooks = new ArrayList<>();
//        if (!searchQuery.isEmpty() || (minPriceStr != null && maxPriceStr != null)) {
//            switch (action) {
//                case "author":
//                    allBooks = bookService.findByAuthor(searchQuery);
//                    System.out.println("After findByAuthor with query '" + searchQuery + "', size: " + allBooks.size());
//                    break;
//                case "author_fr":
//                    allBooks = bookService.findByAuthorFr();
//                    System.out.println("After findByAuthorFr, size: " + allBooks.size());
//                    break;
//                case "findByPrice":
//                    try {
//                        double minPrice = minPriceStr != null ? Double.parseDouble(minPriceStr) : 0.0;
//                        double maxPrice = maxPriceStr != null ? Double.parseDouble(maxPriceStr) : Double.MAX_VALUE;
//                        System.out.println("Filtering by price: " + minPrice + " - " + maxPrice);
//                        allBooks = bookService.findByPrice(minPrice, maxPrice);
//                        System.out.println("After findByPrice, size: " + allBooks.size());
//                    } catch (NumberFormatException | NullPointerException e) {
//                        System.out.println("Invalid price range received: " + e.getMessage());
//                        allBooks = bookService.getAll(); // Fallback to all books if error
//                    }
//                    break;
//                default:
//                    allBooks = bookService.findByName(searchQuery);
//                    System.out.println("Searching by name with query '" + searchQuery + "', size: " + allBooks.size());
//                    break;
//            }
//        } else {
//            allBooks = bookService.getAll();
//            System.out.println("Loading all books, size: " + allBooks.size());
//        }
//
//        // Tính tổng số trang
//        int totalBooks = allBooks.size();
//        int totalPages = (int) Math.ceil((double) totalBooks / BOOKS_PER_PAGE);
//
//        // Lấy danh sách sách cho trang hiện tại
//        int startIndex = (currentPage - 1) * BOOKS_PER_PAGE;
//        int endIndex = Math.min(startIndex + BOOKS_PER_PAGE, totalBooks);
//        List<Book> books = (startIndex < totalBooks) ? allBooks.subList(startIndex, endIndex) : new ArrayList<>();
//
//        // Đưa các thuộc tính vào request
//        req.setAttribute("books", books);
//        req.setAttribute("currentPage", currentPage);
//        req.setAttribute("totalPages", totalPages);
//        req.getRequestDispatcher("WEB-INF/view/book/listBook.jsp").forward(req, resp);
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        doGet(req, resp); // Gọi lại doGet để xử lý POST
//    }
//}
package controller.book;

import model.Book;
import service.impl.BookService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/book")
public class BookController extends HttpServlet {
    private BookService bookService;

    @Override
    public void init() throws ServletException {
        bookService = new BookService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int page = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
        int pageSize = 16;

        String type = request.getParameter("type");
        String query = request.getParameter("query");
        String minPrice = request.getParameter("minPrice");
        String maxPrice = request.getParameter("maxPrice");

        List<Book> books;
        int totalBooks;

        try {
            if (type != null && query != null && !query.isEmpty()) {
                if (type.equals("title")) {
                    books = bookService.findByTitle(query, page, pageSize);
                    totalBooks = bookService.getTotalBooksByTitle(query);
                    System.out.println("Tìm kiếm theo title: " + query + ", Tổng số sách: " + totalBooks);
                } else if (type.equals("author")) {
                    books = bookService.findByAuthor(query, page, pageSize);
                    totalBooks = bookService.getTotalBooksByAuthor(query);
                    System.out.println("Tìm kiếm theo author: " + query + ", Tổng số sách: " + totalBooks);
                } else if (type.equals("findByPrice")) {
                    double min = minPrice != null && !minPrice.isEmpty() ? Double.parseDouble(minPrice) : 0;
                    double max = maxPrice != null && !maxPrice.isEmpty() ? Double.parseDouble(maxPrice) : Double.MAX_VALUE;
                    books = bookService.findByPrice(min, max, page, pageSize);
                    totalBooks = bookService.getTotalBooksByPrice(min, max);
                    System.out.println("Tìm kiếm theo giá từ " + min + " đến " + max + ", Tổng số sách: " + totalBooks);
                } else {
                    books = bookService.getBooksByPage(page, pageSize);
                    totalBooks = bookService.getTotalBooks();
                    System.out.println("Lấy tất cả sách, Tổng số sách: " + totalBooks);
                }
            } else {
                books = bookService.getBooksByPage(page, pageSize);
                totalBooks = bookService.getTotalBooks();
                System.out.println("Lấy tất cả sách, Tổng số sách: " + totalBooks);
            }

            int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
            System.out.println("Danh sách sách: " + books);
            System.out.println("Tổng số trang: " + totalPages);

            request.setAttribute("books", books);
            request.setAttribute("currentPage", page);
            request.setAttribute("totalPages", totalPages);
            request.getRequestDispatcher("/WEB-INF/view/book/listBook.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Đã có lỗi xảy ra: " + e.getMessage());
            request.getRequestDispatcher("/WEB-INF/view/error.jsp").forward(request, response);
        }
    }
}