package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CardOrderTest {
    private WebDriver driver;

    @BeforeAll
    static void setupClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldSendForm() {
        driver.get("http://localhost:9999/");
        List<WebElement> textFields = driver.findElements(By.className("input__control"));
        textFields.get(0).sendKeys("Роман Козин");
        textFields.get(1).sendKeys("+79236064545");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actualText = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText, "Заявочка не прошла");
    }

    @Test
    public void shouldShowErrorNotificationInTheFirstAndLastNameField() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.className("input__control")).sendKeys("qqq");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actualText = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        assertEquals(expected, actualText);
    }

    @Test
    public void shouldShowErrorNotificationInTheFirstAndLastNameFieldEmpty() {
        driver.get("http://localhost:9999/");
        driver.findElement(By.className("input__control")).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actualText = driver.findElement(By.className("input__sub")).getText().trim();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }

    @Test
    public void shouldShowErrorNotificationInTheTelephoneNumberField() {
        driver.get("http://localhost:9999/");
        List<WebElement> textFieldsControl = driver.findElements(By.className("input__control"));
        List<WebElement> textFieldsSub = driver.findElements(By.className("input__sub"));
        textFieldsControl.get(0).sendKeys("Роман Козин");
        textFieldsControl.get(1).sendKeys("34324324");
        driver.findElement(By.className("button__content")).click();
        String actualText = textFieldsSub.get(1).getText().trim();
        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        assertEquals(expected, actualText);
    }

    @Test
    public void shouldShowErrorNotificationInTheTelephoneNumberFieldEmpty() {
        driver.get("http://localhost:9999/");
        List<WebElement> textFields = driver.findElements(By.className("input__sub"));
        driver.findElement(By.className("input__control")).sendKeys("Роман Козин");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actualText = textFields.get(1).getText();
        String expected = "Поле обязательно для заполнения";
        assertEquals(expected, actualText);
    }
}