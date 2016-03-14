package com.github.mysite.common.excel;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * description: POI 导出excel工具类
 *
 * @author : jy.chen
 * @version : 1.0
 * @since : 2016-03-09 15:12
 */
public class ExcelBuilder extends AbstractXlsxView {

    /**
     * excel表格名
     */
    private String               filename;
    /**
     * 工作簿名称
     */
    private String               sheetName;
    /**
     * 内容类型
     */
    private String[]             properties;
    /**
     * 标题
     */
    private String[]             titles;
    /**
     * 转换器
     */
    private Converter[]          converters;
    /**
     * 数据集合 Collection
     */
    private Collection<?>        data;
    /**
     * 内容
     */
    private String[]             contents;
    /**
     * application/vnd.ms-excel , application/force-download
     */
    private static final String  contentType         = "application/vnd.ms-excel;charset=utf-8"; // "application/vnd.ms-excel";
    private static final String  headerType          = "Content-Disposition";
    private static final Integer DEFAULT_COLUMNWIDTH = 40;

    private ExcelBuilderCallback excelBuilderCallback;
    static {
        DateConverter localDateConverter = new DateConverter();
        // localDateConverter.setPattern(CommonAttributes.DATE_PATTERNS[7]);
        ConvertUtils.register(localDateConverter, Date.class);
    }

    public ExcelBuilder(String filename, String sheetName, String[] properties, String[] titles, Collection<?> data,
                        String[] contents){
        this.filename = filename;
        this.sheetName = sheetName;
        this.properties = properties;
        this.titles = titles;
        this.data = data;
        this.contents = contents;
    }

    public ExcelBuilder(String[] properties, String[] titles, Collection<?> data, String[] contents){
        this.properties = properties;
        this.titles = titles;
        this.data = data;
        this.contents = contents;
    }

    public ExcelBuilder(String[] properties, String[] titles, Collection<?> data){
        this.properties = properties;
        this.titles = titles;
        this.data = data;
    }

    public ExcelBuilder(){
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        // TODO: 2016/3/9  
    }

    public ExcelBuilderCallback getExcelBuilderCallback() {
        return excelBuilderCallback;
    }

    public void setExcelBuilderCallback(ExcelBuilderCallback excelBuilderCallback) {
        this.excelBuilderCallback = excelBuilderCallback;
    }

    /**
     * 设置表体的单元格样式
     *
     * @param workbook HSSFWorkbook
     * @param font
     * @param cellData
     * @return CellStyle
     * @throws Exception
     */
    private CellStyle getBodyStyle(Workbook workbook, Font font, Object cellData)
                                                                                                                   throws Exception {
        //
        CellStyle bodyCellStyle = workbook.createCellStyle();

        // HSSFDataFormat df = workbook.createDataFormat();
        // localHSSFCellStyle.setDataFormat(df.getFormat("#,##0"));

        // 设置单元格字体类型
        bodyCellStyle.setFont(font);

        // 自动换行
        bodyCellStyle.setWrapText(true);
        // 设置边框细线
        bodyCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        bodyCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        bodyCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        bodyCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        // 左右居中
        // localHSSFCellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 上下居中
        // localHSSFCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // if(BeanUtils.getProperty(cellData,this.properties[4]).equals("失败")){
        // HSSFFont font = workbook.createFont(); //set font
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // font.setColor(HSSFColor.RED.index);
        // localHSSFCellStyle.setFont(font);
        // }

        return bodyCellStyle;
    }

    private CellStyle getBodyDateStyle(Workbook workbook, Font font, Object cellData) throws Exception {
        CellStyle dateCellStyle = getBodyStyle(workbook, font, cellData);
        dateCellStyle.setDataFormat(workbook.createDataFormat().getFormat("m/d/yy h:mm"));
        return dateCellStyle;
    }

    /**
     * 合并单元格后给合并后的单元格加边框
     *
     * @param region
     * @param cs
     */
    public void setRegionStyle(Sheet hSSFSheet, CellRangeAddress region, CellStyle cs) {

        int toprowNum = region.getFirstRow();
        for (int i = toprowNum; i <= region.getLastRow(); i++) {
            Row row = hSSFSheet.getRow(i);
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                Cell cell = row.getCell(j);
                cell.setCellStyle(cs);
            }
        }
    }

    /**
     * 设置表头单元格样式
     *
     * @param workbook
     * @return CellStyle
     */
    private CellStyle getHeadStyle(Workbook workbook) {
        // 设置单元格类型
        CellStyle localHSSFCellStyle = workbook.createCellStyle();
        // 设置前景色填充颜色注意:确保前前景颜色设置背景颜色。(short) 31 浅紫色 (r:204, g:204, b:255)
        localHSSFCellStyle.setFillForegroundColor(HSSFColor.LIGHT_CORNFLOWER_BLUE.index);
        // 设置一个充满前景颜色的单元格…
        localHSSFCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
        // 左右居中
        localHSSFCellStyle.setAlignment(CellStyle.ALIGN_CENTER);
        // 上下居中
        localHSSFCellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        // localHSSFCellStyle.setRotation(short rotation);// 单元格内容的旋转的角度
        // 创建单元格内容显示不下时自动换行
        localHSSFCellStyle.setWrapText(true);

        // 设置字体
        Font localObject3 = workbook.createFont();
        // 字体高度
        localObject3.setFontHeightInPoints((short) 15);
        // 字体宽度 HSSFFont.BOLDWEIGHT_NORMAL(正常),BOLDWEIGHT_BOLD(加粗)
        localObject3.setBoldweight(Font.BOLDWEIGHT_BOLD);
        // 字体
        localObject3.setFontName(" 黑体 ");
        // ((HSSFFont) localObject3).setColor(HSSFFont.COLOR_RED); // 字体颜色
        // ((HSSFFont) localObject3).setItalic( true ); // 是否使用斜体
        // ((HSSFFont) localObject3).setStrikeout(true); // 是否使用划线
        // 设置单元格字体类型
        localHSSFCellStyle.setFont(localObject3);

        // 设置单元格边框为细线条
        localHSSFCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
        localHSSFCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
        localHSSFCellStyle.setBorderRight(CellStyle.BORDER_THIN);
        localHSSFCellStyle.setBorderTop(CellStyle.BORDER_THIN);
        return localHSSFCellStyle;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String[] getProperties() {
        return properties;
    }

    public void setProperties(String[] properties) {
        this.properties = properties;
    }

    public String[] getTitles() {
        return titles;
    }

    public void setTitles(String[] titles) {
        this.titles = titles;
    }

    public Collection<?> getData() {
        return data;
    }

    public void setData(Collection<?> data) {
        this.data = data;
    }

    public String[] getContents() {
        return contents;
    }

    public void setContents(String[] contents) {
        this.contents = contents;
    }

    public Converter[] getConverters() {
        return converters;
    }

    public void setConverters(Converter[] converters) {
        this.converters = converters;
    }
}
