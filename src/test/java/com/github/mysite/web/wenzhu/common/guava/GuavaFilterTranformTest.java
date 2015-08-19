package com.github.mysite.web.wenzhu.common.guava;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class GuavaFilterTranformTest {
	
	// Filter a Collection
	@Test
	public void whenFilterWithIterables_thenFiltered() {
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Iterable<String> result = Iterables.filter(names, Predicates.containsPattern("a"));
	 
	    assertThat(result, containsInAnyOrder("Jane", "Adam"));
	}
	
	@Test
	public void whenFilterWithCollections2_thenFiltered() {
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));
	     
	    assertEquals(2, result.size());
	    assertThat(result, containsInAnyOrder("Jane", "Adam"));
	 
	    result.add("anna");
	    assertEquals(5, names.size());
	}
	
	// the result is constrained by the predicate 
	@Test(expected = IllegalArgumentException.class)
	public void givenFilteredCollection_whenAddingInvalidElement_thenException() {
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<String> result = Collections2.filter(names, Predicates.containsPattern("a"));
	 
	    result.add("elvis");
	}
	
	// Write Custom Filter Predicate
	@Test
	public void whenFilterCollectionWithCustomPredicate_thenFiltered() {
	    Predicate<String> predicate = new Predicate<String>() {
	        public boolean apply(String input) {
	            return input.startsWith("A") || input.startsWith("J");
	        }
	    };
	 
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<String> result = Collections2.filter(names, predicate);
	 
	    assertEquals(3, result.size());
	    assertThat(result, containsInAnyOrder("John", "Jane", "Adam"));
	}
	
	// Combine multiple Predicates
	@Test
	public void whenFilterUsingMultiplePredicates_thenFiltered() {
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<String> result = Collections2.filter(names, 
	      Predicates.or(Predicates.containsPattern("J"), 
	      Predicates.not(Predicates.containsPattern("a"))));
	 
	    assertEquals(3, result.size());
	    assertThat(result, containsInAnyOrder("John", "Jane", "Tom"));
	}
	
	// Remove null values while Filtering a Collection
	@Test
	public void whenRemoveNullFromCollection_thenRemoved() {
	    List<String> names = Lists.newArrayList("John", null, "Jane", null, "Adam", "Tom");
	    Collection<String> result = Collections2.filter(names, Predicates.notNull());
	 
	    assertEquals(4, result.size());
	    assertThat(result, containsInAnyOrder("John", "Jane", "Adam", "Tom"));
	}
	
	// Check If All Elements in a Collection Match a Condition
	@Test
	public void whenCheckingIfAllElementsMatchACondition_thenCorrect() {
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	 
	    boolean result = Iterables.all(names, Predicates.containsPattern("n|m"));
	    assertTrue(result);
	 
	    result = Iterables.all(names, Predicates.containsPattern("a"));
	    assertFalse(result);
	}
	
	// Transform a Collection
	@Test
	public void whenTransformWithIterables_thenTransformed() {
	    Function<String, Integer> function = new Function<String, Integer>() {
	        public Integer apply(String input) {
	            return input.length();
	        }
	    };
	 
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Iterable<Integer> result = Iterables.transform(names, function);
	 
	    assertThat(result, contains(4, 4, 4, 3));
	}
	
	@Test
	public void whenTransformWithCollections2_thenTransformed() {
	    Function<String,Integer> func = new Function<String,Integer>(){
	        public Integer apply(String input) {
	            return input.length();
	        }
	    };
	 
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<Integer> result = Collections2.transform(names, func);
	 
	    assertEquals(4, result.size());
	    assertThat(result, contains(4, 4, 4, 3));
	 
	    result.remove(3);
	    assertEquals(3, names.size());
	}
	
	// Create Function from Predicate
	@Test
	public void whenCreatingAFunctionFromAPredicate_thenCorrect() {
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<Boolean> result =
	      Collections2.transform(names,
	      Functions.forPredicate(Predicates.containsPattern("m")));
	 
	    assertEquals(4, result.size());
	    assertThat(result, contains(false, false, true, true));
	}
	
	// Composition of two Functions
	@Test
	public void whenTransformingUsingComposedFunction_thenTransformed() {
	    Function<String,Integer> f1 = new Function<String,Integer>(){
	        public Integer apply(String input) {
	            return input.length();
	        }
	    };
	 
	    Function<Integer,Boolean> f2 = new Function<Integer,Boolean>(){
	        public Boolean apply(Integer input) {
	            return input % 2 == 0;
	        }
	    };
	 
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<Boolean> result = Collections2.transform(names, Functions.compose(f2, f1));
	 
	    assertEquals(4, result.size());
	    assertThat(result, contains(true, true, true, false));
	}
	
	
	// Combine Filtering and Transforming
	@Test
	public void whenFilteringAndTransformingCollection_thenCorrect() {
	    Predicate<String> predicate = new Predicate<String>() {
	        public boolean apply(String input) {
	            return input.startsWith("A") || input.startsWith("T");
	        }
	    };
	 
	    Function<String, Integer> func = new Function<String,Integer>(){
	        public Integer apply(String input) {
	            return input.length();
	        }
	    };
	 
	    List<String> names = Lists.newArrayList("John", "Jane", "Adam", "Tom");
	    Collection<Integer> result = FluentIterable.from(names)
	                                               .filter(predicate)
	                                               .transform(func)
	                                               .toList();
	 
	    assertEquals(2, result.size());
	    assertThat(result, containsInAnyOrder(4, 3));
	}
}
