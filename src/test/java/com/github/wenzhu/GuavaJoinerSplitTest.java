package com.github.wenzhu;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class GuavaJoinerSplitTest {
	
	
	// Convert List into String Using Joiner
	@Test
	public void whenConvertListToString_thenConverted() {
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    String result = Joiner.on(",").join(names);
	 
	    assertEquals(result, "John,Jane,Adam,Tom");
	}
	
	// Convert Map to String Using Joiner
	@Test
	public void whenConvertMapToString_thenConverted() {
	    Map<String, Integer> salary = Maps.newHashMap();
	    salary.put("John", 1000);
	    salary.put("Jane", 1500);
	    String result = Joiner.on(" , ").withKeyValueSeparator(" = ")
	                                    .join(salary);
	 
	    assertThat(result, containsString("John = 1000"));
	    assertThat(result, containsString("Jane = 1500"));
	}
	
	
	// Join Nested Collections
	@SuppressWarnings("unchecked")
	@Test
	public void whenJoinNestedCollections_thenJoined() {
	    List<ArrayList<String>> nested = Lists.newArrayList(
	      Lists.newArrayList("apple", "banana", "orange"),
	      Lists.newArrayList("cat", "dog", "bird"),
	      Lists.newArrayList("John", "Jane", "Adam"));
	    String result = Joiner.on(";").join(Iterables.transform(nested,
	      new Function<List<String>, String>() {
	          public String apply(List<String> input) {
	              return Joiner.on("-").join(input);
	          }
	      }));
	 
	    assertThat(result, containsString("apple-banana-orange"));
	    assertThat(result, containsString("cat-dog-bird"));
	    assertThat(result, containsString("apple-banana-orange"));
	}
	
	
	// Handle null values while using Joiner
	@Test
	public void whenConvertListToStringAndSkipNull_thenConverted() {
	    List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
	    String result = Joiner.on(",").skipNulls().join(names);
	 
	    assertEquals(result, "John,Jane,Adam,Tom");
	}
	
	@Test
	public void whenUseForNull_thenUsed() {
	    List<String> names = Lists.newArrayList("John", null, "Jane", "Adam", "Tom");
	    String result = Joiner.on(",").useForNull("nameless").join(names);
	 
	    assertEquals(result, "John,nameless,Jane,Adam,Tom");
	}
	
	
	// Create List from String using Splitter
	@Test
	public void whenCreateListFromString_thenCreated() {
	    String input = "apple - banana - orange";
	    List<String> result = Splitter.on("-").trimResults()
	                                          .splitToList(input);
	 
	    assertThat(result, contains("apple", "banana", "orange"));
	}
	
	
	//  Create Map from String using Splitter
	@Test
	public void whenCreateMapFromString_thenCreated() {
	    String input = "John=first,Adam=second";
	    Map<String, String> result = Splitter.on(",")
	                                         .withKeyValueSeparator("=")
	                                         .split(input);
	 
	    assertEquals("first", result.get("John"));
	    assertEquals("second", result.get("Adam"));
	}
	
	
	//  Split String with multiple separators
	@Test
	public void whenSplitStringOnMultipleSeparator_thenSplit() {
	    String input = "apple.banana,,orange,,.";
	    List<String> result = Splitter.onPattern("[.|,]")
	                                  .omitEmptyStrings()
	                                  .splitToList(input);
	 
	    assertThat(result, contains("apple", "banana", "orange"));
	}
	
	// Split a String at specific length
	@Test
	public void whenSplitStringOnSpecificLength_thenSplit() {
	    String input = "Hello world";
	    List<String> result = Splitter.fixedLength(3).splitToList(input);
	 
	    assertThat(result, contains("Hel", "lo ", "wor", "ld"));
	}
	
	//  Limit the split result
	@Test
	public void whenLimitSplitting_thenLimited() {
	    String input = "a,b,c,d,e";
	    List<String> result = Splitter.on(",")
	                                  .limit(4)
	                                  .splitToList(input);
	 
	    assertEquals(4, result.size());
	    assertThat(result, contains("a", "b", "c", "d,e"));
	}
}
