package com.usami_hiki.selenium;

import java.io.File;
import java.net.URLEncoder;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SeleniumReplace extends SeleniumBase {

	public static void main(String[] args) throws Exception {
		new SeleniumReplace().exe();
	}

	public void exe() throws Exception {

		Scanner scan = new Scanner(new File("./txt/input.txt"));

		while (scan.hasNextLine()) {

			String url = "https://arknights.wikiru.jp/index.php?cmd=edit&page=" + URLEncoder.encode(scan.nextLine(), "EUC-JP");
//			String url = "https://arknights.wikiru.jp/index.php?cmd=edit&page="
//					+ URLEncoder.encode("SandBox/キャラ詳細ページ", "EUC-JP");

			log.debug(url);
			get(url);

		}

		driver.quit();
	}

	public void get(String url) throws Exception {
		driver.get(url);

		WebElement textarea = driver.findElement(By.cssSelector("textarea[name='msg']"));
		String text = textarea.getText();

		log.debug(text);
		if (!text.contains("モーション.gif※サイズの大きなファイルです")) {
			return;
		}

		textarea.clear();
		//textarea.sendKeys(textarea.getText().replaceAll("モーション.gif※サイズの大きなファイルです", "モーション.gif"));
		executeJavaScript(String.format("$(\"textarea[name='msg']\").val(`%s`)",
				text.replaceAll("モーション.gif※サイズの大きなファイルです", "モーション.gif")));

		WebElement element = driver.findElement(By.cssSelector("input[name='write']"));
		element.click();

		//wait.until((WebDriver d) -> d.getTitle().toLowerCase().startsWith("selenium"));
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("body")));

		log.debug("Page title is: " + driver.getTitle());
	}
}