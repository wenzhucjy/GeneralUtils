package com.github.wenzhu.guava;

import com.google.common.base.Function;
import com.google.common.collect.*;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class GuavaMapsTest {


	// ImmutableMap
	@Test
	public void whenCreatingImmutableMap_thenCorrect() {
	    Map<String, Integer> salary = ImmutableMap.<String, Integer> builder()
	      .put("John", 1000)
	      .put("Jane", 1500)
	      .put("Adam", 2000)
	      .put("Tom", 2000)
	      .build();
	 
	    assertEquals(1000, salary.get("John").intValue());
	    assertEquals(2000, salary.get("Tom").intValue());
	}
	
	// SortedMap
	@Test
	public void whenUsingSortedMap_thenKeysAreSorted() {
	    ImmutableSortedMap<String, Integer> salary = new ImmutableSortedMap
	      .Builder<String, Integer>(Ordering.natural())
	      .put("John", 1000)
	      .put("Jane", 1500)
	      .put("Adam", 2000)
	      .put("Tom", 2000)
	      .build();
	 
	    assertEquals("Adam", salary.firstKey());
	    assertEquals(2000, salary.lastEntry().getValue().intValue());
	}
	
	
	// BiMap
	@Test
	public void whenCreateBiMap_thenCreated() {
	    BiMap<String, Integer> words = HashBiMap.create();
	    words.put("First", 1);
	    words.put("Second", 2);
	    words.put("Third", 3);
	 
	    assertEquals(2, words.get("Second").intValue());
	    assertEquals("Third", words.inverse().get(3));
	}
	
	// Multimap
	@Test
	public void whenCreateMultimap_thenCreated() {
	    Multimap<String, String> multimap = ArrayListMultimap.create();
	    multimap.put("fruit", "apple");
	    multimap.put("fruit", "banana");
	    multimap.put("pet", "cat");
	    multimap.put("pet", "dog");
	 
	    assertThat(multimap.get("fruit"), containsInAnyOrder("apple", "banana"));
	    assertThat(multimap.get("pet"), containsInAnyOrder("cat", "dog"));
	}
	
	
	// Table
	@Test
	public void whenCreatingTable_thenCorrect() {
	    Table<String,String,Integer> distance = HashBasedTable.create();
	    distance.put("London", "Paris", 340);
	    distance.put("New York", "Los Angeles", 3940);
	    distance.put("London", "New York", 5576);
	 
	    assertEquals(3940, distance.get("New York", "Los Angeles").intValue());
	    assertThat(distance.columnKeySet(), 
	      containsInAnyOrder("Paris", "New York", "Los Angeles"));
	    assertThat(distance.rowKeySet(), containsInAnyOrder("London", "New York"));
	}
	
	@Test
	public void whenTransposingTable_thenCorrect() {
	    Table<String,String,Integer> distance = HashBasedTable.create();
	    distance.put("London", "Paris", 340);
	    distance.put("New York", "Los Angeles", 3940);
	    distance.put("London", "New York", 5576);
	 
	    Table<String, String, Integer> transposed = Tables.transpose(distance);
	 
	    assertThat(transposed.rowKeySet(), 
	      containsInAnyOrder("Paris", "New York", "Los Angeles"));
	    assertThat(transposed.columnKeySet(), containsInAnyOrder("London", "New York"));
	}
	
	// ClassToInstanceMap
	@Test
	public void whenCreatingClassToInstanceMap_thenCorrect() {
	    ClassToInstanceMap<Number> numbers = MutableClassToInstanceMap.create();
	    numbers.putInstance(Integer.class, 1);
	    numbers.putInstance(Double.class, 1.5);
	 
	    assertEquals(1, numbers.get(Integer.class));
	    assertEquals(1.5, numbers.get(Double.class));
	}
	
	// Group List using Multimap
	@Test
	public void whenGroupingListsUsingMultimap_thenGrouped() {
	    List<String> names = Lists.newArrayList("John", "Adam", "Tom");
	    Function<String,Integer> func = new Function<String,Integer>(){
	        public Integer apply(String input) {
	            return input.length();
	        }
	    };
	    Multimap<Integer, String> groups = Multimaps.index(names, func);
	 
	    assertThat(groups.get(3), containsInAnyOrder("Tom"));
	    assertThat(groups.get(4), containsInAnyOrder("John", "Adam"));
	}
}
