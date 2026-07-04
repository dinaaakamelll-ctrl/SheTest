package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutCompletePage extends BasePage {

    private final By completeHeader = By.className("complete-header");
    private final By backHomeButton = By.id("back-to-products");

    public CheckoutCompletePage(WebDriver driver) {
        super(driver);
    }

    public String getConfirmationMessage() {
        return getText(completeHeader);
    }

    public boolean isOrderComplete() {
        return isDisplayed(completeHeader);
    }

    public ProductsPage backToProducts() {
        click(backHomeButton);
        return new ProductsPage(driver);
    }
}
