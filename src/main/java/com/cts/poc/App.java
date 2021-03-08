package com.cts.poc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.itextpdf.text.DocumentException;

/**
 * 
 * @author 877962
 *
 */
public class App {

	private static GeneratePDF newPDF = new GeneratePDF();
	private static InputStream reader = ClassLoader.getSystemClassLoader().getResourceAsStream("info.properties");
	private static Properties p = new Properties();

	public static void main(String[] args) {

		try {
			// Loading Properties file data

			p.load(reader);
			System.out.println(p.getProperty("introMsg"));
			System.out.println();

			// Generates Password Protected PDF File
			newPDF.generateNewPDF();

		} catch (IOException e) {

			System.out.println(p.getProperty("pdfFileError"));

		} catch (DocumentException e) {
			System.out.println(e.getMessage());
		}

	}

}
