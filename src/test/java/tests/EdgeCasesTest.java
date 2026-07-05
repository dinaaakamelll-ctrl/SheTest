package tests;
import pages.LoginPage;
import pages.ProductsPage;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.openqa.selenium.By;

public class EdgeCasesTest extends BaseTest {
    @Test(description = "TC-001: Cart should persist after page refresh")
    public void cartPersistsAfterRefresh() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addBackpackToCart();
        Assert.assertEquals(productsPage.getCartCount(), 1);
        driver.navigate().refresh();
        Assert.assertEquals(productsPage.getCartCount(), 1);
    }
    @Test(description = "TC-002: Back button from cart should return to products page")
    public void backButtonFromCartReturnsToProducts() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addBackpackToCart();
        driver.findElement(By.className("shopping_cart_link")).click();
        driver.navigate().back();
        Assert.assertTrue(productsPage.isLoaded());
        Assert.assertEquals(productsPage.getCartCount(), 1);
    }

    @Test(description = "TC-003: Spam clicking Add to Cart should not duplicate")
    public void spamClickAddToCartDoesNotDuplicate() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.spamClickAddToCart(6);
        Assert.assertEquals(productsPage.getCartCount(), 0);
    }
    @Test(description = "TC-005: Products page should work on mobile viewport")
    public void productsPageWorksOnMobile() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.resizeWindow(375, 812);
        Assert.assertTrue(productsPage.isLoaded());
        productsPage.addBackpackToCart();
        Assert.assertEquals(productsPage.getCartCount(), 1);
    }
    @Test(description = "TC-008: Fast navigation should not break app state")
    public void fastNavigationDoesNotBreakState() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addBackpackToCart();
        Assert.assertEquals(productsPage.getCartCount(), 1);
        productsPage.navigateFastBetweenProductsAndCart(5);
        Assert.assertTrue(productsPage.isLoaded());
        Assert.assertEquals(productsPage.getCartCount(), 1);
    }
}
