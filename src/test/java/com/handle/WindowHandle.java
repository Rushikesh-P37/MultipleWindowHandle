package com.handle;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

public class WindowHandle {

	WebDriver driver;

	@Test(description = "Open the parent URL in the browser, navigate to the child window, and then return to the parent window.")
	public void openBrowser() throws InterruptedException, IOException {
		String path = System.getProperty("user.dir");
		System.setProperty("webdriver.chrome.driver", path + "/Drivers/chromedriver.exe");

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://central.sonatype.com/");
		String parent_window = driver.getWindowHandle();

		driver.switchTo().newWindow(WindowType.TAB); // get new to window
		driver.get("https://www.javatpoint.com/");

		String copiedText = "";

		Set<String> tabs = driver.getWindowHandles();
		Iterator<String> itr = tabs.iterator();

		while (itr.hasNext()) {
			String child_window = itr.next();

			if (!parent_window.equals(child_window)) {
				driver.switchTo().window(child_window);
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
				//takeScreenshot();
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Java"))).click();
				WebElement element = driver.findElement(By.xpath("//div[@id=\"bottomnextup\"]/../h1"));
				copiedText = element.getText();
				takeScreenshot();
				System.out.println("Extracted Text: " + copiedText);
				driver.close();
			}
		}
		driver.switchTo().window(parent_window);
		WebElement textfiled = driver.findElement(By.xpath("(//input[@role=\"searchbox\"])[2]"));
		textfiled.sendKeys(copiedText);
		Thread.sleep(2000);
		takeScreenshot();
		textfiled.sendKeys(Keys.ARROW_DOWN, Keys.ENTER);
		System.out.println("Pasted Text: " + copiedText);

	}

	@AfterTest
	public void takeScreenshot() throws IOException {
		if (driver != null) {
			File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			String randomText = randomtxt();

			FileUtils.copyFile(file, new File(
					"C:\\Users\\kingr\\eclipse-workspace\\Test_Selenium\\Screenshots\\" + randomText + ".png"));

		} else {
			System.out.println("WebDriver is not initialized.");
		}
	}

	public String randomtxt() {
		return UUID.randomUUID().toString();
	}

	@AfterTest
	public void tearDown() {
		if (driver != null) {
			driver.quit(); // Close the browser after the test
		}
	}

}
