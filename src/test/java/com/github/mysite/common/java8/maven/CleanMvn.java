package com.github.mysite.common.java8.maven;


import java.io.File;

/**
 * description: 删除Maven下载不成功文件
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-01-05 9:19
 */
public class CleanMvn {
    public static void main(String[] args){
        if(args.length != 1){
            print("使用方法错误，方法需要一个参数，参数为mvn本地仓库的路径");
        }
        findAndDelete(new File("D:\\Java\\maven\\repository"));
    }

    public static boolean findAndDelete(File file){
        if (file.exists()) {
            if(file.isFile()){
                if(file.getName().endsWith("lastUpdated")){
                    deleteFile(file.getParentFile());
                    return true;
                }
            } else if(file.isDirectory()){
                File[] files = file.listFiles();
                if (files != null) {
                    for(File f : files){
                        if(findAndDelete(f)){
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void deleteFile(File file){
        if (file.exists()) {
            if(file.isFile()){
                print("删除文件:" + file.getAbsolutePath());
                file.delete();
            } else if(file.isDirectory()){
                File[] files = file.listFiles();
                for(File f : files){
                    deleteFile(f);
                }
                print("删除文件夹:" + file.getAbsolutePath());
                print("====================================");
                file.delete();
            }
        }
    }

    public static void print(String msg){
        System.out.println(msg);
    }
}
