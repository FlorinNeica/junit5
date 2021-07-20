package junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Test1 {

	// @ParameterizedTest
	@EnumSource(Browser.class)
	public void testBrowser(Browser browser) throws Exception {

		WebDriver driver = null;

		switch (browser) {

		case CHROME:
			WebDriverManager.chromedriver()
					.setup();
			driver = new ChromeDriver();
			break;

		case FIREFOX:
			WebDriverManager.firefoxdriver()
					.setup();
			driver = new FirefoxDriver();
			break;

		default:
			throw new Exception("The browser" + browser + "is not supported");
		}

		driver.get("http://siit.epizy.com/index.php?route=account/login");

		WebElement btnLogin = driver.findElement(By.xpath("//input[@value= 'Login']"));

		Assertions.assertTrue(btnLogin.isDisplayed(), "Login button is not visible on login page");

	}

	@ParameterizedTest
	@ValueSource(strings = { "Raoul07", "raoul@gmail.com" })
	public void testLogin(String input) {

		WebDriverManager.chromedriver()
				.setup();
		WebDriver driver = new ChromeDriver();

		driver.get("http://siit.epizy.com/index.php?route=account/login");

		WebElement txtEmailAddress = driver.findElement(By.id("input-email"));

		txtEmailAddress.sendKeys(input);

		WebElement txtPassword = driver.findElement(By.id("input-password"));

		txtPassword.sendKeys("acasa1");

		WebElement btnLogin = driver.findElement(By.xpath("//input[@value='Login']"));

		btnLogin.click();

		WebElement lblValidationError = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']"));

		String actualErrorMsg = lblValidationError.getText();

		String expectedErrorMsg = "Warning: No match for E-Mail Address and/or Password.";

		Assertions.assertEquals(expectedErrorMsg, actualErrorMsg, "The validation error is incorrect");
	}
}
