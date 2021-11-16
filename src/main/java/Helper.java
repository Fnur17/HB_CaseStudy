import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.annotations.Test;
import java.io.IOException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

public class Helper  {

    protected final String dataFile = "./data.json";
    WebDriver driver;
    public String url;
    WebDriverWait wait;
    protected JSONObject data;
    public String productname;
    public String quantity;
    public String totalproduct;
    public String secondproductname;
    public String secondproductquantity;
    public String secondtotalproduct;



    public String randomEmail()
    {

        Random randomGenerator = new Random();
        double randomInt = randomGenerator.nextInt(1000);
        String email = ("username1" + randomInt + "@gmail.com");
        return email;

    }
    protected JSONObject loadDataFromJson() throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader(dataFile);

        return (JSONObject) jsonparser.parse(reader);
    }

    public Helper(WebDriver _driver, String _url, WebDriverWait _wait) {
        driver = _driver;
        url = _url;
        wait = _wait;
    }

    public void openUrl() throws InterruptedException {
        driver.get(url);
        driver.manage().window().maximize();
        Thread.sleep(5000);
    }

    public void registerAndLogin() throws InterruptedException {
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".login")));
    driver.findElement(By.cssSelector(".login")).click();
    Thread.sleep(7000);

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_create")));
        driver.findElement(By.id("email_create")).sendKeys(randomEmail());
        driver.findElement(By.id("SubmitCreate")).click();
        Thread.sleep(7000);
    }

    public void fillCustomerInfo() throws InterruptedException, IOException, ParseException {
        data = loadDataFromJson();
        driver.findElement(By.id("customer_firstname")).sendKeys(data.get("firstname").toString());
        driver.findElement(By.id("customer_lastname")).sendKeys(data.get("lastname").toString());
        driver.findElement(By.id("passwd")).sendKeys(data.get("password").toString());
        driver.findElement(By.id("address1")).sendKeys(data.get("address").toString());
        driver.findElement(By.id("city")).sendKeys(data.get("city").toString());
        driver.findElement(By.id("uniform-id_state")).click();
        driver.findElement(By.cssSelector("#id_state > option:nth-child(6)")).click();
        driver.findElement(By.id("postcode")).sendKeys(data.get("postcode").toString());
        driver.findElement(By.id("uniform-id_country")).click();
        driver.findElement(By.cssSelector("#id_country > option:nth-child(2)")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("phone_mobile")).sendKeys(data.get("phone").toString());
        Thread.sleep(3000);
        driver.findElement(By.id("submitAccount")).click();
        String userinfo = driver.findElement(By.xpath("/html/body/div/div[1]/header/div[2]/div/div/nav/div[1]/a/span")).getText();
        Thread.sleep(3000);
        if (userinfo.equals(data.get("firstname").toString() + " " + data.get("lastname").toString()))
        {
            //Dresses bölümü seçtirme
            WebElement e = driver.findElement(By.cssSelector("#block_top_menu > ul > li:nth-child(2) > a"));
            Actions actions = new Actions(driver);
            actions.moveToElement(e).perform();
            Thread.sleep(3000);
            //Dresses -> Summer Dreses bölümünün seçilmesi
            driver.findElement(By.cssSelector("#block_top_menu > ul > li:nth-child(2) > ul > li:nth-child(3) > a")).click();

            Thread.sleep(3000);
        }
        else
        {
          throw new InterruptedException("Kullanıcı adı ve soyadı uyumsuz");
        }

    }

    public void selectProduct() throws InterruptedException {

        // Summer Dresses sayfasından 2.olan ürünün seçilip eklenmesi ve bilgileri
        driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-line.last-item-of-tablet-line.last-mobile-line > div > div.right-block > h5 > a")).click();
        productname = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/div/div[3]/h1")).getText();
        quantity = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/div/div[4]/form/div/div[2]/p[1]/input")).getAttribute("value");
        totalproduct = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div/div/div[4]/form/div/div[1]/div[1]/p[1]/span")).getText();
        driver.findElement(By.cssSelector("#add_to_cart > button")).click();
        Thread.sleep(5000);

    }

    public void continueShopping() throws InterruptedException {
        driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > span")).click();

    }

    public void addProductFromSearch() throws InterruptedException {
        driver.findElement(By.id("search_query_top")).sendKeys(data.get("search").toString());
        driver.findElement(By.cssSelector("#searchbox > button")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#center_column > ul > li.ajax_block_product.col-xs-12.col-sm-6.col-md-4.last-in-line.first-item-of-tablet-line.last-item-of-mobile-line > div > div.right-block > h5 > a")).click();
        Thread.sleep(3000);
        secondproductname = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[4]/div/div/div/div[3]/h1")).getText();
        secondproductquantity = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[4]/div/div/div/div[4]/form/div/div[2]/p[1]/input")).getAttribute("value");
        secondtotalproduct = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[4]/div/div/div/div[4]/form/div/div[1]/div[1]/p[1]/span")).getText();
        driver.findElement(By.cssSelector("#add_to_cart > button")).click();
        Thread.sleep(5000);

    }

    public void checkout() throws InterruptedException {
        driver.findElement(By.cssSelector("#layer_cart > div.clearfix > div.layer_cart_cart.col-xs-12.col-md-6 > div.button-container > span")).click();
        Thread.sleep(3000);
        WebElement w = driver.findElement(By.cssSelector("#header > div:nth-child(3) > div > div > div:nth-child(3) > div > a"));
        Actions action = new Actions(driver);
        action.moveToElement(w).perform();
        Thread.sleep(3000);

        driver.findElement(By.cssSelector("#button_order_cart")).click();
        Thread.sleep(3000);
    }

    public void cart() throws InterruptedException {
        String cartfirstproductname = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[1]/td[2]/p/a")).getText();
        String cartfirstproductquantity = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[1]/td[5]/input[2]")).getAttribute("value");
        String cartfirsttotalproduct = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[1]/td[6]/span")).getText();
        String cartlastproductname = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]/td[2]/p/a")).getText();
        String cartlastproductquantity = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]/td[5]/input[2]")).getAttribute("value");
        String cartlasttotalproduct = driver.findElement(By.xpath("/html/body/div/div[2]/div/div[3]/div/div[2]/table/tbody/tr[2]/td[6]/span")).getText();


        if (productname.equals(cartfirstproductname) & quantity.equals(cartfirstproductquantity) & totalproduct.equals( cartfirsttotalproduct)) {
            if (secondproductname.equals(cartlastproductname) & secondproductquantity.equals(cartlastproductquantity) & secondtotalproduct.equals(cartlasttotalproduct)) {
                System.out.println("Ürün bilgileri eşleşmiştir");
            }
        } else {
            throw new InterruptedException("Ürün bilgilerinde yanlışlık var!!!");
        }

    }

    public void shipping () throws InterruptedException {
        driver.findElement(By.cssSelector("#center_column > p.cart_navigation.clearfix > a.button.btn.btn-default.standard-checkout.button-medium")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#center_column > form > p > button")).click();
        Thread.sleep(3000);
        driver.findElement(By.id("uniform-cgv")).click();
        driver.findElement(By.cssSelector("#form > p > button")).click();
        Thread.sleep(3000);
    }

    public void payment () throws InterruptedException {
        driver.findElement(By.cssSelector("#HOOK_PAYMENT > div:nth-child(1) > div > p > a")).click();
        driver.findElement(By.cssSelector("#cart_navigation > button")).click();
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("#header > div.nav > div > div > nav > div:nth-child(1) > a")).click();

    }

}
