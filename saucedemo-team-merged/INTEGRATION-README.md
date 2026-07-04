# إزاي هذا المشروع مبني

ده نسخة من مشروع الفريق (`SheTest-master` بتاع Tester 1 - Login) بعد ما ضفت جواه شغلك
(Tester 2 - Products) بنفس الأسلوب والتنظيم بتاعهم، بحيث لو التيم ليدر دمجت شغل الكل
مع بعض، الكود يتماشى من غير تعارض.

## الفروق عن النسخة اللي كانت عندك قبل كده

| قبل | بعد (دلوقتي) |
|---|---|
| كل تست بيفتح ويقفل المتصفح بنفسه (كود مكرر) | فيه كلاس واحد مشترك `BaseTest` بيعمل فتح/قفل المتصفح لكل التستات |
| `ProductsTest` مستقل بذاته | `ProductsTest extends BaseTest` — يعني بيرث فتح/قفل المتصفح جاهز |
| `LoginPage.login()` كانت بترجع `ProductsPage` جاهزة | `LoginPage.login()` هنا بترجع `void` (زي أسلوب الفريق) — فبعد تسجيل الدخول بنعمل `new ProductsPage(driver)` بنفسنا |
| Java 11 | Java 21 (زي إعداد الفريق في `pom.xml`) |

## الملفات الجديدة اللي ضفتها

- `src/main/java/pages/ProductsPage.java` ← شغلك (عرض المنتجات + Sorting)
- `src/main/java/pages/ProductDetailsPage.java` ← شغلك (تفاصيل المنتج)
- `src/test/java/tests/ProductsTest.java` ← 9 test cases بتاعتك

## الملفات اللي جت من مشروع الفريق زي ما هي

- `pom.xml`
- `src/main/java/pages/LoginPage.java` (بتاع Tester 1)
- `src/test/java/tests/BaseTest.java` (مشترك)
- `src/test/java/tests/LoginTest.java` (بتاع Tester 1)

## طريقة التشغيل

نفس الطريقة اللي اتعودتي عليها:
1. فكي الـ zip
2. Open في IntelliJ → اختاري الفولدر
3. Trust Project → استني Maven يحمل المكتبات
4. افتحي `src/test/java/tests/ProductsTest.java`
5. دوسي السهم الأخضر ▶️ جنب `public class ProductsTest`

هيشغل الـ 9 تستات بتاعتك، وكل واحدة هتسجل دخول لوحدها الأول (لأن كل تست بيبدأ بمتصفح جديد وفاضي).

## لو عايزة تشغلي تستات Login والـ Products مع بعض في مرة واحدة

كليك يمين على فولدر `tests` → **Run 'All Tests'**، وهيشغل `LoginTest` و `ProductsTest` مع بعض (14 تست في المجموع).
