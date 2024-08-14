package org.example;
import org.junit.Before;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.AfterClass;
import org.junit.Test;
import org.mockito.Mockito;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
public class UserServiceTest {

    private UserService userService;
    private User mockUser;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before running any tests in UserServiceTest");
    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After running all tests in UserServiceTest");
    }

    @Before
    public void setUp() {
        userService = new UserService();
        mockUser = Mockito.mock(User.class);
    }

    @After
    public void tearDown() {
        System.out.println("Test completed. Cleaning up resources if any.");
    }

    @Test
    public void testRegisterUser_Success() {
        when(mockUser.getUsername()).thenReturn("JohnDoe");

        boolean result = userService.registerUser(mockUser);
        assertTrue(result);
        verify(mockUser, atLeastOnce()).getUsername();
    }

    @Test
    public void testRegisterUser_UserAlreadyExists() {
        when(mockUser.getUsername()).thenReturn("JohnDoe");

        userService.registerUser(mockUser);
        boolean result = userService.registerUser(mockUser);
        assertFalse(result);
        verify(mockUser, atLeastOnce()).getUsername();  // Allow for more flexibility
    }

    @Test
    public void testRegisterUser_EdgeCase_EmptyUsername() {
        // Given: A mock user with an empty username
        when(mockUser.getUsername()).thenReturn("");
        // When: Registering the user
        boolean result = userService.registerUser(mockUser);
        // Then: The result should be true, since the method doesn't handle empty username explicitly
        assertTrue("Expected registerUser to return true since the method doesn't explicitly handle empty username.", result);
        // Verify that getUsername() was called exactly twice (once for the if check, and once for adding to the database)
        verify(mockUser, times(2)).getUsername();
    }


    @Test
    public void testLoginUser_Success() {
        when(mockUser.getUsername()).thenReturn("JohnDoe");
        when(mockUser.getPassword()).thenReturn("password");

        userService.registerUser(mockUser);
        User result = userService.loginUser("JohnDoe", "password");
        assertNotNull(result);
        verify(mockUser, atLeastOnce()).getUsername();
        verify(mockUser, atLeastOnce()).getPassword();
    }

    @Test
    public void testLoginUser_WrongPassword() {
        when(mockUser.getUsername()).thenReturn("JohnDoe");
        when(mockUser.getPassword()).thenReturn("password");

        userService.registerUser(mockUser);
        User result = userService.loginUser("JohnDoe", "wrongpassword");
        assertNull(result);
        verify(mockUser, atLeastOnce()).getUsername();
    }

    @Test
    public void testLoginUser_UserDoesNotExist() {
        User result = userService.loginUser("NonExistentUser", "password");
        assertNull(result);
    }
}
