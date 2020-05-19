package com.usami_hiki.selenium;

import java.io.File;
import java.net.URLDecoder;
import java.util.Scanner;

public class DecodeSample {

	public static void main(String[] args) throws Exception {

		Scanner scan = new Scanner(new File("input.txt"));

		while(scan.hasNextLine()) {
			System.out.println(URLDecoder.decode( scan.nextLine(), "EUC-JP"));
		}


	}

}
