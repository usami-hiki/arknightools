package com.usami_hiki.selenium;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import lombok.Cleanup;
import lombok.Data;

public class SeleniumGifMake extends SeleniumBase {

	public static void main(String[] args) throws Exception {
		new SeleniumGifMake().exe();
	}

	public void exe() throws Exception {
		Params p = new Params();

		final String FOLDER = "m/";

		p.setDriver(driver);
		p.setR(new Robot());
		p.setClipboard(Toolkit.getDefaultToolkit().getSystemClipboard());

		@Cleanup
		Scanner scan = new Scanner(new File("./txt/all.txt"));
		String line;
		String files;

		while (scan.hasNextLine()) {
			line = String.format(FOLDER + "front/%s/%s", (Object[]) scan.nextLine().split(" "));
			files = String.format("\"%s\" \"%s\" \"%s\"",
					new File(line + ".png").getAbsolutePath(),
					new File(line + ".atlas").getAbsolutePath(),
					new File(line + ".skel").getAbsolutePath());

			log.debug(files);

			try {
				p.setParam(files);
				doeach(p);
			} catch (Exception e) {
				log.error(line);
			}

		}

		// p.setParam("\"C:\\Users\\かなた\\Downloads\\arknights\\model\\char_376_therex\\char_376_therex.atlas\"
		// \"C:\\Users\\かなた\\Downloads\\arknights\\model\\char_376_therex\\char_376_therex.png\"
		// \"C:\\Users\\かなた\\Downloads\\arknights\\model\\char_376_therex\\char_376_therex.skel\"");
		// doeach(p);

		Thread.sleep(2000);
		p.getDriver().quit();
	}

	public void doeach(Params p) throws Exception {
		final String URL = "https://naganeko.github.io/chibi-gif/";
		p.getDriver().get(URL);

		// Wait<WebDriver> wait = new WebDriverWait(driver, 10);
		// wait.until(visibilityOfElementLocated(By.className("submitBtn")));
		WebDriverWait wait = new WebDriverWait(p.getDriver(), 10);

		WebElement addSkeleton = p.getDriver().findElement(By.id("fileSelect"));
		wait.until(ExpectedConditions.elementToBeClickable(addSkeleton));
		addSkeleton.click();

		Thread.sleep(5000);
		StringSelection stringSelection = new StringSelection(p.getParam());
		p.getClipboard().setContents(stringSelection, stringSelection);
		p.getR().keyPress(KeyEvent.VK_CONTROL);
		p.getR().keyPress(KeyEvent.VK_V);
		p.getR().keyRelease(KeyEvent.VK_V);
		p.getR().keyRelease(KeyEvent.VK_CONTROL);

		p.getR().keyPress(KeyEvent.VK_ENTER);
		p.getR().keyRelease(KeyEvent.VK_ENTER);

		Thread.sleep(2000);

		Select dousaList = new Select(p.getDriver().findElement(By.id("dousa")));
		ArrayList<String> dousaArray = new ArrayList<>();
		for (WebElement elem : dousaList.getOptions()) {
			dousaArray.add(elem.getText().trim());
			log.debug(elem.getText().trim());
		}

		StringBuilder sb = new StringBuilder();

		sb.append(this.checkListRetString(dousaArray, "Start"));
		sb.append(this.checkListRetString(dousaArray, "Idle"));
		sb.append(this.checkListRetString(dousaArray, "Attack_Begin"));
		sb.append(this.checkListRetString(dousaArray, "Attack"));
		sb.append(this.checkListRetString(dousaArray, "Attack"));
		sb.append(this.checkListRetString(dousaArray, "Attack_Loop"));
		sb.append(this.checkListRetString(dousaArray, "Attack_Loop"));
		sb.append(this.checkListRetString(dousaArray, "Attack_End"));
		sb.append(this.checkListRetString(dousaArray, "Die"));

		WebElement motionList = p.getDriver().findElement(By.id("motion_list"));
		motionList.sendKeys(sb.toString());

		log.debug(sb.toString());

		WebElement stepBy = p.getDriver().findElement(By.id("step_by"));
		stepBy.clear();
		stepBy.sendKeys("100");

		WebElement splitBtn = p.getDriver().findElement(By.id("splitBtn"));
		splitBtn.click();

		// #sshot>div.frame1
		p.getDriver().findElement(By.cssSelector("#sshot>div.frame1"));

		WebElement delay = p.getDriver().findElement(By.id("delay"));
		delay.clear();
		delay.sendKeys("100");

		WebElement genGIFbtn = p.getDriver().findElement(By.id("genGIFbtn"));
		genGIFbtn.click();

		p.getDriver().findElement(By.cssSelector("div#gifhere>img"));

		WebElement saveGifBtn = p.getDriver().findElement(By.id("saveGifBtn"));
		saveGifBtn.click();
		Thread.sleep(1000);

		// new WebDriverWait(p.getDriver(), 10).until((WebDriver d) ->
		// d.getTitle().toLowerCase().startsWith("selenium"));

		// System.out.println("Page title is: " + driver.getTitle());
	}

	private String checkListRetString(List<String> list, String keyword) {
		if (list.contains(keyword)) {
			return keyword + "\r\n";
		}
		return "";
	}

	@Data
	private class Params {
		private WebDriver driver;
		private Robot r;
		private Clipboard clipboard;
		private String param;
	}
}
