package com.usami_hiki.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SeleniumBase {

	private final String PATH = "driver/chromedriver.exe";

	protected WebDriver driver;
	protected WebDriverWait wait;

	 protected Logger log;

	public SeleniumBase() {

		log = LoggerFactory.getLogger(this.getClass());

		log.debug(PATH);
		System.setProperty("webdriver.chrome.driver", PATH);

		ChromeOptions options = new ChromeOptions();
		options.setExperimentalOption("useAutomationExtension", false);
		this.driver = new ChromeDriver(options);
		this.driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);

		this.wait = new WebDriverWait(driver, 10);

	}

	protected void executeJavaScript(String script, Object... var2) {
		((JavascriptExecutor) driver).executeScript(script, var2);
	}

	public void _exe() {

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
