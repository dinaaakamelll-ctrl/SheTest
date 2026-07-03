package tests;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

public class LoginTest extends BaseTest {
    // TC01: Login with valid credentials
    @Test
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        Assert.assertTrue(loginPage.getCurrentUrl().contains("inventory.html"),
                "User should be redirected to the inventory page after a valid login");
    }

    // TC02: Login with an incorrect password
    @Test
    public void testInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "wrong_password");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"),
                "Error message should indicate that the credentials are incorrect");
    }

    // TC03: Login with empty username and password fields
    @Test
    public void testEmptyFields() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be displayed when fields are empty");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Error message should indicate that username is required");
    }

    // TC04: Login with empty username but valid password
    @Test
    public void testEmptyUsernameOnly() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be displayed");
        Assert.assertTrue(loginPage.getErrorMessage().contains("Username is required"),
                "Error message should indicate that username is required");
    }

    // TC05: Login with a locked-out user
    @Test
    public void testLockedOutUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(loginPage.isErrorDisplayed(), "An error message should be displayed for a locked-out user");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"),
                "Error message should indicate that the user is locked out");
    }
}

