package com.github.mysite.common.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * description: JDK8测试
 *
 *  @author :   jy.chen
 * @version  : 1.0
 * @since : 2015-09-21 10:24
 */
public class JavaTest {

    List<String> stringCollection = Lists.newArrayList();
    @Before
    public void before() {
        stringCollection.add("ddd2");
        stringCollection.add("aaa2");
        stringCollection.add("bbb1");
        stringCollection.add("aaa1");
        stringCollection.add("bbb3");
        stringCollection.add("ccc");
        stringCollection.add("bbb2");
        stringCollection.add("ddd1");
    }
    
    @Test
    public void jdk8FilterTest() {
        stringCollection
                .stream()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

    // "aaa2", "aaa1"
    }
    @Test
    public void jdk8SortedTest() {
        stringCollection
                .stream()
                .sorted()
                .filter((s) -> s.startsWith("a"))
                .forEach(System.out::println);

    // "aaa1", "aaa2"
    }


    @Test
    public void jdk8MapTest() {
        stringCollection
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(System.out::println);

        // "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
    }

    @Test
    public void jdk8MatchTest() {
        boolean anyStartsWithA =
                stringCollection
                        .stream()
                        .anyMatch((s) -> s.startsWith("a"));

        System.out.println(anyStartsWithA);      // true
        
        boolean allStartsWithA =
                stringCollection
                        .stream()
                        .allMatch((s) -> s.startsWith("a"));

        System.out.println(allStartsWithA);      // false

        boolean noneStartsWithZ =
                stringCollection
                        .stream()
                        .noneMatch((s) -> s.startsWith("z"));

        System.out.println(noneStartsWithZ);      // true
    }
    
    @Test
    public void jdk8CountTest() {
        long startsWithB =
                stringCollection
                        .stream()
                        .filter((s) -> s.startsWith("b"))
                        .count();

        System.out.println(startsWithB);    // 3
    }

    @Test
    public void jdk8ReduceTest() {
        Optional<String> reduced =
                stringCollection
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);
        reduced.ifPresent(System.out::println);
        // "aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2"
    }

    @Test
    public void jdk8SortTest() {
        // Java 8 version
        List<String> sortedJDK8Names = stringCollection.stream().sorted().collect(Collectors.toList());
        // Guava version
        List<String> sortedGuavaNames = Ordering.natural().sortedCopy(stringCollection);
    }

    @Test
    public void jdk8StatisticsTest() {
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

        IntSummaryStatistics stats = numbers.stream().mapToInt((x) -> x).summaryStatistics();

        System.out.println("Highest number in List : " + stats.getMax());
        System.out.println("Lowest  number in List : " + stats.getMin());
        System.out.println("Sum of all numbers : " + stats.getSum());
        System.out.println("Average of all  numbers : " + stats.getAverage());
    }
}
