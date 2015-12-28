package com.github.mysite.common.guava;

import com.google.common.base.Charsets;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.io.*;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

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



}
