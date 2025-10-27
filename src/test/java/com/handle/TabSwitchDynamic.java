package com.handle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TabSwitchDynamic {

	WebDriver driver;
	@Test(description = "Open mutliple tabs using list, and come back to the second tab")
	public void openBrowser() throws InterruptedException, IOException {
//		String path = System.getProperty("user.dir");
//		System.setProperty("webdriver.chrome.driver", path + "/Drivers/chromedriver.exe");
//		
		WebDriverManager.chromedriver().setup();

		driver = new ChromeDriver();
		driver.manage().window().maximize();
	
		List<String> urls = Arrays.asList(
	            "https://central.sonatype.com/",
	            "https://www.tpointtech.com/",
	            "https://www.programiz.com/sql/online-compiler",
	            "https://chatgpt.com/"
	        );

	        List<String> handles = new ArrayList<>();

	        for (int i = 0; i < urls.size(); i++) {
	            if (i == 0) {
	                driver.get(urls.get(i));
	            } else {
	                driver.switchTo().newWindow(WindowType.TAB);
	                driver.get(urls.get(i));
	            }
	            handles.add(driver.getWindowHandle());
	        }

	        
	        // Switch back to second tab (index 1)  "tab1 = 0"
	        driver.switchTo().window(handles.get(1));
	        System.out.println("Switched to second tab: " + driver.getTitle());

	        Thread.sleep(2000);
	        driver.quit();

	}
}
