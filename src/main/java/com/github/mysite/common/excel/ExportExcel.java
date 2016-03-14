package com.github.mysite.common.excel;

import com.github.mysite.common.excel.annotation.ExcelField;
import com.github.mysite.common.excel.util.Reflections;
import com.google.common.collect.Lists;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.*;

/**
 * description: 导出Excel文件（导出“XLSX”格式，支持大数据量导出 @see org.apache.poi.ss.SpreadsheetVersion）
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-11 14:13
 */
public class ExportExcel {

    /* 默认的导出的Date时间格式 */
    private static final String    DATE_FORMATTER = "yyyy-MM-dd HH:mm:ss";

    /**
     * Logger for ExportExcel
     */
    private static final Logger LOG            = LoggerFactory.getLogger(ExportExcel.class);

    /**
     * 工作薄对象
     */
    private Workbook wb;

    /**
     * 工作表对象
     */
    private Sheet sheet;

    /**
     * 样式列表
     */
    private Map<String, CellStyle> styles;

    /**
     * 当前行号
     */
    private int                    rownum;

    /**
     * 注解列表（Object[]{ ExcelField, Field/Method }）
     */
    List<Object[]>                 annotationList = Lists.newArrayList();

    /**
     * 构造函数
     *
     * @param titleName 表格标题，传“空值”，表示无标题
     * @param cls 实体对象，通过annotation.ExportField获取标题
     * @param wb Workbook
     * @return ExportExcel
     */
    public ExportExcel(String titleName, Class<?> cls, Workbook wb){
        this(titleName, cls, wb, 1);
    }

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param cls 实体对象，通过annotation.ExportField获取标题
     * @param type 导出类型（1:导出数据；2：导出模板）
     */
    public ExportExcel(String title, Class<?> cls, Workbook wb, int type){
        this.wb = wb;
        // Get annotation field
        Field[] fs = cls.getDeclaredFields();
        for (Field f : fs) {
            ExcelField ef = f.getAnnotation(ExcelField.class);
            /* 导入导出 */
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                annotationList.add(new Object[] { ef, f });
            }
        }
        // Get annotation method
        Method[] ms = cls.getDeclaredMethods();
        for (Method m : ms) {
            ExcelField ef = m.getAnnotation(ExcelField.class);
            if (ef != null && (ef.type() == 0 || ef.type() == type)) {
                annotationList.add(new Object[] { ef, m });
            }
        }
        // Field sorting
        Collections.sort(annotationList,
                         (o1, o2) -> new Integer(((ExcelField) o1[0]).sort()).compareTo(((ExcelField) o2[0]).sort()));
        // Initialize
        List<String> headerList = Lists.newArrayList();
        for (Object[] os : annotationList) {
            String t = ((ExcelField) os[0]).title();
            headerList.add(t);
        }
        initialize(title, headerList);
    }

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param headers 表头数组
     */
    public ExportExcel(String title, String[] headers){
        initialize(title, Lists.newArrayList(headers));
    }

    /**
     * 构造函数
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    public ExportExcel(String title, List<String> headerList){
        initialize(title, headerList);
    }

    /**
     * 初始化Excel
     *
     * @param title 表格标题，传“空值”，表示无标题
     * @param headerList 表头列表
     */
    private void initialize(String title, List<String> headerList) {
        /* 如果为空，则新建对象 */
        Optional.ofNullable(wb).orElse(new SXSSFWorkbook(500));
        if (StringUtils.isNotBlank(title)) {
            this.sheet = wb.createSheet(title);
        } else {
            this.sheet = wb.createSheet();
        }
        this.styles = createStyles(wb);
        // Create title
        if (StringUtils.isNotBlank(title)) {
            Row titleRow = sheet.createRow(rownum++);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            // 合并单元格
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(),
                                                       titleRow.getRowNum(), headerList.size() - 1));

        }
        // Create header
        if (headerList == null) {
            throw new RuntimeException("headerList not null!");
        }
        Row headerRow = sheet.createRow(rownum++);
        headerRow.setHeightInPoints(20);
        for (int i = 0; i < headerList.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellStyle(styles.get("header"));
            cell.setCellValue(headerList.get(i));
            sheet.autoSizeColumn(i);
        }
        for (int i = 0; i < headerList.size(); i++) {
            int colWidth = sheet.getColumnWidth(i) * 2;
            sheet.setColumnWidth(i, colWidth < 3000 ? 3000 : colWidth);
        }
        LOG.info("Initialize success.");
    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表集合
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        Map<String, CellStyle> styles = new HashMap<>();

        CellStyle style = wb.createCellStyle();
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        style.setFont(titleFont);
        /* 标题样式 */
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // 设置边框细线
        style.setBorderRight(CellStyle.BORDER_THIN);
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderLeft(CellStyle.BORDER_THIN);
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderTop(CellStyle.BORDER_THIN);
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        style.setBorderBottom(CellStyle.BORDER_THIN);
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());

        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        /* 默认数据样式 */
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_LEFT);
        // 左对齐样式
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        // 居中样式
        style.setAlignment(CellStyle.ALIGN_CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(CellStyle.ALIGN_RIGHT);
        // 右对齐样式
        styles.put("data3", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        // 自动换行
        // style.setWrapText(true);
        style.setAlignment(CellStyle.ALIGN_CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(CellStyle.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        // 头部样式
        styles.put("header", style);

        return styles;
    }

    /**
     * 添加一行
     *
     * @return 行对象
     */
    public Row addRow() {
        return sheet.createRow(rownum++);
    }

    /**
     * 添加一个单元格
     *
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val) {
        return this.addCell(row, column, val, 0, Class.class);
    }

    /**
     * 添加一个单元格
     *
     * @param row 添加的行
     * @param column 添加列号
     * @param val 添加值
     * @param align 对齐方式（1：靠左；2：居中；3：靠右）
     * @return 单元格对象
     */
    public Cell addCell(Row row, int column, Object val, int align, Class<?> fieldType) {
        Cell cell = row.createCell(column);
        CellStyle style = styles.get("data" + (align >= 1 && align <= 3 ? align : ""));
        try {
            Object newVal = Optional.ofNullable(val).orElse("");
            if (newVal instanceof String) {
                cell.setCellValue((String) newVal);
            } else if (newVal instanceof Integer) {
                cell.setCellValue((Integer) newVal);
            } else if (newVal instanceof Long) {
                cell.setCellValue((Long) newVal);
            } else if (newVal instanceof Double) {
                cell.setCellValue((Double) newVal);
            } else if (newVal instanceof Float) {
                cell.setCellValue((Float) newVal);
            } else if (newVal instanceof Date) {
                DataFormat format = wb.createDataFormat();
                style.setDataFormat(format.getFormat(DATE_FORMATTER));
                cell.setCellValue((Date) newVal);
            } else if (newVal instanceof BigDecimal) {
                // 两位小数点，四舍五入
                BigDecimal bVal = ((BigDecimal) newVal).setScale(2, BigDecimal.ROUND_HALF_UP);
                cell.setCellValue(bVal.toString());
            } else {
                if (fieldType != Class.class) {
                    cell.setCellValue((String) fieldType.getMethod("setValue", Object.class).invoke(null, newVal));
                } else {
                    String type = "fieldtype." + newVal.getClass().getSimpleName() + "Type";
                    String className = this.getClass().getName().replaceAll(this.getClass().getSimpleName(), type);
                    cell.setCellValue((String) Class.forName(className).getMethod("setValue", Object.class).invoke(null,
                                                                                                                   newVal));
                }
            }
        } catch (Exception ex) {
            LOG.error("Set cell value [" + row.getRowNum() + "," + column + "] error: " + ex.toString());
            cell.setCellValue(val.toString());
        }
        cell.setCellStyle(style);
        return cell;
    }

    /**
     * 添加数据（通过annotation.ExportField添加数据）
     *
     * @return list 数据列表
     */
    public <E> ExportExcel setDataList(List<E> list) {
        for (E e : list) {
            int colunm = 0;
            Row row = this.addRow();
            StringBuilder sb = new StringBuilder();
            for (Object[] os : annotationList) {
                ExcelField ef = (ExcelField) os[0];
                Object val = null;
                // Get entity value
                if (StringUtils.isNotBlank(ef.value())) {
                    val = Reflections.invokeGetter(e, ef.value());
                } else {
                    if (os[1] instanceof Field) {
                        val = Reflections.invokeGetter(e, ((Field) os[1]).getName());
                    } else if (os[1] instanceof Method) {
                        val = Reflections.invokeMethod(e, ((Method) os[1]).getName(), new Class[] {},
                                                       new Object[] {});
                    }
                }
                // If is dict, get dict label
                // if (StringUtils.isNotBlank(ef.dictType())){
                //
                // }
                this.addCell(row, colunm++, val, ef.align(), ef.fieldType());
                sb.append(val).append(", ");
            }
            LOG.info("Write success: [" + row.getRowNum() + "] " + sb.toString());
        }
        return this;
    }

    /**
     * 输出数据流
     *
     * @param os 输出数据流
     */
    public ExportExcel write(OutputStream os) throws IOException {
        wb.write(os);
        return this;
    }

    /**
     * 输出到客户端
     *
     * @param fileName 输出文件名
     */
    public ExportExcel write(HttpServletRequest request, HttpServletResponse response, String fileName)
                                                                                                       throws IOException {
        response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        if (StringUtils.isNotBlank(fileName)) {
            String fileExtension = FilenameUtils.getExtension(fileName);
            if (!"xlsx".equals(fileExtension)) {
                fileName = fileName + ".xlsx";
            }
        } else {
            fileName = "Export.xlsx";
        }
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
        write(response.getOutputStream());
        return this;
    }

    /**
     * 输出到文件
     *
     * @param fileName 输出文件名
     */
    public ExportExcel writeFile(String fileName) throws IOException {
        FileOutputStream os = new FileOutputStream(fileName);
        this.write(os);
        return this;
    }

    /**
     * 清理临时文件
     */
    public ExportExcel dispose() {
        if (wb != null && wb instanceof SXSSFWorkbook) {
            ((SXSSFWorkbook) wb).dispose();
        }
        return this;
    }
}
