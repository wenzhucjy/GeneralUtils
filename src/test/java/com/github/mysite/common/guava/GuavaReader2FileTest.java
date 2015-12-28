package com.github.mysite.common.guava;

import com.google.common.io.CharSink;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

public class GuavaReader2FileTest {

	// ////////////////////Java – Write a Reader to File////////////////////////////

	@Test
	public void givenUsingPlainJava_whenWritingReaderContentsToFile_thenCorrect() throws IOException {
		Reader initialReader = new StringReader("Some text");

		int intValueOfChar;
		StringBuilder buffer = new StringBuilder();
		while ((intValueOfChar = initialReader.read()) != -1) {
			buffer.append((char) intValueOfChar);
		}
		initialReader.close();

		File targetFile = new File("src/test/resources/targetFile.txt");
		targetFile.createNewFile();

		Writer targetFileWriter = new FileWriter(targetFile);
		targetFileWriter.write(buffer.toString());
		targetFileWriter.close();
	}

	@Test
	public void givenUsingGuava_whenWritingReaderContentsToFile_thenCorrect() throws IOException {
		Reader initialReader = new StringReader("Some text");

		File targetFile = new File("src/test/resources/targetFile.txt");
		com.google.common.io.Files.touch(targetFile);
		CharSink charSink = com.google.common.io.Files.asCharSink(targetFile, Charset.defaultCharset(), FileWriteMode.APPEND);
		charSink.writeFrom(initialReader);

		initialReader.close();
	}

	@Test
	public void givenUsingCommonsIO_whenWritingReaderContentsToFile_thenCorrect() throws IOException {
		Reader initialReader = new CharSequenceReader("CharSequenceReader extends Reader");

		File targetFile = new File("src/test/resources/targetFile.txt");
		FileUtils.touch(targetFile);
		byte[] buffer = IOUtils.toByteArray(initialReader);
		FileUtils.writeByteArrayToFile(targetFile, buffer);

		initialReader.close();
	}


	// /////////////////////////////Java – File to Reader/////////////////////////////////////////

	@Test
	public void givenUsingGuava_whenConvertingFileIntoReader_thenCorrect() throws IOException {
		File initialFile = new File("src/test/resources/initialFile.txt");
		com.google.common.io.Files.touch(initialFile);
		Reader targetReader = Files.newReader(initialFile, Charset.defaultCharset());
		targetReader.close();
	}

	@Test
	public void givenUsingCommonsIO_whenConvertingFileIntoReader_thenCorrect() throws IOException {
		File initialFile = new File("src/test/resources/initialFile.txt");
		FileUtils.touch(initialFile);
		FileUtils.write(initialFile, "With Commons IO");
		byte[] buffer = FileUtils.readFileToByteArray(initialFile);
		Reader targetReader = new CharSequenceReader(new String(buffer));
		targetReader.close();
	}
}
