package com.usami_hiki.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SeleniumMain extends SeleniumBase {

	public static void main(String[] args) {
		new SeleniumMain().exe();
	}

	public void exe() {

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