package com.github.mysite.common.java8;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;

/**
 * description: 集合
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2015-12-17 17:37
 */
public class StreamTest {

    public static void main(String[] args) {
        //List<T> intersect = list1.stream().filter(list2::contains).collect(Collectors.toList());
        //List<T> union = Stream.concat(list1.stream(), list2.stream()).distinct().collect(Collectors.toList());
        List<Integer> a = Stream.of(1, 2, 3).collect(Collectors.toList());
        List<Integer> b = Stream.of(4).collect(Collectors.toList());


        List<Integer> c = a.stream().filter(b::contains).collect(Collectors.toList());
        System.out.println(c);

        Set<Integer> d = Stream.of(1, 2, 3).collect(Collectors.toSet());
        Set<Integer> e = Stream.of(1, 2).collect(Collectors.toSet());
        Sets.SetView<Integer> f = Sets.intersection(d, e);
    }

    //1. Remove duplicates from a List using plain Java
    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithPlainJava_thenCorrect() {
        List<Integer> listWithDuplicates = Lists.newArrayList(0, 1, 2, 3, 0, 0);
        List<Integer> listWithoutDuplicates = new ArrayList<>(new HashSet<>(listWithDuplicates));

        assertThat(listWithoutDuplicates, hasSize(4));
    }
    //2. Remove duplicates from a List using Guava
    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithGuava_thenCorrect() {
        List<Integer> listWithDuplicates = Lists.newArrayList(0, 1, 2, 3, 0, 0);
        List<Integer> listWithoutDuplicates = Lists.newArrayList(Sets.newHashSet(listWithDuplicates));
        assertThat(listWithoutDuplicates, hasSize(4));
    }
    //3. Remove duplicates form a List using Java 8 Lambdas
    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithJava8_thenCorrect() {
        List<Integer> listWithDuplicates = Lists.newArrayList(1, 1, 2, 2, 3, 3);
        List<Integer> listWithoutDuplicates =
                listWithDuplicates.parallelStream().distinct().collect(Collectors.toList());
        assertThat(listWithoutDuplicates, hasSize(4));
    }

    @Test
    public void givenListContainsDuplicates_whenRemovingDuplicatesWithJava8_thenConvertToString() {
        DuplicateDemo demo1 = new DuplicateDemo(1, "测试1");
        DuplicateDemo demo2 = new DuplicateDemo(2, "测试2");
        DuplicateDemo demo3 = new DuplicateDemo(3, "测试1");
        List<DuplicateDemo> listWithDuplicates = Lists.newArrayList(demo1,demo2,demo3);

        String result = listWithDuplicates.stream().map(DuplicateDemo::getName).collect(Collectors.toList()).parallelStream().distinct().collect(Collectors.joining("-"));
        System.out.println(result);
    }

}

class DuplicateDemo {
    private int code;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public DuplicateDemo(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
