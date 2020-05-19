package com.usami_hiki.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumMain {

	public static void main(String[] args) {
		final String PATH = "driver/chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", PATH);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		WebDriver driver = new ChromeDriver(options);

		WebDriverWait wait = new WebDriverWait(driver, 10);

		final String URL = "http://www.google.com";
		driver.get(URL);

		WebElement element = driver.findElement(By.name("q"));
		element.sendKeys("selenium");
		element.submit();

		wait.until((WebDriver d) -> d.getTitle().toLowerCase().startsWith("selenium"));

		System.out.println("Page title is: " + driver.getTitle());
		driver.quit();
	}
}