package com.github.wenzhu.uuid;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 17:51
 */
public class MaxCodeFactory {
    private MaxCodeFactory() {

    }

    public static ISnCode generateISnCode() {
        return new SnCodeService();
    }

}
