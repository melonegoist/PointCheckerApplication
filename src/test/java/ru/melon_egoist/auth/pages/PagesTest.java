package ru.melon_egoist.auth.pages;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PagesTest {

    private WebDriver driver;

    @BeforeAll
    void setup() {
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        driver.manage().window().maximize();

        driver.get("http://localhost:5173/");
    }

    @BeforeEach
    void openLoginPage() {
        driver.get("http://localhost:5173/");
    }

    @AfterAll
    void tearDown() {
        driver.quit();
    }

    @Test
    @Order(1)
    void redirectToRegisterPage() {
        driver.findElement(By.id("corner_button")).click();
        Assertions.assertTrue(driver.getCurrentUrl().contains("/reg"));
    }


    @Test
    @Order(2)
    void togglePasswordVisibility() {
        WebElement passwordField = driver.findElement(By.id("password-field"));
        passwordField.sendKeys("secret_password");
        driver.findElement(By.id("lock-icon")).click();
        assertEquals("text", passwordField.getAttribute("type"));
    }

    @Test
    @Order(3)
    void successfulLoginDrawsGraph() {
        login("admin", "1234");
        WebElement canvas = driver.findElement(By.id("submit-button"));
        Assertions.assertTrue(canvas.isDisplayed());
    }

    @Test
    @Order(4)
    void logoutSession() {
        driver.findElement(By.id("corner_button")).click();

        WebElement button = driver.findElement(By.id("login-button"));

        Assertions.assertTrue(button.isDisplayed());
    }

    @Test
    @Order(5)
    void usernameMessageAppears() {
        login("admin", "1234");
        Assertions.assertTrue(driver.getPageSource().contains("Welcome, admin"));
    }

    @Test
    @Order(6)
    void emptyXShowsError() {
        driver.findElement(By.id("y-input")).sendKeys("2");
        driver.findElement(By.id("r-input")).sendKeys("3");

        WebElement sendButton = driver.findElement(By.id("submit-button"));
        sendButton.click();

        WebElement notAllowedGraph = driver.findElement(By.id("graph"));
        Assertions.assertFalse(notAllowedGraph.isDisplayed());
    }

    @Test
    @Order(7)
    void emptyYShowsError() {
        driver.findElement(By.id("x-input")).sendKeys("1");
        driver.findElement(By.id("r-input")).sendKeys("3");

        WebElement sendButton = driver.findElement(By.id("submit-button"));
        sendButton.click();

        WebElement notAllowedGraph = driver.findElement(By.id("graph"));

        Assertions.assertFalse(notAllowedGraph.isDisplayed());
    }

    @Test
    @Order(8)
    void emptyRShowsError() {
        driver.findElement(By.id("x-input")).sendKeys("2");
        driver.findElement(By.id("y-input")).sendKeys("3");

        WebElement sendButton = driver.findElement(By.id("submit-button"));
        sendButton.click();

        WebElement notAllowedGraph = driver.findElement(By.id("graph"));
        Assertions.assertFalse(notAllowedGraph.isDisplayed());
    }

    @Test
    @Order(9)
    void xOutsideRangeShowsError() {
        driver.findElement(By.id("x-input")).sendKeys("4");
        driver.findElement(By.id("submit-button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Wrong x"));
    }

    @Test
    @Order(10)
    void yOutsideRangeShowsError() {
        driver.findElement(By.id("y-input")).sendKeys("6");
        driver.findElement(By.id("submit-button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Wrong y"));
    }

    @Test
    @Order(11)
    void rOutsideRangeShowsError() {
        driver.findElement(By.id("r-input")).sendKeys("6");
        driver.findElement(By.id("submit-button")).click();

        Assertions.assertTrue(driver.getPageSource().contains("Wrong r"));
    }

    @Test
    @Order(12)
    void validDataDrawsGraph() {
        fillPointForm("1", "2", "3");

        WebElement graph = driver.findElement(By.id("graph"));

        Assertions.assertTrue(graph.isDisplayed());
    }

    @Test
    @Order(13)
    void showAllDotsDisplaysPreviousHits() {
        driver.findElement(By.id("show_dots_button")).click();
        WebElement table = driver.findElement(By.id("dashboard"));

        Assertions.assertTrue(table.isDisplayed());
    }

    @Test
    @Order(14)
    void sessionPersistsAfterReload() {
        driver.navigate().refresh();

        Assertions.assertTrue(driver.findElement(By.id("corner_button")).isDisplayed());
    }

    @Test
    @Order(15)
    void sendButtonColorChanges() {
        fillPointForm("1", "2", "3");

        WebElement button = driver.findElement(By.id("submit-button"));
        String color = button.getCssValue("background-color");
        Assertions.assertTrue(color.contains("green") || color.contains("red"));
    }

    private void login(String username, String password) {
        driver.findElement(By.id("login-field")).sendKeys(username);
        driver.findElement(By.id("password-field")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
    }

    private void fillPointForm(String x, String y, String r) {
        driver.findElement(By.id("x-input")).sendKeys(x);
        driver.findElement(By.id("y-input")).sendKeys(y);
        driver.findElement(By.id("r-input")).sendKeys(r);
        driver.findElement(By.id("submit-button")).click();
    }
}
