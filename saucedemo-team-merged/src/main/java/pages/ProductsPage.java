package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import java.util.List;
import java.util.stream.Collectors;

/**
 * صفحة المنتجات (Inventory Page) - Tester 2 Scope
 * بتغطي: عرض المنتجات - Sorting - الدخول لتفاصيل المنتج
 */
public class ProductsPage {

    private final WebDriver driver;

    private final By inventoryItems = By.className("inventory_item");
    private final By productNames = By.className("inventory_item_name");
    private final By productPrices = By.className("inventory_item_price");
    private final By sortDropdown = By.cssSelector("select[data-test='product_sort_container']");
    private final By pageTitle = By.className("title");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
    }

    // ---------- عرض المنتجات ----------

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

    // ---------- Sorting ----------

    /**
     * @param option القيم المتاحة: "Name (A to Z)", "Name (Z to A)",
     *               "Price (low to high)", "Price (high to low)"
     */
    public void sortBy(String option) {
        Select select = new Select(driver.findElement(sortDropdown));
        select.selectByVisibleText(option);
    }

    // ---------- تفاصيل المنتج ----------

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
}
