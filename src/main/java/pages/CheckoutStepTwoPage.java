package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutStepTwoPage extends BasePage {

    private final By finishButton = By.id("finish");
    private final By cancelButton = By.id("cancel");
    private final By summaryTotalLabel = By.className("summary_total_label");
    private final By summarySubtotalLabel = By.className("summary_subtotal_label");
    private final By itemsTotalLabel = By.className("summary_info");

    public CheckoutStepTwoPage(WebDriver driver) {
        super(driver);
    }

    public CheckoutCompletePage finishOrder() {
        click(finishButton);
        return new CheckoutCompletePage(driver);
    }

    public String getTotalPriceText() {
        return getText(summaryTotalLabel);
    }

    public double getTotalPriceValue() {
        String text = getTotalPriceText();
        return Double.parseDouble(text.replace("Total: $", ""));
    }

    public double getSubtotalValue() {
        String text = getText(summarySubtotalLabel);
        return Double.parseDouble(text.replace("Item total: $", ""));
    }

    public boolean isLoaded() {
        return isDisplayed(itemsTotalLabel);
    }
}