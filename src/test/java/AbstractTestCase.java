import io.github.bonigarcia.wdm.WebDriverManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Random;

public abstract class AbstractTestCase {
    protected final String dataFile = "./data.json";
    protected JSONObject data;
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected Helper test;


    @BeforeClass
    public void setUp() throws IOException, ParseException {
        data = loadDataFromJson();
        initWebDriver();
        wait = new WebDriverWait(driver, 30);
        test = new Helper(driver, data.get("url").toString(),wait);
    }


    @AfterClass
    public void tearDown() {
        driver.quit();
    }
    protected JSONObject loadDataFromJson() throws IOException, ParseException {
        JSONParser jsonparser = new JSONParser();
        FileReader reader = new FileReader(dataFile);

        return (JSONObject) jsonparser.parse(reader);
    }


    protected void initWebDriver() throws MalformedURLException {
        String driverName = System.getenv("DRIVER");
        if (null == driverName || "chrome".equals(driverName)) {
            // if environment variables empty run with default chromedriver
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
            System.out.println("Default Chrome Driver created");
            return;
        }
        if ("firefox".equals(driverName)){
            WebDriverManager.firefoxdriver().setup();
            driver = new FirefoxDriver();
            System.out.println("Firefox Driver created");
        }

    }
}
