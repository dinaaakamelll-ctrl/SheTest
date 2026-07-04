package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CartPage extends BasePage {

    private final By cartItems = By.className("cart_item");
    private final By cartItemNames = By.className("inventory_item_name");
    private final By checkoutButton = By.id("checkout");
    private final By continueShoppingButton = By.id("continue-shopping");
    private final By pageTitle = By.className("title");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isDisplayed(pageTitle) && getText(pageTitle).equalsIgnoreCase("Your Cart");
    }

    public int getItemsCount() {
        return driver.findElements(cartItems).size();
    }

    public List<String> getItemNames() {
        return waitForAllVisible(cartItemNames).stream().map(WebElement::getText).toList();
    }

    public boolean containsProduct(String productName) {
        return getItemNames().contains(productName);
    }

    public void removeProduct(String productName) {
        String idSuffix = productName.toLowerCase().replace(" ", "-");
        By removeButton = By.id("remove-" + idSuffix);
        int countBefore = driver.findElements(cartItems).size();
        click(removeButton);
        wait.until(d -> d.findElements(cartItems).size() < countBefore);
    }

    public CheckoutStepOnePage goToCheckout() {
        click(checkoutButton);
        return new CheckoutStepOnePage(driver);
    }

    public ProductsPage continueShopping() {
        click(continueShoppingButton);
        return new ProductsPage(driver);
    }
}