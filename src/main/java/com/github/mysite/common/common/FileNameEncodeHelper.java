package com.github.mysite.common.common;


import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import java.net.URLEncoder;

/**
 * description: 文件编码工具类
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-09 15:20
 */
public class FileNameEncodeHelper {

    /**
     * 设置下载文件中文件的名称
     *
     * @param filename  文件名
     * @param request   HttpServletRequest
     * @return  结果
     */
    public static String encodeFilename(String filename, HttpServletRequest request) {
        // 获取客户端浏览器和操作系统信息
        // 在IE浏览器中得到的是：User-Agent=Mozilla/4.0
        // (compatible; MSIE 6.0; Windows NT 5.1; SV1; Maxthon; Alexa Toolbar)
        // 在Firefox中得到的是：User-Agent=Mozilla/5.0
        // (Windows; U; Windows NT 5.1; zh-CN; rv:1.7.10) Gecko/20050717 Firefox/1.0.6
        String agent = request.getHeader("USER-AGENT");
        try {
            if ((agent != null) && (agent.contains("MSIE"))) {
                String newFileName = URLEncoder.encode(filename, "UTF-8");
                newFileName = FileNameEncodeHelper.replace(newFileName, "+", "%20");
                if (newFileName.length() > 150) {
                    newFileName = new String(filename.getBytes("GB2312"), "ISO8859-1");
                    newFileName = FileNameEncodeHelper.replace(newFileName, " ", "%20");
                }
                return newFileName;
            }
            if ((agent != null) && (agent.contains("Mozilla"))) {
                 MimeUtility.encodeText(filename, "UTF-8", "B");
            }

            return filename;
        } catch (Exception ex) {
            return filename;
        }
    }

    /**
     * ie,chrom,firfox下处理文件名显示乱码
     * 
     * @param request HttpServletRequest
     * @param fileNames 文件名
     * @return 结果
     */
    public static String processFileName(HttpServletRequest request, String fileNames) {
        // response.setHeader("Cache-Control", "private");
        // response.setHeader("Pragma", "private");
        // response.setContentType("application/vnd.ms-excel;charset=utf-8");
        // response.setHeader("Content-Type",
        // "application/force-download");
        // title = FileUtil.processFileName(request, title);
        // response.setHeader("Content-disposition", "attachment;filename=" + title + ".xls");
        //
        String codedfilename = null;
        try {
            String agent = request.getHeader("USER-AGENT");
            if (null != agent && agent.contains("MSIE") || null != agent && agent.contains("Trident")) {// ie

                codedfilename = URLEncoder.encode(fileNames, "UTF8");
            } else if (null != agent && agent.contains("Mozilla")) {// 火狐,chrome等

                codedfilename = new String(fileNames.getBytes("UTF-8"), "iso-8859-1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return codedfilename;
    }

    public static String replace(String filename, String oldStr, String newStr) {
        return filename.replaceAll(oldStr, newStr);
    }
}
