package com.github.mysite.common.java8;

import com.google.common.collect.Sets;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
}
