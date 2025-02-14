package id.ac.ui.cs.advprog.eshop.functional;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
public class CreateProductFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    public void setUp() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    // Test the creation of a product and verify it appears in the product list with correct details
    @Test
    public void createProduct_success(ChromeDriver driver) {
        String createUrl = baseUrl + "/product/create";
        driver.get(createUrl);
        
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Test Product");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("10");

        driver.findElement(By.xpath("//button[@type='submit']")).click();
        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("/product/list"));

        WebElement productNameCell = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.xpath("//table//td[text()='Test Product']"))
        );
        assertNotNull(productNameCell);

        WebElement quantityCell = driver.findElement(
            By.xpath("//table//tr[td[text()='Test Product']]/td[2]")
        );
        assertEquals("10", quantityCell.getText());
    }
}
