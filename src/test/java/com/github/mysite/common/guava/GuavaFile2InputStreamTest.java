package com.github.mysite.common.guava;

import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;

public class GuavaFile2InputStreamTest {

// ////////////////  Java – Convert File to InputStream ///////////////////

	@Test
	public void givenUsingGuava_whenConvertingFileToInputStream_thenCorrect() throws IOException {
		File initialFile = new File("src/main/resources/sample.txt");
		InputStream targetStream = Files.asByteSource(initialFile).openStream();
	}

	@Test
	public void givenUsingCommonsIO_whenConvertingFileToInputStream_thenCorrect() throws IOException {
		File initialFile = new File("src/main/resources/sample.txt");
		InputStream targetStream = FileUtils.openInputStream(initialFile);
	}

	// //////////////////// Java – Write an InputStream to a File ///////////////

	@Test
	public void givenUsingPlainJava_whenConvertingAnInProgressInputStreamToAFile_thenCorrect() throws IOException {
		InputStream initialStream = new FileInputStream(new File("src/main/resources/sample.txt"));
		File targetFile = new File("src/main/resources/targetFile.tmp");
		OutputStream outStream = new FileOutputStream(targetFile);

		byte[] buffer = new byte[8 * 1024];
		int bytesRead;
		while ((bytesRead = initialStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		IOUtils.closeQuietly(initialStream);
		IOUtils.closeQuietly(outStream);
	}

	@Test
	public void givenUsingGuava_whenConvertingAnInputStreamToAFile_thenCorrect() throws IOException {
		InputStream initialStream = new FileInputStream(new File("src/main/resources/sample.txt"));
		byte[] buffer = new byte[initialStream.available()];
		initialStream.read(buffer);

		File targetFile = new File("src/main/resources/targetFile.tmp");
		Files.write(buffer, targetFile);
	}

	@Test
	public void givenUsingCommonsIO_whenConvertingAnInputStreamToAFile_thenCorrect() throws IOException {
		InputStream initialStream = FileUtils.openInputStream(new File("src/main/resources/sample.txt"));

		File targetFile = new File("src/main/resources/targetFile.tmp");

		FileUtils.copyInputStreamToFile(initialStream, targetFile);
	}}
