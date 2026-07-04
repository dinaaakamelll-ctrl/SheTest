package tests;

import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

public class CartTest extends BaseTest {

    private static final String PRODUCT_1 = "Sauce Labs Backpack";
    private static final String PRODUCT_2 = "Sauce Labs Bike Light";

    private ProductsPage loginAsStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");
        // ضغطة ESC احترازية لقفل أي نافذة (زي "غيّر الباسورد") لو ظهرت
        new Actions(driver).sendKeys(Keys.ESCAPE).perform();
        return new ProductsPage(driver);
    }

    @Test(description = "التأكد إن إضافة منتج واحد للكارت بتحدث عداد الكارت لـ 1")
    public void addSingleProductToCart_ShouldUpdateBadgeCount() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addProductToCart(PRODUCT_1);
        Assert.assertEquals(productsPage.getCartBadgeCount(), 1,
                "عداد الكارت المفروض يبقى 1 بعد إضافة منتج واحد");
    }

    @Test(description = "التأكد إن إضافة أكتر من منتج بتحدث العداد صح")
    public void addMultipleProducts_ShouldUpdateBadgeCountCorrectly() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addProductToCart(PRODUCT_1);
        productsPage.addProductToCart(PRODUCT_2);
        Assert.assertEquals(productsPage.getCartBadgeCount(), 2,
                "عداد الكارت المفروض يبقى 2 بعد إضافة منتجين");
    }

    @Test(description = "التأكد إن إزالة منتج من صفحة البروداكتس نفسها بتقلل العداد")
    public void removeProductFromProductsPage_ShouldDecreaseBadgeCount() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addProductToCart(PRODUCT_1);
        productsPage.addProductToCart(PRODUCT_2);
        productsPage.removeProductFromCart(PRODUCT_1);
        Assert.assertEquals(productsPage.getCartBadgeCount(), 1,
                "بعد إزالة منتج واحد من أصل اتنين، العداد لازم يبقى 1");
    }

    @Test(description = "التأكد إن صفحة الكارت بتعرض أسماء المنتجات الصح")
    public void cartPage_ShouldDisplayCorrectProductNames() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addProductToCart(PRODUCT_1);
        productsPage.addProductToCart(PRODUCT_2);
        CartPage cartPage = productsPage.goToCart();

        Assert.assertTrue(cartPage.isLoaded(), "صفحة الكارت المفروض تفتح");
        Assert.assertEquals(cartPage.getItemsCount(), 2, "المفروض يكون فيه منتجين في الكارت");
        Assert.assertTrue(cartPage.containsProduct(PRODUCT_1), "المنتج الأول لازم يكون موجود");
        Assert.assertTrue(cartPage.containsProduct(PRODUCT_2), "المنتج الثاني لازم يكون موجود");
    }

    @Test(description = "التأكد إن إزالة منتج من داخل صفحة الكارت شغالة")
    public void removeProductFromCartPage_ShouldRemoveItem() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addProductToCart(PRODUCT_1);
        productsPage.addProductToCart(PRODUCT_2);
        CartPage cartPage = productsPage.goToCart();

        cartPage.removeProduct(PRODUCT_1);

        Assert.assertEquals(cartPage.getItemsCount(), 1, "بعد الإزالة لازم يفضل منتج واحد بس");
        Assert.assertFalse(cartPage.containsProduct(PRODUCT_1), "المنتج المتشال مينفعش يكون لسه موجود");
    }

    @Test(description = "التأكد إن زرار Continue Shopping بيرجع لصفحة البروداكتس")
    public void continueShopping_ShouldReturnToProductsPage() {
        ProductsPage productsPage = loginAsStandardUser();
        productsPage.addProductToCart(PRODUCT_1);
        CartPage cartPage = productsPage.goToCart();

        ProductsPage backToProducts = cartPage.continueShopping();

        Assert.assertTrue(backToProducts.isLoaded(), "المفروض نرجع لصفحة البروداكتس تاني");
    }

    @Test(description = "Edge Case: الكارت الفاضي مايظهرش فيه رقم على الأيقونة")
    public void emptyCart_ShouldNotShowBadgeNumber() {
        ProductsPage productsPage = loginAsStandardUser();
        Assert.assertEquals(productsPage.getCartBadgeCount(), 0,
                "الكارت الفاضي المفروض العداد يبقى 0 (مفيش badge أصلاً)");
    }
}