package ru.netology.web;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import java.awt.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CardOrderTest {
    private WebDriver driver;
    @BeforeAll
    public static void  setUpAll(){
        System.setProperty("webdriver.chrome.driver","./driver/win/chromedriver.exe");
    }
    @BeforeEach
   public  void setUp(){
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);

    }

    @AfterEach
    public void tearDown(){
        driver.quit();
        driver = null;
    }


    @Test
    public void shouldSendForm(){
        driver.get("http://localhost:9999/");
       List<WebElement> textFields = driver.findElements (By.className("input__control"));
       textFields.get(0).sendKeys("Роман Козин");
       textFields.get(1).sendKeys("+79236064545");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button__content")).click();
        String actualText = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText().trim();
        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        assertEquals(expected, actualText, "Заявочка не прошла");


    }
}