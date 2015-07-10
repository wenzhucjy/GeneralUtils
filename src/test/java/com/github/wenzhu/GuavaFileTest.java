package com.github.wenzhu;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.google.common.io.CharSink;
import com.google.common.io.CharSource;
import com.google.common.io.CharStreams;
import com.google.common.io.FileWriteMode;
import com.google.common.io.Files;
import com.google.common.io.Resources;

public class GuavaFileTest {

	@Test
	public void whenWriteUsingFiles_thenWritten() throws IOException {
		String expectedValue = "Hello world";
		File file = new File("test.txt");
		Files.write(expectedValue, file, Charsets.UTF_8);
		String result = Files.toString(file, Charsets.UTF_8);
		assertEquals(expectedValue, result);
	}

	// Write to File using CharSink
	@Test
	public void whenWriteUsingCharSink_thenWritten() throws IOException {
		String expectedValue = "Hello world";
		File file = new File("test.txt");
		CharSink sink = Files.asCharSink(file, Charsets.UTF_8);
		sink.write(expectedValue);

		String result = Files.toString(file, Charsets.UTF_8);
		assertEquals(expectedValue, result);
	}

	@Test
	public void whenWriteMultipleLinesUsingCharSink_thenWritten() throws IOException {
		List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
		File file = new File("test.txt");
		CharSink sink = Files.asCharSink(file, Charsets.UTF_8);
		sink.writeLines(names, " ");

		String result = Files.toString(file, Charsets.UTF_8);
		String expectedValue = Joiner.on(" ").join(names);
		assertEquals(expectedValue, result.trim());
	}

	// Write to File using ByteSink
	@Test
	public void whenWriteUsingByteSink_thenWritten() throws IOException {
		String expectedValue = "Hello world";
		File file = new File("test.txt");
		ByteSink sink = Files.asByteSink(file);
		sink.write(expectedValue.getBytes());

		String result = Files.toString(file, Charsets.UTF_8);
		assertEquals(expectedValue, result);
	}

	@Test
	public void whenReadUsingFiles_thenRead() throws IOException {
		String expectedValue = "Hello world";
		File file = new File("test.txt");
		String result = Files.toString(file, Charsets.UTF_8);

		assertEquals(expectedValue, result);
	}

	@Test
	public void whenReadMultipleLinesUsingFiles_thenRead() throws IOException {
		File file = new File("test.txt");
		List<String> result = Files.readLines(file, Charsets.UTF_8);

		assertThat(result, contains("John", "Jane", "Adam", "Tom"));
	}

	// Read from File using CharSource
	@Test
	public void whenReadUsingCharSource_thenRead() throws IOException {
		String expectedValue = "Hello world";
		File file = new File("test.txt");
		CharSource source = Files.asCharSource(file, Charsets.UTF_8);

		String result = source.read();
		assertEquals(expectedValue, result);
	}

	@Test
	public void whenReadMultipleCharSources_thenRead() throws IOException {
		String expectedValue = "Hello worldTest";
		File file1 = new File("test1.txt");
		File file2 = new File("test2.txt");

		CharSource source1 = Files.asCharSource(file1, Charsets.UTF_8);
		CharSource source2 = Files.asCharSource(file2, Charsets.UTF_8);
		CharSource source = CharSource.concat(source1, source2);

		String result = source.read();
		assertEquals(expectedValue, result);
	}

	// Read from File using CharStreams
	@Test
	public void whenReadUsingCharStream_thenRead() throws IOException {
		String expectedValue = "Hello world";
		FileReader reader = new FileReader("test.txt");
		String result = CharStreams.toString(reader);

		assertEquals(expectedValue, result);
		reader.close();
	}

	@Test
	public void whenReadUsingByteSource_thenRead() throws IOException {
		String expectedValue = "Hello world";
		File file = new File("test.txt");
		ByteSource source = Files.asByteSource(file);

		byte[] result = source.read();
		assertEquals(expectedValue, new String(result));
	}

	@Test
	public void whenReadAfterOffsetUsingByteSource_thenRead() throws IOException {
		String expectedValue = "lo world";
		File file = new File("test.txt");
		long offset = 3;
		long len = 1000;

		ByteSource source = Files.asByteSource(file).slice(offset, len);
		byte[] result = source.read();
		assertEquals(expectedValue, new String(result));
	}

	// Read from File using ByteStreams
	@Test
	public void whenReadUsingByteStream_thenRead() throws IOException {
		String expectedValue = "Hello world";
		FileInputStream reader = new FileInputStream("test.txt");
		byte[] result = ByteStreams.toByteArray(reader);
		reader.close();

		assertEquals(expectedValue, new String(result));
	}

	// Read using Resources
	@Test
	public void whenReadUsingResources_thenRead() throws IOException {
		String expectedValue = "Hello world";
		URL url = Resources.getResource("test.txt");
		String result = Resources.toString(url, Charsets.UTF_8);

		assertEquals(expectedValue, result);
	}

	// ///////////////////////////// Java – Rename or Move a File ///////////////////////

	@Test
	public void givenUsingGuava_whenMovingFile_thenCorrect() throws IOException {
		File fileToMove = new File("src/test/resources/fileToMove.txt");
		File destDir = new File("src/test/resources/");
		File targetFile = new File(destDir, fileToMove.getName());

		com.google.common.io.Files.move(fileToMove, targetFile);
	}

	@Test
	public void givenUsingApache_whenMovingFileApproach2_thenCorrect() throws IOException {
		FileUtils.moveFileToDirectory(FileUtils.getFile("src/test/resources/fileToMove.txt"),
				FileUtils.getFile("src/main/resources/"), true);
	}

	// //////////////////////////////////////Java – Convert File to InputStream
	// ///////////////////

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
	}

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
