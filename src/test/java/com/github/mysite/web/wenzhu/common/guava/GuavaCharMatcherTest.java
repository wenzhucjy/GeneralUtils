package com.github.mysite.web.wenzhu.common.guava;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.junit.Test;

import com.google.common.base.CharMatcher;
import com.google.common.base.Predicate;

public class GuavaCharMatcherTest {

	
	// Remove Special Characters from a String
	@Test
	public void whenRemoveSpecialCharacters_thenRemoved(){
	    String input = "H*el.lo,}12";
	    CharMatcher matcher = CharMatcher.JAVA_LETTER_OR_DIGIT;
	    String result = matcher.retainFrom(input);
	 
	    assertEquals("Hello12", result);
	}
	
	//  Remove non ASCII characters from String
	
	@Test
	public void whenRemoveNonASCIIChars_thenRemoved() {
	    String input = "あhello₤";
	 
	    String result = CharMatcher.ASCII.retainFrom(input);
	    assertEquals("hello", result);
	 
	    result = CharMatcher.inRange('0', 'z').retainFrom(input);
	    assertEquals("hello", result);
	}
	
	
	
	// Remove Characters not in the Charset
	@Test
	public void whenRemoveCharsNotInCharset_thenRemoved() {
	    Charset charset = Charset.forName("cp437");
	    final CharsetEncoder encoder = charset.newEncoder();
	 
	    Predicate<Character> inRange = new Predicate<Character>() {
	        public boolean apply(Character c) {
	            return encoder.canEncode(c);
	        }
	    };
	 
	    String result = CharMatcher.forPredicate(inRange)
	                               .retainFrom("helloは");
	    assertEquals("hello", result);
	}
	
	
	// Validate String
	@Test
	public void whenValidateString_thenValid(){
	    String input = "hello";
	 
	    boolean result = CharMatcher.JAVA_LOWER_CASE.matchesAllOf(input);
	    assertTrue(result);
	 
	    result = CharMatcher.is('e').matchesAnyOf(input);
	    assertTrue(result);
	 
	    result = CharMatcher.JAVA_DIGIT.matchesNoneOf(input);
	    assertTrue(result);
	}
	
	
	// Trim String
	@Test
	public void whenTrimString_thenTrimmed() {
	    String input = "---hello,,,";
	 
	    String result = CharMatcher.is('-').trimLeadingFrom(input);
	    assertEquals("hello,,,", result);
	 
	    result = CharMatcher.is(',').trimTrailingFrom(input);
	    assertEquals("---hello", result);
	 
	    result = CharMatcher.anyOf("-,").trimFrom(input);
	    assertEquals("hello", result);
	}
	
	// Collapse a String
	@Test
	public void whenCollapseFromString_thenCollapsed() {
	    String input = "       hel    lo      ";
	 
	    String result = CharMatcher.is(' ').collapseFrom(input, '-');
	    assertEquals("-hel-lo-", result);
	 
	    result = CharMatcher.is(' ').trimAndCollapseFrom(input, '-');
	    assertEquals("hel-lo", result);
	}
	
	//  Replace from String
	@Test
	public void whenReplaceFromString_thenReplaced() {
	    String input = "apple-banana.";
	 
	    String result = CharMatcher.anyOf("-.").replaceFrom(input, '!');
	    assertEquals("apple!banana!", result);
	 
	    result = CharMatcher.is('-').replaceFrom(input, " and ");
	    assertEquals("apple and banana.", result);
	}
	
	// Count Character Occurrences
	@Test
	public void whenCountCharInString_thenCorrect() {
	    String input = "a, c, z, 1, 2";
	 
	    int result = CharMatcher.is(',').countIn(input);
	    assertEquals(4, result);
	 
	    result = CharMatcher.inRange('a', 'h').countIn(input);
	    assertEquals(2, result);
	}
}
