package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductDetailsPage;
import pages.ProductsPage;

import java.util.List;

/**
 * Tester 2 - Products
 * وارثة من BaseTest المشتركة بين الفريق (فيها فتح/قفل المتصفح)
 */
public class ProductsTest extends BaseTest {

    private ProductsPage productsPage;

    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASS = "secret_sauce";

    // ده بيتنفذ بعد setUp() بتاعة BaseTest أوتوماتيك (لأن الكلاس الأب بينفذ الأول)
    @BeforeMethod
    public void loginBeforeEachTest() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(VALID_USER, VALID_PASS);
        productsPage = new ProductsPage(driver);
    }

    // ---------------- عرض المنتجات (Display) ----------------

    @Test(description = "TC_PROD_01: التأكد إن صفحة المنتجات فتحت بنجاح بعد تسجيل الدخول")
    public void testProductsPageLoadsSuccessfully() {
        Assert.assertTrue(productsPage.isLoaded(), "صفحة المنتجات لازم يظهر فيها عنوان Products");
    }

    @Test(description = "TC_PROD_02: التأكد إن عدد المنتجات المعروضة = 6")
    public void testAllProductsAreDisplayed() {
        int count = productsPage.getProductsCount();
        Assert.assertEquals(count, 6, "المفروض يظهر 6 منتجات في صفحة Products");
    }

    @Test(description = "TC_PROD_03: التأكد إن كل منتج ظاهر بيه اسم وسعر (مفيش قيم فاضية)")
    public void testEachProductHasNameAndPrice() {
        List<String> names = productsPage.getProductNamesList();
        List<Double> prices = productsPage.getProductPricesList();

        Assert.assertFalse(names.isEmpty(), "أسماء المنتجات مفروض تكون موجودة");
        Assert.assertEquals(names.size(), prices.size(), "عدد الأسماء لازم يساوي عدد الأسعار");
        for (Double price : prices) {
            Assert.assertTrue(price > 0, "السعر لازم يكون أكبر من صفر");
        }
    }

    // ---------------- Sorting ----------------

    @Test(description = "TC_PROD_04: Sorting حسب الاسم من A إلى Z")
    public void testSortByNameAToZ() {
        productsPage.sortBy("Name (A to Z)");
        List<String> names = productsPage.getProductNamesList();
        List<String> sorted = names.stream().sorted().toList();
        Assert.assertEquals(names, sorted, "الأسماء لازم تكون مرتبة أبجديًا من A لـ Z");
    }

    @Test(description = "TC_PROD_05: Sorting حسب الاسم من Z إلى A")
    public void testSortByNameZToA() {
        productsPage.sortBy("Name (Z to A)");
        List<String> names = productsPage.getProductNamesList();
        List<String> sortedDesc = names.stream().sorted((a, b) -> b.compareTo(a)).toList();
        Assert.assertEquals(names, sortedDesc, "الأسماء لازم تكون مرتبة تنازليًا من Z لـ A");
    }

    @Test(description = "TC_PROD_06: Sorting حسب السعر من الأقل للأعلى")
    public void testSortByPriceLowToHigh() {
        productsPage.sortBy("Price (low to high)");
        List<Double> prices = productsPage.getProductPricesList();
        List<Double> sorted = prices.stream().sorted().toList();
        Assert.assertEquals(prices, sorted, "الأسعار لازم تكون مرتبة تصاعديًا");
    }

    @Test(description = "TC_PROD_07: Sorting حسب السعر من الأعلى للأقل")
    public void testSortByPriceHighToLow() {
        productsPage.sortBy("Price (high to low)");
        List<Double> prices = productsPage.getProductPricesList();
        List<Double> sortedDesc = prices.stream().sorted((a, b) -> Double.compare(b, a)).toList();
        Assert.assertEquals(prices, sortedDesc, "الأسعار لازم تكون مرتبة تنازليًا");
    }

    // ---------------- تفاصيل المنتج (Product Details) ----------------

    @Test(description = "TC_PROD_08: الضغط على منتج يفتح صفحة تفاصيله وبيانات صح")
    public void testOpenProductDetailsShowsCorrectData() {
        String productName = "Sauce Labs Backpack";
        productsPage.openProductDetails(productName);
        ProductDetailsPage detailsPage = new ProductDetailsPage(driver);

        Assert.assertEquals(detailsPage.getName(), productName, "اسم المنتج في صفحة التفاصيل لازم يطابق اللي ضغطنا عليه");
        Assert.assertTrue(detailsPage.getPrice().startsWith("$"), "السعر لازم يبدأ بعلامة $");
        Assert.assertFalse(detailsPage.getDescription().isEmpty(), "الوصف لازم يكون موجود ومش فاضي");
    }

    @Test(description = "TC_PROD_09: زرار Back to products بيرجع تاني لصفحة المنتجات")
    public void testBackButtonReturnsToProductsPage() {
        productsPage.openProductDetails("Sauce Labs Bike Light");
        ProductDetailsPage detailsPage = new ProductDetailsPage(driver);
        detailsPage.backToProducts();

        Assert.assertTrue(productsPage.isLoaded(), "بعد الضغط على Back لازم نرجع لصفحة Products");
    }
}
