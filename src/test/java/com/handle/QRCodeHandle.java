package com.handle;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import io.github.bonigarcia.wdm.WebDriverManager;

public class QRCodeHandle {
	
	public static void main(String[] args) throws IOException, NotFoundException, InterruptedException {
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver;
//		String path = System.getProperty("user.dir");
//		System.setProperty("webdriver.chrome.driver", path+"/Drivers/chromedriver.exe");
		
		
		
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		
		//driver.get("https://qrfy.com/");
		driver.get("https://idemia-mobile-id.com/testqr");
		String parent_window = driver.getWindowHandle();
		
		//generating a QR by sending the url's
		//driver.findElement(By.name("data.url")).sendKeys("https://www.tpointtech.com");
		Thread.sleep(2000);
		JavascriptExecutor js = (JavascriptExecutor)driver;
		js.executeScript("window.scrollBy(0,750)", "");
		
		//waiting for QR to render,  
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//js.executeScript("window.scrollBy(0,750)", "");
        //wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='MuiBox-root css-i12fn5']//div//canvas")));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[contains(@class,'loaded')]")));
		
        WebElement qrCodeElement = driver.findElement(By.xpath("//img[contains(@class,'loaded')]"));
        
        //AI/ML-Based CAPTCHA Solving 
        File qrCodeImage = qrCodeElement.getScreenshotAs(OutputType.FILE);
        
		//Decode the QR code using a library like ZXing
		BufferedImage bufferedimage = ImageIO.read(qrCodeImage);
		LuminanceSource source = new BufferedImageLuminanceSource(bufferedimage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));	
		Result result = new MultiFormatReader().decode(bitmap);
		
		//Random Text
		String randomText = randomtxt();
		FileUtils.copyFile(qrCodeImage, new File("C:\\Users\\kingr\\eclipse-workspace\\Test_WindowHandle\\Screenshots\\qr-code_"+randomText+".png"));
		
		//Print the decoded result
	    System.out.println("QR Code Text: "+result.getText());
		Thread.sleep(3000);
		
		//To see the QR Scan successfully done in new tab/window
		driver.switchTo().newWindow(WindowType.TAB);
		Thread.sleep(3000);
		//To see the QR Scan successfully done in same tab/window
		driver.switchTo().window(parent_window);
		driver.get(result.getText());
		Thread.sleep(1000);
		driver.quit();

	}

	private static String randomtxt() {
		return UUID.randomUUID().toString();
	}

}
