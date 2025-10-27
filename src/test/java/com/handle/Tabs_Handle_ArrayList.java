package com.handle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Tabs_Handle_ArrayList {

	WebDriver driver;

	@Test(description = "Open mutliple tabs, and come back to the second tab")
	public void openBrowser() throws InterruptedException, IOException {
//		String path = System.getProperty("user.dir");
//		System.setProperty("webdriver.chrome.driver", path + "/Drivers/chromedriver.exe");
//		
		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();
		driver.manage().window().maximize();

		// Open first tab
		driver.get("https://central.sonatype.com/");
		List<String> tabHandles = new ArrayList<>();
		tabHandles.add(driver.getWindowHandle()); // Tab 1
		
		// Open Tab 2
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.tpointtech.com/");
        tabHandles.add(driver.getWindowHandle());

        // Open Tab 3
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://www.programiz.com/sql/online-compiler");
        tabHandles.add(driver.getWindowHandle());

        // Open Tab 4
        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://chatgpt.com/");
        tabHandles.add(driver.getWindowHandle());

        // Switch back to Tab 2
        driver.switchTo().window(tabHandles.get(1));
        System.out.println("Switched to Tab 2: " + driver.getTitle());

        Thread.sleep(2000);
        driver.quit();

	}
}