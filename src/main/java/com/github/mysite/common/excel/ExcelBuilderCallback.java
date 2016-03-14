package com.github.mysite.common.excel;

import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * description: Excel写数据回调接口
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-09 15:15
 */
public abstract class ExcelBuilderCallback {

    public int beforeBuildTitles(int rowIndex, Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return rowIndex;
    }

    public int beforeBuildDatas(int rowIndex, Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return rowIndex;
    }

    public void queryData(Object cellData,HttpServletRequest request){

    }

    public int afterBuildDatas(int rowIndex, Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        return rowIndex;
    }
}
