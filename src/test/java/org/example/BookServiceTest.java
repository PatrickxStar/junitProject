package org.example;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.mockito.Mockito;
import java.util.List;
import static org.junit.Assert.*;

public class BookServiceTest {

    private BookService bookService;
    private Book realBook;
    private User mockUser;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before running any tests in BookServiceTest");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After running all tests in BookServiceTest");
    }

    @Before
    public void setUp() {
        bookService = new BookService();
        realBook = new Book("1984", "George Orwell", "Dystopian", 9.99); // Use a real book object
        mockUser = Mockito.mock(User.class);
    }

    @After
    public void tearDown() {
        System.out.println("Test completed. Cleaning up resources if any.");
    }

    @Test
    public void testSearchBook_TitleMatch() {
        bookService.addBook(realBook);

        List<Book> result = bookService.searchBook("1984");
        assertEquals(1, result.size());
    }

    @Test
    public void testSearchBook_NoMatch() {
        bookService.addBook(realBook); // Add a book with a known title

        List<Book> result = bookService.searchBook("Nonexistent");

        assertTrue("Expected no books to be returned for a nonexistent keyword.", result.isEmpty());
    }

    @Test
    public void testSearchBook_EdgeCase_EmptyKeyword() {
        bookService.addBook(realBook); // Ensure the book is added to the database

        List<Book> result = bookService.searchBook("");

        // If empty keyword should return all books
        assertEquals("Expected the search to return all books for an empty keyword.", 1, result.size());
    }

    @Test
    public void testPurchaseBook_Success() {
        bookService.addBook(realBook); // Add the book to the database to simulate that it exists

        boolean result = bookService.purchaseBook(mockUser, realBook);

        assertTrue("Expected the purchase to be successful.", result);
    }

    @Test
    public void testPurchaseBook_BookNotInDatabase() {
        boolean result = bookService.purchaseBook(mockUser, realBook);
        assertFalse(result);
    }

    @Test
    public void testPurchaseBook_EdgeCase_NullUser() {
        boolean result = bookService.purchaseBook(null, realBook);
        assertFalse(result); // Assuming null user cannot purchase
    }
}
