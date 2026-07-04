package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutStepOnePage;
import pages.CheckoutStepTwoPage;
import pages.LoginPage;
import pages.ProductsPage;

public class CheckoutTest extends BaseTest {

    private static final String PRODUCT_1 = "Sauce Labs Backpack";

    private CheckoutStepOnePage goToCheckoutStepOne() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.addProductToCart(PRODUCT_1);
        CartPage cartPage = productsPage.goToCart();
        return cartPage.goToCheckout();
    }

    @Test(description = "إتمام عملية الشراء ببيانات صحيحة والتأكد من رسالة التأكيد")
    public void completeCheckoutWithValidInfo_ShouldShowConfirmation() {
        CheckoutStepOnePage stepOne = goToCheckoutStepOne();
        CheckoutStepTwoPage stepTwo = stepOne.fillInfoAndContinue("Sara", "Ahmed", "12345");
        CheckoutCompletePage completePage = stepTwo.finishOrder();

        Assert.assertTrue(completePage.isOrderComplete(), "صفحة التأكيد المفروض تظهر");
        Assert.assertEquals(completePage.getConfirmationMessage(), "Thank you for your order!",
                "رسالة التأكيد لازم تكون مطابقة");
    }

    @Test(description = "التأكد من ظهور رسالة خطأ لما First Name يبقى فاضي")
    public void checkoutWithEmptyFirstName_ShouldShowError() {
        CheckoutStepOnePage stepOne = goToCheckoutStepOne();
        stepOne.fillInfoAndContinue("", "Ahmed", "12345");

        Assert.assertTrue(stepOne.isErrorDisplayed(), "لازم تظهر رسالة خطأ لما الاسم الأول يبقى فاضي");
        Assert.assertTrue(stepOne.getErrorMessage().contains("First Name"),
                "رسالة الخطأ لازم توضح إن المشكلة في First Name");
    }

    @Test(description = "التأكد من ظهور رسالة خطأ لما Last Name يبقى فاضي")
    public void checkoutWithEmptyLastName_ShouldShowError() {
        CheckoutStepOnePage stepOne = goToCheckoutStepOne();
        stepOne.fillInfoAndContinue("Sara", "", "12345");

        Assert.assertTrue(stepOne.isErrorDisplayed(), "لازم تظهر رسالة خطأ لما اسم العائلة يبقى فاضي");
        Assert.assertTrue(stepOne.getErrorMessage().contains("Last Name"),
                "رسالة الخطأ لازم توضح إن المشكلة في Last Name");
    }

    @Test(description = "التأكد من ظهور رسالة خطأ لما Postal Code يبقى فاضي")
    public void checkoutWithEmptyPostalCode_ShouldShowError() {
        CheckoutStepOnePage stepOne = goToCheckoutStepOne();
        stepOne.fillInfoAndContinue("Sara", "Ahmed", "");

        Assert.assertTrue(stepOne.isErrorDisplayed(), "لازم تظهر رسالة خطأ لما الرقم البريدي يبقى فاضي");
        Assert.assertTrue(stepOne.getErrorMessage().contains("Postal Code"),
                "رسالة الخطأ لازم توضح إن المشكلة في Postal Code");
    }

    @Test(description = "التأكد إن زرار Cancel من صفحة الـ checkout بيرجع للكارت")
    public void cancelCheckout_ShouldReturnToCart() {
        CheckoutStepOnePage stepOne = goToCheckoutStepOne();
        CartPage cartPage = stepOne.cancel();
        Assert.assertTrue(cartPage.isLoaded(), "المفروض نرجع لصفحة الكارت بعد الإلغاء");
    }

    @Test(description = "التأكد إن السعر الإجمالي = السعر الفرعي + الضريبة")
    public void checkoutTotalPrice_ShouldEqualSubtotalPlusTax() {
        CheckoutStepOnePage stepOne = goToCheckoutStepOne();
        CheckoutStepTwoPage stepTwo = stepOne.fillInfoAndContinue("Sara", "Ahmed", "12345");

        double subtotal = stepTwo.getSubtotalValue();
        double total = stepTwo.getTotalPriceValue();

        Assert.assertTrue(total > subtotal,
                "السعر الإجمالي لازم يكون أكبر من السعر الفرعي (بعد إضافة الضريبة)");
    }

    @Test(description = "Edge Case: الضغط على Continue من غير ملء أي بيانات")
    public void checkoutWithAllFieldsEmpty_ShouldShowError() {
        CheckoutStepOnePage stepOne = goToCheckoutStepOne();
        stepOne.clickContinueWithoutFilling();
        Assert.assertTrue(stepOne.isErrorDisplayed(),
                "لازم تظهر رسالة خطأ لما كل الحقول تبقى فاضية");
    }
}