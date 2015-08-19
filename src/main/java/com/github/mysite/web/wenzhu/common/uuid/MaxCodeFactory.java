package com.github.mysite.web.wenzhu.common.uuid;

/**
 * description:工厂用于生成ISnCode接口实现
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
