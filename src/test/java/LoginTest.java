import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class LoginTest {

//  private static final String baseUrl = "https://codeready-oandriie-code.apps.che-dev.x6e0.p1.openshiftapps.com/";
  private static final String baseUrl = "https://che-oandriie-code.apps.che-dev.x6e0.p1.openshiftapps.com/";
  private static final String githubLogin = "https://github.com/login";
  public WebDriver driver;
  private static final String loginBtnText = "github-app-sre";

  private static final String githubUserName = "===";
  private static final String githubPassword = "===";
  private static final String CODE = "===";

  @Test
  public void test() {
    WebDriverManager.chromedriver().setup();
    // Create driver object for CHROME browser
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    driver.manage().window().maximize();
    driver.get(baseUrl);
    String URL= driver.getCurrentUrl();
    System.out.print(URL);

    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(loginBtnText)));

    driver.switchTo().newWindow(WindowType.WINDOW);
    driver.get(githubLogin);
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("login_field")));
    driver.findElement(By.id("login_field")).sendKeys(githubUserName);
    driver.findElement(By.id("password")).sendKeys(githubPassword);
    driver.findElement(By.name("commit")).click();
    wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("otp")));
    driver.findElement(By.id("otp")).sendKeys(CODE);
    driver.findElement(By.xpath("//button[text()=\"Verify\"]")).click();

    driver.switchTo().window(new ArrayList<>(driver.getWindowHandles()).get(0));

    try {
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(loginBtnText)));
        driver.findElement(By.linkText(loginBtnText)).click();
        waitCheAndLogOut();
//        waitCodeReadyAndLogOut();
    } catch (RuntimeException e) {
      System.out.println(e.getCause().toString());
    }
  }

  public void waitCodeReadyAndLogOut() {
    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("main-content")));

    driver.findElement(By.id("user-name")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()=\" Logout\"]")));
    driver.findElement(By.xpath("//span[text()=\" Logout\"]")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(loginBtnText)));
  }

  public void waitCheAndLogOut() {
    WebDriverWait wait = new WebDriverWait(driver, 30);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("ui-container")));

    driver.findElement(By.id("pf-dropdown-toggle-id-1")).click();
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()=\"Logout\"]")));
    driver.findElement(By.xpath("//button[text()=\"Logout\"]")).click();

    wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(loginBtnText)));
  }

  @BeforeTest
  public void beforeTest() {
    System.out.println("before test");
  }

  @AfterTest
  public void afterTest() {
    driver.quit();
    System.out.println("after test");
  }
}
