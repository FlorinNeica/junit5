package junit5;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

//@TestMethodOrder(MethodOrderer.Random.class)
// @TestMethodOrder(MethodOrderer.DisplayName.class)
// @TestMethodOrder(MethodOrderer.MethodName.class)
// @TestMethodOrder(MethodOrderer.OrderAnnotation.class)

@Execution(ExecutionMode.SAME_THREAD)
public class Test2 {

	WebDriver driver;

	@ParameterizedTest
	// @Order(2)
	@EnumSource(value = Browser.class, names = { "CHROME", "FIREFOX" })
	@DisplayName("Test that checks that the Login button is displayed on the login form")
	// @RepeatedTest(5)
	public void testBrowser(Browser browser) throws Exception {

		System.out.println(Thread.currentThread()
				.getName());
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

		driver.quit();

	}

	@Test
	// @RepeatedTest(5)
	// @Order(1)
	// @ValueSource(strings = { "Raoul07", "raoul@gmail.com" })
	@DisplayName("Test that checks the validation message displayed when using incorrect credentials")
	public void testLogin() {

		System.out.println(Thread.currentThread()
				.getName());

		WebDriverManager.chromedriver()
				.setup();
		WebDriver driver = new ChromeDriver();

		driver.get("http://siit.epizy.com/index.php?route=account/login");

		WebElement txtEmailAddress = driver.findElement(By.id("input-email"));

		txtEmailAddress.sendKeys("raoul@gmail.com");

		WebElement txtPassword = driver.findElement(By.id("input-password"));

		txtPassword.sendKeys("acasa1");

		WebElement btnLogin = driver.findElement(By.xpath("//input[@value='Login']"));

		btnLogin.click();

		WebElement lblValidationError = driver.findElement(By.xpath("//div[@class='alert alert-danger alert-dismissible']"));

		String actualErrorMsg = lblValidationError.getText();

		String expectedErrorMsg = "Warning: No match for E-Mail Address and/or Password.";

		Assertions.assertEquals(expectedErrorMsg, actualErrorMsg, "The validation error is incorrect");

		driver.quit();
	}

}
