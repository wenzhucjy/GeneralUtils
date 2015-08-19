package com.github.mysite.web.wenzhu.common.file;

import java.io.*;

/**
 * description:文件辅助类
 *
 * @author: jy.chen
 * @version: 1.0
 * @since: 2015/8/11 - 10:38
 */
public class FileHelper {

    private FileHelper() {

    }

    /**
     * 读取文件
     *
     * @param filePathName
     * @return
     */
    public static Object readFile(String filePathName) {
        ObjectInputStream oin = null;
        try {
            File file = new File(filePathName);
            if (!file.exists()) {
                file.createNewFile();
            }
            oin = new ObjectInputStream(
                    new BufferedInputStream(
                            new FileInputStream(
                                    file
                            )
                    )
            );
            Object obj = oin.readObject();

            return obj;
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                oin.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 写入文件
     *
     * @param filePathName
     * @param obj
     */
    public static void writeFile(String filePathName, Object obj) {
        ObjectOutputStream oout = null;
        File file = new File(filePathName);
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            oout = new ObjectOutputStream(
                    new BufferedOutputStream(
                            new FileOutputStream(
                                    file
                            )
                    )
            );

            oout.writeObject(obj);
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            try {
                oout.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
