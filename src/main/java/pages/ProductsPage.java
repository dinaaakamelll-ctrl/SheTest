package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;


public class ProductsPage {

    private final WebDriver driver;

    private final By inventoryItems = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.cssSelector("select[data-test='product_sort_container']");
    private final By pageTitle = By.className("title");
    private final By cartBadge = By.className("shopping_cart_badge");
    private final By cartIcon = By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }


    public boolean isLoaded() {
        return driver.findElement(pageTitle).getText().equalsIgnoreCase("Products");
    }

    public int getProductsCount() {
        return driver.findElements(inventoryItems).size();
    }

    public List<String> getProductNamesList() {
        return driver.findElements(productNames)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public List<Double> getProductPricesList() {
        return driver.findElements(productPrices)
                .stream()
                .map(e -> Double.parseDouble(e.getText().replace("$", "")))
                .collect(Collectors.toList());
    }


    public void sortBy(String option) {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText(option);
    }


    public void openProductDetails(String productName) {
        List<WebElement> names = driver.findElements(productNames);
        for (WebElement el : names) {
            if (el.getText().equalsIgnoreCase(productName)) {
                el.click();
                return;
            }
        }
        throw new RuntimeException("Product not found: " + productName);
    }


    public void addProductToCart(String productName) {
        String idSuffix = productName.toLowerCase().replace(" ", "-");
        By addButton = By.id("add-to-cart-" + idSuffix);
        driver.findElement(addButton).click();
    }

    public void removeProductFromCart(String productName) {
        String idSuffix = productName.toLowerCase().replace(" ", "-");
        By removeButton = By.id("remove-" + idSuffix);
        driver.findElement(removeButton).click();
    }

    public int getCartBadgeCount() {
        List<WebElement> badge = driver.findElements(cartBadge);
        if (badge.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badge.get(0).getText());
    }

    public CartPage goToCart() {
        driver.findElement(cartIcon).click();
        return new CartPage(driver);
    }
}
