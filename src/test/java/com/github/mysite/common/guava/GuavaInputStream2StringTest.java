package com.github.mysite.common.guava;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.CharStreams;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GuavaInputStream2StringTest {

	// ##################### Java InputStream to String #######################

	@Test
	public void givenUsingGuava_whenConvertingAnInputStreamToAString_thenCorrect()
			throws IOException {
		String originalString = randomAlphabetic(8);
		InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

		ByteSource byteSource = new ByteSource() {
			@Override
			public InputStream openStream() throws IOException {
				return inputStream;
			}
		};

		String text = byteSource.asCharSource(Charsets.UTF_8).read();

		assertThat(text, equalTo(originalString));
	}


	@Test
	public void givenUsingGuavaAndJava7_whenConvertingAnInputStreamToAString_thenCorrect()
			throws IOException {
		String originalString = randomAlphabetic(8);
		InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

		String text;
		try (final Reader reader = new InputStreamReader(inputStream)) {
			text = CharStreams.toString(reader);
		}

		assertThat(text, equalTo(originalString));
	}

	@Test
	public void givenUsingCommonsIo_whenConvertingAnInputStreamToAString_thenCorrect()
			throws IOException {
		String originalString = randomAlphabetic(8);
		InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

		String text = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
		assertThat(text, equalTo(originalString));
	}

	@Test
	public void givenUsingCommonsIoWithCopy_whenConvertingAnInputStreamToAString_thenCorrect()
			throws IOException {
		String originalString = randomAlphabetic(8);
		InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

		StringWriter writer = new StringWriter();
		String encoding = StandardCharsets.UTF_8.name();
		IOUtils.copy(inputStream, writer, encoding);

		assertThat(writer.toString(), equalTo(originalString));
	}

	@Test
	public void givenUsingJava5_whenConvertingAnInputStreamToAString_thenCorrect()
			throws IOException {
		String originalString = randomAlphabetic(8);
		InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

		StringBuilder textBuilder = new StringBuilder();
		try (Reader reader = new BufferedReader(new InputStreamReader
				(inputStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
			int c = 0;
			while ((c = reader.read()) != -1) {
				textBuilder.append((char) c);
			}
		}
		assertEquals(textBuilder.toString(), originalString);
	}

	@Test
	public void givenUsingJava7_whenConvertingAnInputStreamToAString_thenCorrect()
			throws IOException {
		String originalString = randomAlphabetic(8);
		InputStream inputStream = new ByteArrayInputStream(originalString.getBytes());

		String text;
		try (Scanner scanner = new Scanner(inputStream, StandardCharsets.UTF_8.name())) {
			text = scanner.useDelimiter("\\A").next();
		}

		assertThat(text, equalTo(originalString));
	}

}
