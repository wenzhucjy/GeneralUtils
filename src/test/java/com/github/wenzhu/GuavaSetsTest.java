package com.github.wenzhu;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;
import com.google.common.collect.Multisets;
import com.google.common.collect.Range;
import com.google.common.collect.RangeSet;
import com.google.common.collect.Sets;
import com.google.common.collect.TreeRangeSet;

public class GuavaSetsTest {
	
	// Union of Sets
	@Test
	public void whenCalculatingUnionOfSets_thenCorrect() {
	    Set<Character> first = ImmutableSet.of('a', 'b', 'c');
	    Set<Character> second = ImmutableSet.of('b', 'c', 'd');
	 
	    Set<Character> union = Sets.union(first, second);
	    assertThat(union, containsInAnyOrder('a', 'b', 'c', 'd'));
	}
	
	//  Cartesian Product of Sets
	@Test
	public void whenCalculatingCartesianProductOfSets_thenCorrect() {
	    Set<Character> first = ImmutableSet.of('a', 'b');
	    Set<Character> second = ImmutableSet.of('c', 'd');
	    Set<List<Character>> result =
	      Sets.cartesianProduct(ImmutableList.of(first, second));
	 
	    Function<List<Character>, String> func =
	      new Function<List<Character>, String>() {
	        public String apply(List<Character> input) {
	            return Joiner.on(" ").join(input);
	        }
	    };
	    Iterable<String> joined = Iterables.transform(result, func);
	    assertThat(joined, containsInAnyOrder("a c", "a d", "b c", "b d"));
	}
	
	//  Sets Intersection
	@Test
	public void whenCalculatingSetIntersection_thenCorrect() {
	    Set<Character> first = ImmutableSet.of('a', 'b', 'c');
	    Set<Character> second = ImmutableSet.of('b', 'c', 'd');
	 
	    Set<Character> intersection = Sets.intersection(first, second);
	    assertThat(intersection, containsInAnyOrder('b', 'c'));
	}
	
	
	//  Power Set
	@SuppressWarnings("unchecked")
	@Test
	public void whenCalculatingPowerSet_thenCorrect() {
	    Set<Character> chars = ImmutableSet.of('a', 'b');
	 
	    Set<Set<Character>> result = Sets.powerSet(chars);
	 
	    Set<Character> empty =  ImmutableSet.<Character> builder().build();
	    Set<Character> a = ImmutableSet.of('a');
	    Set<Character> b = ImmutableSet.of('b');
	    Set<Character> aB = ImmutableSet.of('a', 'b');
	 
	    assertThat(result, contains(empty, a, b, aB));
	}
	
	
	// ContiguousSet
	@Test
	public void whenCreatingRangeOfIntegersSet_thenCreated() {
	    int start = 10;
	    int end = 30;
	    ContiguousSet<Integer> set = ContiguousSet.create(
	      Range.closed(start, end), DiscreteDomain.integers());
	 
	    assertEquals(21, set.size());
	    assertEquals(10, set.first().intValue());
	    assertEquals(30, set.last().intValue());
	}
	
	// RangeSet
	@Test
	public void whenUsingRangeSet_thenCorrect() {
	    RangeSet<Integer> rangeSet = TreeRangeSet.create();
	    rangeSet.add(Range.closed(1, 10));
	    rangeSet.add(Range.closed(12, 15));
	 
	    assertEquals(2, rangeSet.asRanges().size());
	 
	    rangeSet.add(Range.closed(10, 12));
	    assertTrue(rangeSet.encloses(Range.closed(1, 15)));
	    assertEquals(1, rangeSet.asRanges().size());
	}
	
	
	// MultiSet
	@Test
	public void whenInsertDuplicatesInMultiSet_thenInserted() {
	    Multiset<String> names = HashMultiset.create();
	    names.add("John");
	    names.add("Adam", 3);
	    names.add("John");
	 
	    assertEquals(2, names.count("John"));
	    names.remove("John");
	    assertEquals(1, names.count("John"));
	 
	    assertEquals(3, names.count("Adam"));
	    names.remove("Adam", 2);
	    assertEquals(1, names.count("Adam"));
	}
	
	// Get Top N Elements in a MultiSet
	@Test
	public void whenGetTopOcurringElementsWithMultiSet_thenCorrect() {
	    Multiset<String> names = HashMultiset.create();
	    names.add("John");
	    names.add("Adam", 5);
	    names.add("Jane");
	    names.add("Tom", 2);
	 
	    Set<String> sorted = Multisets.copyHighestCountFirst(names).elementSet();
	    List<String> sortedAsList = Lists.newArrayList(sorted);
	    assertEquals("Adam", sortedAsList.get(0));
	    assertEquals("Tom", sortedAsList.get(1));
	}
}
