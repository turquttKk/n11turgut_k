import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;


public class N11 {

    public WebDriver driver;
    public String urunismi;


    @BeforeTest

    public void createDriver() {

        System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver.exe");

        driver = new FirefoxDriver();
        driver.manage().window().maximize();


    }


    //1  sayfa dogrulama

    @Test(priority = 1)

    public void test1() {


        driver.get("https://www.n11.com");

        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("https://www.n11.com/"));


    }


    //2   login
    @Test(priority = 2)
    public void test2() throws InterruptedException {


        driver.findElement(By.xpath("//a[contains(text(),'Giriş Yap')]")).click();
        Thread.sleep(5000);

        WebElement username = driver.findElement(By.xpath("//input[@id='email']"));
        username.sendKeys("test1234test@gmail.com");

        Thread.sleep(2000);

        WebElement password = driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("123456a");

        Thread.sleep(2000);
        driver.findElement(By.id("loginButton")).click();


        Thread.sleep(2000);
        String name = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/header[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/a[2]")).getText();
        Assert.assertNotEquals(name, "Giriş Yap", "geçti");

    }


    //3    arama çubuğuna samsung yaz ve tıkla sayfayı asagı kaydır
    @Test(priority = 3)
    public void test3() throws InterruptedException {


        Thread.sleep(5000);
        WebElement searchbar = driver.findElement(By.xpath("//input[@id='searchData']"));
        searchbar.sendKeys("samsung");
        driver.findElement(By.xpath("//a[@title='ARA']")).click();


        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("window.scrollBy(0,500)");

        String URL = driver.getCurrentUrl();
        Assert.assertTrue(URL.contains("https://www.n11.com/arama?q=samsung"));

    }


    //4  samsung için ürün buldugunu yaz
    @Test(priority = 4)
    public void test4() {


        List<WebElement> elements = driver.findElements(By.xpath("(//div[@class='pro'])[1]"));
        for (WebElement e : elements) {
            System.out.println(e.getAttribute("innerText"));
        }

        String name1 = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/section[2]/div[1]/div[3]/h1[1]")).getText();
        Assert.assertEquals(name1, "Samsung,", "geçti");

    }


    //5  sayfa 2ye  geldiğini kontrol edip tıklama
    @Test(priority = 5)
    public void test5() {


        if (!driver.findElements(By.xpath("(//a[normalize-space()='2'])[1]")).isEmpty()) {
            driver.findElement(By.xpath("(//a[normalize-space()='2'])[1]")).click();
            System.out.println("2. sayfaya geçildi.");
        } else {
            System.out.println("2. sayfa yok");
        }

    }


    //6 3.sıradaki ürünü fav ekle

    @Test(priority = 6)
    public void test6() throws InterruptedException {


        Thread.sleep(2000);
        driver.findElement(By.xpath("(//span[@title='Favorilere ekle'])[3]")).click();

        urunismi = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/section[2]/div[2]/ul[1]/li[3]/div[1]/div[1]/a[1]/h3[1]")).getText();
        System.out.println("seçilen 3. ürünün ismi: " + urunismi);
    }


    //7  hesabım dan favorilere tıkla

    @Test(priority = 7)
    public void test7() throws InterruptedException {


        Thread.sleep(2000);
        driver.findElement(By.xpath("//span[@id='btnScrollTop']")).click();

        Thread.sleep(2000);
        WebElement hesabim = driver.findElement(By.xpath("//div[@class='myAccount']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(hesabim).perform();
        Thread.sleep(2000);
        WebElement altmenu = driver.findElement(By.xpath("//a[@title='Favorilerim / Listelerim']"));
        actions.moveToElement(altmenu).perform();
        driver.findElement(By.xpath("(//a[normalize-space()='Favorilerim / Listelerim'])[1]")).click();
    }


    //8   favoriye alınan ürünle listedeki ürün kontrolü

    @Test(priority = 8)
    public void test8() {


        driver.findElement(By.cssSelector("li[class='wishGroupListItem favorites'] h4[class='listItemTitle']")).click();

        String favouriteProductName = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[1]/div[2]/div[2]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[1]/a[1]/h3[1]")).getText();
        System.out.println(favouriteProductName);
        if (favouriteProductName.equals(urunismi)) {
            System.out.println("Seçilen ürun favori listesine eklendi;" + urunismi);
        } else {
            System.out.println("Yanlış urun favoriye, EKLENEMEDİ");
        }

    }


// 9 Favoriden ürünü kaldırma

    @Test(priority = 9)
    public void test9() {

        driver.findElement(By.xpath("(//span[@class='deleteProFromFavorites'][normalize-space()='Sil'])[1]")).click();
        driver.findElement(By.xpath("//span[@class='btn btnBlack confirm']")).click();

    }


    // 10 Favori ürünü kaldırma kontrolü favori listesi boş

    @Test(priority = 10)
    public void test10() {


        WebElement checkProductDelete = driver.findElement(By.xpath("//span[text()='Ürününüz listeden silindi.']"));
        if (checkProductDelete != null) {
            System.out.println("Favori listesinde ürün bulunmamaktadır");

        } else {
            System.out.println("İstediğiniz ürün silinemedi");

        }

    }

    @AfterTest
    public void finish() {
        try {
            driver.quit();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


}








