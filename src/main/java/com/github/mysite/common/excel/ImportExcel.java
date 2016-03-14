package com.github.mysite.common.excel;

import com.github.mysite.common.excel.annotation.ExcelField;
import com.github.mysite.common.excel.util.Reflections;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * description: 导入Excel文件（支持“XLS”和“XLSX”格式）
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-11 16:20
 */
public class ImportExcel {
    private static Logger log = LoggerFactory.getLogger(ImportExcel.class);

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 标题行号
     */
    private int headerNum;

    /**
     * 构造函数
     * @param fileName 导入文件，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum)
            throws IOException {
        this(new File(fileName), headerNum);
    }

    /**
     * 构造函数
     * @param file 导入文件对象，读取第一个工作表
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum)
            throws IOException {
        this(file, headerNum, 0);
    }

    /**
     * 构造函数
     * @param fileName 导入文件
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException
     */
    public ImportExcel(String fileName, int headerNum, int sheetIndex)
            throws IOException {
        this(new File(fileName), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     * @param file 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException
     */
    public ImportExcel(File file, int headerNum, int sheetIndex)
            throws IOException {
        this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     * @param multipartFile 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException
     */
    public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex)
            throws IOException {
        this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
    }

    /**
     * 构造函数
     * @param fileName 导入文件对象
     * @param headerNum 标题行号，数据行号=标题行号+1
     * @param sheetIndex 工作表编号
     * @throws IOException
     */
    public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex)
            throws IOException {
        // 工作薄对象
        Workbook wb;
        if (StringUtils.isBlank(fileName)){
            throw new RuntimeException("导入文档为空!");
        }else if(fileName.toLowerCase().endsWith("xls")){
            wb = new HSSFWorkbook(is);
        }else if(fileName.toLowerCase().endsWith("xlsx")){
            wb = new XSSFWorkbook(is);
        }else{
            throw new RuntimeException("文档格式不正确!");
        }
        if (wb.getNumberOfSheets()<sheetIndex){
            throw new RuntimeException("文档中没有工作表!");
        }
        this.sheet = wb.getSheetAt(sheetIndex);
        this.headerNum = headerNum;
        log.debug("Initialize success.");
    }

    /**
     * 获取行对象
     * @param rownum
     * @return
     */
    public Row getRow(int rownum){
        return this.sheet.getRow(rownum);
    }

    /**
     * 获取数据行号
     * @return
     */
    public int getDataRowNum(){
        return headerNum+1;
    }

    /**
     * 获取最后一个数据行号
     * @return
     */
    public int getLastDataRowNum(){
        return this.sheet.getLastRowNum()+headerNum;
    }

    /**
     * 获取最后一个列号
     * @return
     */
    public int getLastCellNum(){
        return this.getRow(headerNum).getLastCellNum();
    }

    /**
     * 获取单元格值
     * @param row 获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column){
        Object val = "";
        try{
            Cell cell = row.getCell(column);
            if (cell != null){
                if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC){
                    val = cell.getNumericCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_STRING){
                    val = cell.getStringCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA){
                    val = cell.getCellFormula();
                }else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN){
                    val = cell.getBooleanCellValue();
                }else if (cell.getCellType() == Cell.CELL_TYPE_ERROR){
                    val = cell.getErrorCellValue();
                }
            }
        }catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 获取导入数据列表
     * @param cls 导入对象类型
     * @param groups 导入分组
     */
    public <E> List<E> getDataList(Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException{
        List<Object[]> annotationList = Lists.newArrayList();
        // Get annotation field
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs){
            ExcelField ef = f.getAnnotation(ExcelField.class);
            annotationList.add(new Object[]{ef, f});
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms){
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type()==0 || ef.type()==2)){
                annotationList.add(new Object[]{ef, m});
            }
        }
        // Field sorting
        Collections.sort(annotationList, (o1, o2) -> new Integer(((ExcelField)o1[0]).sort()).compareTo(
                ((ExcelField) o2[0]).sort()));
        //log.debug("Import column count:"+annotationList.size());
        // Get excel data
        List<E> dataList = Lists.newArrayList();
        for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
            E e = cls.newInstance();
            int column = 0;
            Row row = this.getRow(i);
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList){
                Object val = this.getCellValue(row, column++);
                if (val != null){
                    ExcelField ef = (ExcelField)os[0];
                    // If is dict type, get dict value
                    //if (StringUtils.isNotBlank(ef.dictType())){
                    //    val = DictUtils.getDictValue(val.toString(), ef.dictType(), "");
                    //    //log.debug("Dictionary type value: ["+i+","+colunm+"] " + val);
                    //}
                    // Get param type and type cast
                    Class<?> valType = Class.class;
                    if (os[1] instanceof Field){
                        valType = ((Field)os[1]).getType();
                    }else if (os[1] instanceof Method){
                        Method method = ((Method)os[1]);
                        if ("get".equals(method.getName().substring(0, 3))){
                            valType = method.getReturnType();
                        }else if("set".equals(method.getName().substring(0, 3))){
                            valType = ((Method)os[1]).getParameterTypes()[0];
                        }
                    }
                    //log.debug("Import value type: ["+i+","+column+"] " + valType);
                    try {
                        if (valType == String.class){
                            String s = String.valueOf(val.toString());
                            if(StringUtils.endsWith(s, ".0")){
                                val = StringUtils.substringBefore(s, ".0");
                            }else{
                                val = String.valueOf(val.toString());
                            }
                        }else if (valType == Integer.class){
                            val = Double.valueOf(val.toString()).intValue();
                        }else if (valType == Long.class){
                            val = Double.valueOf(val.toString()).longValue();
                        }else if (valType == Double.class){
                            val = Double.valueOf(val.toString());
                        }else if (valType == Float.class){
                            val = Float.valueOf(val.toString());
                        }else if (valType == Date.class){
                            val = DateUtil.getJavaDate((Double)val);
                        }else{
                            if (ef.fieldType() != Class.class){
                                val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString());
                            }else{
                                val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
                                        "fieldtype."+valType.getSimpleName()+"Type")).getMethod("getValue", String.class).invoke(null, val.toString());
                            }
                        }
                    } catch (Exception ex) {
                        log.error("Get cell value ["+i+","+column+"] error: " + ex.toString());
                        val = null;
                    }
                    // set entity value
                    if (os[1] instanceof Field){
                        Reflections.invokeSetter(e, ((Field)os[1]).getName(), val);
                    }else if (os[1] instanceof Method){
                        String mthodName = ((Method)os[1]).getName();
                        if ("get".equals(mthodName.substring(0, 3))){
                            mthodName = "set"+ StringUtils.substringAfter(mthodName, "get");
                        }
                        Reflections.invokeMethod(e, mthodName, new Class[] {valType}, new Object[] {val});
                    }
                }
                sb.append(val).append(", ");
            }
            dataList.add(e);
            log.info("Read success: ["+i+"] "+sb.toString());
        }
        return dataList;
    }
}
