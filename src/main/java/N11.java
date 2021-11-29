import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;



import java.util.List;
import java.util.concurrent.TimeUnit;


public class N11 {

    public static void main(String[] args)throws InterruptedException {



        System.setProperty("webdriver.gecko.driver", "./src/main/resources/geckodriver.exe");
        WebDriver driver = new FirefoxDriver();

//1    n11 gidiş işlemeleri ve dogrulama

        driver.get("https://www.n11.com");
        driver.manage().window().maximize();


        String Actual = driver.getCurrentUrl();
        String Expected = "https://www.n11.com/";
        if (Actual.equals(Expected)) {
            System.out.println("N11 Anasayfa açıldı!");
        } else {
            System.out.println("N11 Anasayfa açılamadı");
        }


//2   login

        driver.findElement(By.xpath("//a[@title='Giriş Yap']")).click();
        Thread.sleep(5000);

        WebElement username=driver.findElement(By.xpath("//input[@id='email']"));
        username.sendKeys("test1234test@gmail.com");

        Thread.sleep(2000);

        WebElement password=driver.findElement(By.xpath("//input[@id='password']"));
        password.sendKeys("123456a");

        Thread.sleep(2000);
        driver.findElement(By.id("loginButton")).click();




//3    arama çubuğuna samsung yaz ve tıkla sayfayı asagı kaydır

        Thread.sleep(5000);
        WebElement searchbar=driver.findElement(By.xpath("//input[@id='searchData']"));
        searchbar.sendKeys("iphone");
        driver.findElement(By.xpath("//a[@title='ARA']")).click();


        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("window.scrollBy(0,500)");


//4  samsung için ürün buldugunu yaz


       List <WebElement> elements = driver.findElements(By.xpath("(//div[@class='pro'])[1]"));
        for (WebElement e: elements)
        {
          System.out.println(e.getAttribute("innerText"));
        }



//5  sayfa 2ye  geldiğini kontrol edip tıklama

        if(!driver.findElements(By.xpath("(//a[normalize-space()='2'])[1]")).isEmpty()){
            driver.findElement(By.xpath("(//a[normalize-space()='2'])[1]")).click();
            System.out.println("2. sayfaya geçildi.");
        }else{
            System.out.println("2. sayfa yok");
        }





//6 3.sıradaki ürünü fav ekle

        Thread.sleep(2000);
        driver.findElement(By.xpath("(//span[@title='Favorilere ekle'])[3]")).click();

        String urunismi = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[3]/div[1]/div[1]/div[2]/section[2]/div[2]/ul[1]/li[3]/div[1]/div[1]/a[1]/h3[1]")).getText();
        System.out.println("seçilen 3. ürünün ismi: " + urunismi);




//7  hesabım dan favorilere tıkla
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



//8   favoriye alınan ürünle listedeki ürün kontrolü

        driver.findElement(By.cssSelector("li[class='wishGroupListItem favorites'] h4[class='listItemTitle']")).click();

        String favouriteProductName = driver.findElement(By.xpath("/html[1]/body[1]/div[1]/div[5]/div[1]/div[2]/div[2]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[1]/a[1]/h3[1]")).getText();
        System.out.println(favouriteProductName);
        if (favouriteProductName.equals(urunismi)) {
            System.out.println("Seçilen ürun favori listesine eklendi;" + urunismi);
        } else {
            System.out.println("Yanlış urun favoriye, EKLENEMEDİ");
        }




// 9 Favoriden ürünü kaldırma

        driver.findElement(By.xpath("(//span[@class='deleteProFromFavorites'][normalize-space()='Sil'])[1]")).click();
        driver.findElement(By.xpath("//span[@class='btn btnBlack confirm']")).click();


// 10 Favori ürünü kaldırma kontrolü favori listesi boş

        WebElement checkProductDelete = driver.findElement(By.xpath("//span[text()='Ürününüz listeden silindi.']"));
        if (checkProductDelete != null) {
            System.out.println("Favori listesinde ürün bulunmamaktadır");

        } else {
            System.out.println("İstediğiniz ürün silinemedi");

        }

        try {driver.quit();}

        catch (Exception exception) {
            exception.printStackTrace();}

    }
}







