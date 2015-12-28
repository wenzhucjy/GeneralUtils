package com.github.mysite.common.java8.stream;

import java.util.Optional;

/**
 * description: java8-tutorial stream sample 
 * @seealso https://github.com/winterbe/java8-tutorial
 *
 * @author :    jy.chen
 *  @version  :  1.0
 * @since  : 2015/8/28 - 14:16
 */
public class OptionalSample {
    public static void main(String[] args) {
        Optional<String> optional = Optional.of("bam");

        optional.isPresent();           // true
        optional.get();                 // "bam"
        optional.orElse("fallback");    // "bam"

        optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
    }
}
