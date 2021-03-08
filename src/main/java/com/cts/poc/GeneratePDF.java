package com.cts.poc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * 
 * @author 877962
 *
 */
public class GeneratePDF {

	private String userPassword;
	private String filePathName;
	private String fileContent;

	private InputStream reader = ClassLoader.getSystemClassLoader().getResourceAsStream("info.properties");
	private Properties p = new Properties();

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public void setFilePathName(String filePathName) {
		this.filePathName = filePathName;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void getData() {
		Scanner scanner = new Scanner(System.in);
		try {

			// Loading Properties file data

			p.load(reader);

			System.out.println(p.getProperty("enterPassword"));
			// Loops until valid password is provided by user
			while (true) {

				userPassword = scanner.nextLine();
				if (isValidPasswordOrFileName("passwordPattern", userPassword)) {
					break;
				}
				System.out.println(p.getProperty("reenterPass"));
				System.out.println();
				System.out.println(p.getProperty("conditions"));
			}

			System.out.println(p.getProperty("fileContent"));
			// Loops until valid data is provided by user
			while (true) {
				fileContent = scanner.nextLine();
				if (fileContent != null && fileContent.length() > 0) {
					break;
				}
				System.out.println(p.getProperty("mandatory"));
			}

			// Getting Complete FileName
			System.out.println(p.getProperty("fileName"));
			while (true) {
				filePathName = scanner.nextLine();
				if (isValidPasswordOrFileName("fileNamePattern", filePathName)) {
					break;
				}
				System.out.println(p.getProperty("fileNameError"));
			}

			scanner.close();
		} catch (IOException e) {

			System.out.println("Property File Not Found");
		}
	}

	public void generateNewPDF() throws DocumentException, IOException {

		// Get required PDF file information

		this.getData();

		// Password Protected PDF File Generation

		OutputStream file = new FileOutputStream(new File(filePathName));
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, file);

		writer.setEncryption(userPassword.getBytes(), null, PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
		document.open();
		document.add(new Paragraph(fileContent));

		document.close();
		file.close();
		System.out.println(p.getProperty("success"));

	}

	public boolean isValidPasswordOrFileName(String patternType, String text) throws IOException {

		// Regex to check valid password.

		String regex = p.getProperty(patternType);

		Pattern p = Pattern.compile(regex);

		// If the password is empty
		// return false
		if (text == null) {
			return false;
		}

		Matcher m = p.matcher(text);

		// Return true if the password
		// matched the ReGex
		return m.matches();
	}

	/*
	 * public void modifyPDF() throws IOException, DocumentException { // Get
	 * required PDF file information
	 * 
	 * this.getData();
	 * 
	 * // Password Protected PDF File Generation OutputStream tempFile = new
	 * FileOutputStream("sample.pdf", true); Document doc = new Document();
	 * PdfReader.unethicalreading = true; PdfReader reader = new
	 * PdfReader(filePathName); PdfStamper stamper = new PdfStamper(reader,
	 * tempFile); stamper.close(); reader.close(); PdfWriter writer =
	 * PdfWriter.getInstance(doc, tempFile); doc.open(); doc.add(new
	 * Paragraph(fileContent)); doc.close(); tempFile.close();
	 * 
	 * reader = new PdfReader("sample.pdf"); stamper = new PdfStamper(reader, new
	 * FileOutputStream(filePathName, true));
	 * stamper.setEncryption(userPassword.getBytes(), null,
	 * PdfWriter.ALLOW_PRINTING, PdfWriter.ENCRYPTION_AES_128);
	 * 
	 * stamper.close(); reader.close();
	 * 
	 * System.out.println(p.getProperty("success")); }
	 */

}
