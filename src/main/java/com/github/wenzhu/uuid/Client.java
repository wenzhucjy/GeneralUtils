package com.github.wenzhu.uuid;

/**
 * description:
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 22:20
 */
public class Client {
    public static void main(String[] args) {
        for (int i = 0; i < 21; i++) {
            String uuid = MaxCodeFactory.generateISnCode().genSnCode("Doc"
                    , true, "字第 # 号", "0", 30, false, null, 5
            );
            System.out.println("doc uuid1===" + uuid);
        }
    }
}
