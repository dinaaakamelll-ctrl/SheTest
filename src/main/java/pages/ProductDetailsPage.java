package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * صفحة تفاصيل منتج واحد - Tester 2 Scope
 */
public class ProductDetailsPage {

    private final WebDriver driver;

    private final By detailsName = By.className("inventory_details_name");
    private final By detailsPrice = By.className("inventory_details_price");
    private final By detailsDesc = By.className("inventory_details_desc");
    private final By backButton = By.id("back-to-products");

    public ProductDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getName() {
        return driver.findElement(detailsName).getText();
    }

    public String getPrice() {
        return driver.findElement(detailsPrice).getText();
    }

    public String getDescription() {
        return driver.findElement(detailsDesc).getText();
    }

    public void backToProducts() {
        driver.findElement(backButton).click();
    }
}
