package com.yanshen.common.core.util.poi;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;

import com.yanshen.common.core.annotation.Excel;
import com.yanshen.common.core.annotation.Excel.Type;
import com.yanshen.common.core.annotation.Excel.ColumnType;
import com.yanshen.common.core.annotation.Excels;
import com.yanshen.common.core.text.Convert;
import com.yanshen.common.core.util.DateUtils;
import com.yanshen.common.core.util.ReflectUtils;
import com.yanshen.common.core.util.StringUtils;
import com.yanshen.common.core.util.file.FileTypeUtils;
import com.yanshen.common.core.util.file.ImageUtils;
import lombok.Data;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 根据网络资料针对ruoyi框架execl进行扩展能够使用多层表头和合并单元格、自定义单元格颜色等功能
 *
 * @author baohao-jia
 * @since 2023年11月15日 13:38
 */
public class ExcelExtendUtil<T> {
    public static final String[] FORMULA_STR = {"=", "-", "+", "@"};
    /**
     * Excel sheet最大行数，默认65536
     */
    public static final int sheetSize = 65536;
    private static final Logger log = LoggerFactory.getLogger(ExcelUtil.class);
    /**
     * 数字格式
     */
    private static final DecimalFormat DOUBLE_FORMAT = new DecimalFormat("######0.00");
    /**
     * 实体对象
     */
    public Class<T> clazz;
    /**
     * 工作表名称
     */
    private String sheetName;
    /**
     * 导出类型（EXPORT:导出数据；IMPORT：导入模板）
     */
    private Type type;
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
     * 导入导出数据列表
     */
    private List<T> list;
    /**
     * 注解列表
     */
    private List<Object[]> fields;
    /**
     * 当前行号
     */
    private int rownum;
    /**
     * 标题索引
     */
    private Integer titleRowIndex;
    /**
     * 标题
     */
    private String title;
    /**
     * 最大高度
     */
    private short maxHeight;
    /**
     * 统计列表
     */
    private Map<Integer, Double> statistics = new HashMap<Integer, Double>();
    /**
     * 表头开始的索引
     */
    private int tableTopStartIndex = 1;

    /**
     * 表数据开始的索引
     */
    private int tableDataStartIndex = 2;

    /**
     * 表头行映射 创建时复用
     */
    private Map<Integer, Row> tableTopRowMap = new HashMap<>();


    /**
     * 是否开启多级表头
     */
    private boolean openMultilevelTableTop = false;

    /**
     * 表头所占最大行索引
     */
    private int tableTopMaxIndex = 1;

    /**
     * 表头Json数据
     */
    private String tableTopJsonData;

    /**
     * 表头数据集合列表
     */
    private List<JsonData> tableTopJsonDataList;

    /**
     * 快速合并计算附加参数
     */
    private Map<Long, ExcelFieldAdditionParams> fieldAdditionParamsMap;

    /**
     * 根据动态数据改变单元格颜色规则
     * 需与isAlertBackgroundColorByDynamic配合使用
     */
    private Map<Integer, Map<String, String>> alertBackgroundColorByDynamicRuleMap;


    public ExcelExtendUtil(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 处理表头数据-深度优先
     *
     * @param data
     * @return
     */
    private static List<List<String>> head(List<JsonData> data) {
        List<List<String>> result = new ArrayList<>();

        List<String> temp = new ArrayList<>();

        // 深度优先搜索 使用递归
        for (JsonData jsonData : data) {
            dfs(result, jsonData, temp);
        }

        return result;
    }

    /**
     * 深度搜索复制
     *
     * @param result
     * @param jsonData
     * @param temp
     */
    private static void dfs(List<List<String>> result, JsonData jsonData, List<String> temp) {
        List<JsonData> datas = jsonData.getChildren();
        temp.add(jsonData.getName());
        if (datas == null) {
            //看似浅复制，其实是深复制
            List<String> head = new ArrayList<>(temp);
            result.add(head);
        } else {
            for (JsonData data : datas) {
                dfs(result, data, temp);
            }
        }

        // 移除最后一个
        temp.remove(temp.size() - 1);
    }

    /**
     * 获取画布
     */
    public static Drawing<?> getDrawingPatriarch(Sheet sheet) {
        if (sheet.getDrawingPatriarch() == null) {
            sheet.createDrawingPatriarch();
        }
        return sheet.getDrawingPatriarch();
    }

    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String convertByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(separator, propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[0].equals(value)) {
                        propertyString.append(itemArray[1] + separator);
                        break;
                    }
                }
            } else {
                if (itemArray[0].equals(propertyValue)) {
                    return itemArray[1];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    /**
     * 反向解析值 男=0,女=1,未知=2
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     */
    public static String reverseByExp(String propertyValue, String converterExp, String separator) {
        StringBuilder propertyString = new StringBuilder();
        String[] convertSource = converterExp.split(",");
        for (String item : convertSource) {
            String[] itemArray = item.split("=");
            if (StringUtils.containsAny(separator, propertyValue)) {
                for (String value : propertyValue.split(separator)) {
                    if (itemArray[1].equals(value)) {
                        propertyString.append(itemArray[0] + separator);
                        break;
                    }
                }
            } else {
                if (itemArray[1].equals(propertyValue)) {
                    return itemArray[0];
                }
            }
        }
        return StringUtils.stripEnd(propertyString.toString(), separator);
    }

    public void init(List<T> list, String sheetName, String title, Type type) {
        if (list == null) {
            list = new ArrayList<T>();
        }
        this.list = list;
        this.sheetName = sheetName;
        this.type = type;
        this.title = title;
        createExcelField();
        createWorkbook();
        createTitle();
    }

    /**
     * 创建excel第一行标题
     */
    public void createTitle() {
        if (StringUtils.isNotEmpty(title)) {
            Row titleRow = sheet.createRow(titleRowIndex == null ? 0 : titleRowIndex);
            titleRow.setHeightInPoints(30);
            Cell titleCell = titleRow.createCell(0);
            titleCell.setCellStyle(styles.get("title"));
            titleCell.setCellValue(title);
            sheet.addMergedRegion(new CellRangeAddress(titleRow.getRowNum(), titleRow.getRowNum(), titleRow.getRowNum(),
                    this.fields.size() - 1));
        }
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param is 输入流
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is) throws Exception {
        return importExcel(is, 0);
    }

    /**
     * 对excel表单默认第一个索引名转换成list
     *
     * @param is       输入流
     * @param titleNum 标题占用行数
     * @return 转换后集合
     */
    public List<T> importExcel(InputStream is, int titleNum) throws Exception {
        return importExcel(StringUtils.EMPTY, is, titleNum);
    }

    /**
     * 对excel表单指定表格索引名转换成list
     *
     * @param sheetName 表格索引名
     * @param titleNum  标题占用行数
     * @param is        输入流
     * @return 转换后集合
     */
    public List<T> importExcel(String sheetName, InputStream is, int titleNum) throws Exception {
        this.type = Type.IMPORT;
        this.wb = WorkbookFactory.create(is);
        List<T> list = new ArrayList<T>();
        // 如果指定sheet名,则取指定sheet中的内容 否则默认指向第1个sheet
        Sheet sheet = StringUtils.isNotEmpty(sheetName) ? wb.getSheet(sheetName) : wb.getSheetAt(0);
        if (sheet == null) {
            throw new IOException("文件sheet不存在");
        }

        // 获取最后一个非空行的行下标，比如总行数为n，则返回的为n-1
        int rows = sheet.getLastRowNum();

        if (rows > 0) {
            // 定义一个map用于存放excel列的序号和field.
            Map<String, Integer> cellMap = new HashMap<String, Integer>();
            // 获取表头
            Row heard = sheet.getRow(titleNum);
            for (int i = 0; i < heard.getPhysicalNumberOfCells(); i++) {
                Cell cell = heard.getCell(i);
                if (StringUtils.isNotNull(cell)) {
                    String value = this.getCellValue(heard, i).toString();
                    cellMap.put(value, i);
                } else {
                    cellMap.put(null, i);
                }
            }
            // 有数据时才处理 得到类的所有field.
            List<Object[]> fields = this.getFields();
            Map<Integer, Object[]> fieldsMap = new HashMap<Integer, Object[]>();
            for (Object[] objects : fields) {
                Excel attr = (Excel) objects[1];
                Integer column = cellMap.get(attr.name());
                if (column != null) {
                    fieldsMap.put(column, objects);
                }
            }
            for (int i = titleNum + 1; i <= rows; i++) {
                // 从第2行开始取数据,默认第一行是表头.
                Row row = sheet.getRow(i);
                // 判断当前行是否是空行
                if (isRowEmpty(row)) {
                    continue;
                }
                T entity = null;
                for (Map.Entry<Integer, Object[]> entry : fieldsMap.entrySet()) {
                    Object val = this.getCellValue(row, entry.getKey());

                    // 如果不存在实例则新建.
                    entity = (entity == null ? clazz.newInstance() : entity);
                    // 从map中得到对应列的field.
                    Field field = (Field) entry.getValue()[0];
                    Excel attr = (Excel) entry.getValue()[1];
                    // 取得类型,并根据对象类型设置值.
                    Class<?> fieldType = field.getType();
                    if (String.class == fieldType) {
                        String s = Convert.toStr(val);
                        if (StringUtils.endsWith(s, ".0")) {
                            val = StringUtils.substringBefore(s, ".0");
                        } else {
                            String dateFormat = field.getAnnotation(Excel.class).dateFormat();
                            if (StringUtils.isNotEmpty(dateFormat)) {
                                val = parseDateToStr(dateFormat, (Date) val);
                            } else {
                                val = Convert.toStr(val);
                            }
                        }
                    } else if ((Integer.TYPE == fieldType || Integer.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val))) {
                        val = Convert.toInt(val);
                    } else if ((Long.TYPE == fieldType || Long.class == fieldType) && StringUtils.isNumeric(Convert.toStr(val))) {
                        val = Convert.toLong(val);
                    } else if (Double.TYPE == fieldType || Double.class == fieldType) {
                        val = Convert.toDouble(val);
                    } else if (Float.TYPE == fieldType || Float.class == fieldType) {
                        val = Convert.toFloat(val);
                    } else if (BigDecimal.class == fieldType) {
                        val = Convert.toBigDecimal(val);
                    } else if (Date.class == fieldType) {
                        if (val instanceof String) {
                            val = DateUtils.parseDate(val);
                        } else if (val instanceof Double) {
                            val = DateUtil.getJavaDate((Double) val);
                        }
                    } else if (Boolean.TYPE == fieldType || Boolean.class == fieldType) {
                        val = Convert.toBool(val, false);
                    }
                    if (StringUtils.isNotNull(fieldType)) {
                        String propertyName = field.getName();
                        if (StringUtils.isNotEmpty(attr.targetAttr())) {
                            propertyName = field.getName() + "." + attr.targetAttr();
                        } else if (StringUtils.isNotEmpty(attr.readConverterExp())) {
                            val = reverseByExp(Convert.toStr(val), attr.readConverterExp(), attr.separator());
                        } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                            val = dataFormatHandlerAdapter(val, attr);
                        }
                        ReflectUtils.invokeSetter(entity, propertyName, val);
                    }
                }
                list.add(entity);
            }
        }
        return list;
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param response  返回数据
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @return 结果
     * @throws IOException
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName) {
        exportExcel(response, list, sheetName, StringUtils.EMPTY);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param response  返回数据
     * @param list      导出数据集合
     * @param sheetName 工作表的名称
     * @param title     标题
     * @return 结果
     * @throws IOException
     */
    public void exportExcel(HttpServletResponse response, List<T> list, String sheetName, String title) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        this.init(list, sheetName, title, Type.EXPORT);
        exportExcel(response);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @return 结果
     */
    public void importTemplateExcel(HttpServletResponse response, String sheetName) {
        importTemplateExcel(response, sheetName, StringUtils.EMPTY);
    }

    //public static void main(String[] args) {
    //    String data = getDataString();
    //    JSONObject jsonObject = JSONObject.parseObject(data);
    //    //读取
    //    JSONObject jsonObject1 = jsonObject.getJSONObject("data");
    //    JSONArray jsonArray = jsonObject1.getJSONArray("headerNodes");
    //
    //    List<JsonData> list = jsonArray.toJavaList(JsonData.class);
    //
    //    System.out.println(list);
    //    List<List<String>> head = head(list);
    //    System.out.println(head);
    //}

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     * @param title     标题
     * @return 结果
     */
    public void importTemplateExcel(HttpServletResponse response, String sheetName, String title) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        this.init(null, sheetName, title, Type.IMPORT);
        exportExcel(response);
    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @return 结果
     */
    public void exportExcel(HttpServletResponse response) {
        try {
            writeSheet();
            wb.write(response.getOutputStream());
        } catch (Exception e) {
            log.error("导出Excel异常{}", e.getMessage());
        } finally {
            IOUtils.closeQuietly(wb);
        }
    }

    /**
     * 创建写入数据到Sheet
     */
    public void writeSheet() {
        // 取出一共有多少个sheet.
        int sheetNo = Math.max(1, (int) Math.ceil(list.size() * 1.0 / sheetSize));
        for (int index = 0; index < sheetNo; index++) {
            createSheet(sheetNo, index);

            // 产生表头信息开始一行

            this.tableTopStartIndex = this.titleRowIndex + 1;
            Row row = sheet.createRow(this.tableTopStartIndex);

            int column = 0;
            // TODO 动态添加多级表头
            if (this.openMultilevelTableTop) {
                try {
                    this.createTableHead();
                    this.tableTopMaxIndex = tableTopRowMap.entrySet().stream().map(Map.Entry::getKey).max(Comparator.comparing(i -> i)).get();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                //根据字段注解配置参数填充表头
                for (Object[] os : fields) {
                    Excel excel = (Excel) os[1];
                    this.createCell(excel, row, column++);
                }
            }
            //表数据开始索引计算
            this.tableDataStartIndex = this.tableTopStartIndex + this.tableTopMaxIndex;
            Row startDataRow = sheet.createRow(this.tableDataStartIndex);

            if (Type.EXPORT.equals(type)) {
                fillExcelData(index, startDataRow);
                addStatisticsRow();
            }
        }
    }

    /**
     * 创建多级表头
     */
    public void createTableHead() {
        List<JsonData> list = new ArrayList<>();
        if (StringUtils.isNotEmpty(tableTopJsonData) || (StringUtils.isNotEmpty(tableTopJsonData) && this.tableTopJsonDataList != null)) {
            JSONArray jsonArray = JSON.parseArray(this.tableTopJsonData);
            list = jsonArray.toJavaList(JsonData.class);
        } else if (StringUtils.isEmpty(tableTopJsonData) && this.tableTopJsonDataList != null) {
            list = this.tableTopJsonDataList;
        } else {
            throw new RuntimeException("请设置表头Json数据字符串或者列表！");
        }

        Row row = sheet.createRow(this.tableTopStartIndex);
        for (int i = 0; i < list.size(); i++) {
            this.recursionCreateTableTopCell(list.get(i), row);
        }

        //EasyExcel多表头格式
        //List<List<String>> headList = head(list);

    }

    /**
     * 递归-深度优先 创建表格单元格
     *
     * @param jsonData
     * @param row
     * @return
     */
    public Cell recursionCreateTableTopCell(JsonData jsonData, Row row) {
        this.tableTopRowMap.put(row.getRowNum(), row);


        // 创建列
        Cell cell = row.createCell(jsonData.getColIndex().intValue());
        // 写入列信息
        cell.setCellValue(jsonData.getName());
        //setDataValidation2(jsonData.getColIndex().intValue());
        cell.setCellStyle(styles.get("header"));

        //合并规则信息
        String spanRow = jsonData.getSpanRow();
        String spanCol = jsonData.getSpanCol();
        //处理多级表头行合并
        if (spanRow != null) {
            String[] spanRowArray = spanRow.split(",");
            int spanRowStartIndex = Integer.parseInt(spanRowArray[0]);
            int spanRowEndIndex = Integer.parseInt(spanRowArray[1]);
            int spanColStartIndex = jsonData.getColIndex().intValue();
            int spanColEndIndex = jsonData.getColIndex().intValue();

            CellRangeAddress region = new CellRangeAddress(spanRowStartIndex + this.tableTopStartIndex, spanRowEndIndex + this.tableTopStartIndex, spanColStartIndex, spanColEndIndex);
            sheet.addMergedRegion(region);
        }
        //处理多级表头列合并
        if (spanCol != null) {
            String[] spanColArray = spanCol.split(",");
            int spanRowStartIndex = jsonData.getRowIndex().intValue();
            int spanRowEndIndex = jsonData.getRowIndex().intValue();
            int spanColStartIndex = Integer.parseInt(spanColArray[0]);
            int spanColEndIndex = Integer.parseInt(spanColArray[1]);

            CellRangeAddress region = new CellRangeAddress(spanRowStartIndex + this.tableTopStartIndex, spanRowEndIndex + this.tableTopStartIndex, spanColStartIndex, spanColEndIndex);
            sheet.addMergedRegion(region);
        }

        //判断是否有子集进入递归
        List<JsonData> children = jsonData.getChildren();
        if (children != null) {
            int rowIndex = row.getRowNum() + 1;
            Row historyRow = this.tableTopRowMap.get(rowIndex);
            if (historyRow == null) {
                historyRow = sheet.createRow(rowIndex);
            }
            for (int i = 0; i < children.size(); i++) {
                JsonData sonJsonData = children.get(i);
                this.recursionCreateTableTopCell(sonJsonData, historyRow);
            }
        }

        return cell;
    }

    /**
     * 填充excel数据
     *
     * @param index 序号
     * @param row   单元格行
     */
    public void fillExcelData(int index, Row row) {
        int startNo = index;
        int endNo = Math.min(startNo + sheetSize, list.size());


        //当前行
        int thisLine = startNo;
        for (int i = startNo; i < endNo; i++) {
            if (i > 0) {
                row = sheet.createRow(row.getRowNum() + 1 - startNo);
            }
            thisLine = row.getRowNum();
            // 得到导出对象.
            T vo = (T) list.get(i);
            T voPrevious = null;
            //得到上一个导出对象
            if (i != startNo) {
                voPrevious = (T) list.get(i - 1);
            }
            /**
             *取下一个对象   与当前对象对比，如果相同，记住当前列，再与下一个对比，一直对比到不相同，执行合并代码
             * 注解加入 合并行列标识
             */
            int column = 0;
            for (Object[] os : fields) {
                Field field = (Field) os[0];
                Excel excel = (Excel) os[1];
                // 设置实体类私有属性可访问
                field.setAccessible(true);
                //每个列加入辅助参数对象
                this.addCell(excel, row, vo, field, column++, voPrevious, thisLine);

            }
        }

    }

    /**
     * 创建表格样式
     *
     * @param wb 工作薄对象
     * @return 样式列表
     */
    private Map<String, CellStyle> createStyles(Workbook wb) {
        // 写入各条记录,每条记录对应excel表中的一行
        Map<String, CellStyle> styles = new HashMap<String, CellStyle>();
        CellStyle style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font titleFont = wb.createFont();
        titleFont.setFontName("Arial");
        titleFont.setFontHeightInPoints((short) 16);
        titleFont.setBold(true);
        style.setFont(titleFont);
        styles.put("title", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setBorderRight(BorderStyle.THIN);
        style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderLeft(BorderStyle.THIN);
        style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderTop(BorderStyle.THIN);
        style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setBorderBottom(BorderStyle.THIN);
        style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
        Font dataFont = wb.createFont();
        dataFont.setFontName("Arial");
        dataFont.setFontHeightInPoints((short) 10);
        style.setFont(dataFont);
        style.setWrapText(true); //TODO 设置自动换行不起作用
        styles.put("data", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        style.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        Font headerFont = wb.createFont();
        headerFont.setFontName("Arial");
        headerFont.setFontHeightInPoints((short) 10);
        headerFont.setBold(true);
        headerFont.setColor(IndexedColors.WHITE.getIndex());
        style.setFont(headerFont);
        styles.put("header", style);

        style = wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        Font totalFont = wb.createFont();
        totalFont.setFontName("Arial");
        totalFont.setFontHeightInPoints((short) 10);
        style.setFont(totalFont);
        styles.put("total", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.LEFT);
        styles.put("data1", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.CENTER);
        styles.put("data2", style);

        style = wb.createCellStyle();
        style.cloneStyleFrom(styles.get("data"));
        style.setAlignment(HorizontalAlignment.RIGHT);
        styles.put("data3", style);

        return styles;
    }

    /**
     * 创建单元格
     */
    public Cell createCell(Excel attr, Row row, int column) {
        // 创建列
        Cell cell = row.createCell(column);
        // 写入列信息
        cell.setCellValue(attr.name());
        setDataValidation(attr, row, column);
        cell.setCellStyle(styles.get("header"));
        return cell;
    }

    /**
     * 设置单元格信息
     *
     * @param value 单元格值
     * @param attr  注解相关
     * @param cell  单元格信息
     */
    public void setCellVo(Object value, Excel attr, Cell cell) {
        if (ColumnType.STRING == attr.cellType()) {
            String cellValue = Convert.toStr(value);
            // 对于任何以表达式触发字符 =-+@开头的单元格，直接使用tab字符作为前缀，防止CSV注入。
            if (StringUtils.containsAny(cellValue, FORMULA_STR)) {
                cellValue = StringUtils.replaceEach(cellValue, FORMULA_STR, new String[]{"\t=", "\t-", "\t+", "\t@"});
            }
            cell.setCellValue(StringUtils.isNull(cellValue) ? attr.defaultValue() : cellValue + attr.suffix());
        } else if (ColumnType.NUMERIC == attr.cellType()) {
            if (StringUtils.isNotNull(value)) {
                // cell.setCellValue(StringUtils.contains(Convert.toStr(value), ".") ? Convert.toDouble(value) : Convert.toInt(value));
                if (StringUtils.contains(Convert.toStr(value), ".")) {
                    DataFormat df = this.wb.createDataFormat();
                    cell.getCellStyle().setDataFormat(df.getFormat("0.000_"));
                    cell.setCellValue(Convert.toDouble(value));
                } else {
                    DataFormat df = this.wb.createDataFormat();
                    cell.getCellStyle().setDataFormat(df.getFormat("0_"));
                    cell.setCellValue(Convert.toInt(value));
                }
            }
        } else if (ColumnType.IMAGE == attr.cellType()) {
            ClientAnchor anchor = new XSSFClientAnchor(0, 0, 0, 0, (short) cell.getColumnIndex(), cell.getRow().getRowNum(), (short) (cell.getColumnIndex() + 1), cell.getRow().getRowNum() + 1);
            String imagePath = Convert.toStr(value);
            if (StringUtils.isNotEmpty(imagePath)) {
                byte[] data = ImageUtils.getImage(imagePath);
                getDrawingPatriarch(cell.getSheet()).createPicture(anchor,
                        cell.getSheet().getWorkbook().addPicture(data, getImageType(data)));
            }
        }
    }

    /**
     * 获取图片类型,设置图片插入类型
     */
    public int getImageType(byte[] value) {
        String type = FileTypeUtils.getFileExtendName(value);
        if ("JPG".equalsIgnoreCase(type)) {
            return Workbook.PICTURE_TYPE_JPEG;
        } else if ("PNG".equalsIgnoreCase(type)) {
            return Workbook.PICTURE_TYPE_PNG;
        }
        return Workbook.PICTURE_TYPE_JPEG;
    }

    /**
     * 创建表格样式
     */
    public void setDataValidation(Excel attr, Row row, int column) {
        if (attr.name().indexOf("注：") >= 0) {
            sheet.setColumnWidth(column, 6000);
        } else {
            // 设置列宽
            sheet.setColumnWidth(column, (int) ((attr.width() + 0.72) * 256));
        }
        // 如果设置了提示信息则鼠标放上去提示.
        if (StringUtils.isNotEmpty(attr.prompt())) {
            // 这里默认设了2-101列提示.
            setXSSFPrompt(sheet, "", attr.prompt(), 1, 100, column, column);
        }
        // 如果设置了combo属性则本列只能选择不能输入
        if (attr.combo().length > 0) {
            // 这里默认设了2-101列只能选择不能输入.
            setXSSFValidation(sheet, attr.combo(), 1, 100, column, column);
        }
    }

    /**
     * 创建表格样式
     */
    public void setDataValidation2(int column) {
        // 设置列宽
        sheet.setColumnWidth(column, (int) ((60 + 0.72) * 256));
    }

    /**
     * 添加单元格
     */
    public Cell addCell(Excel attr, Row row, T vo, Field field, int column, T voPrevious, int thisLine) {
        Cell cell = null;
        try {
            // 设置行高
            row.setHeight(maxHeight);
            // 根据Excel中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
            if (attr.isExport()) {
                // 创建cell
                cell = row.createCell(column);
                int align = attr.align().value();

                //索引值背景色改变
                //CellStyle style = wb.createCellStyle();
                //style.setFillForegroundColor(IndexedColors.RED.getIndex());
                //style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                //cell.setCellStyle(style);


                int rowIndex = row.getRowNum();


                Cell finalCell = cell;
                // TODO 处理动态数据改变颜色
                if (attr.isAlertBackgroundColorByDynamic()) {
                    if (this.alertBackgroundColorByDynamicRuleMap == null) {
                        throw new RuntimeException("未设置动态数据改变颜色规则集合！");
                    }
                    if (StringUtils.isEmpty(attr.colorValueField())) {
                        throw new RuntimeException("@Excel为设置定数据实体类的颜色对应属性！");
                    }
                    this.alertBackgroundColorByDynamicRuleMap.entrySet().stream().forEach(i -> {
                        Map<String, String> colorMap = i.getValue();
                        if (rowIndex == i.getKey() + this.tableDataStartIndex) {
                            String color = colorMap.get(attr.colorValueField());
                            if (StringUtils.isNotEmpty(color)) {

                                this.handleHexBackgroundColor(finalCell, color);

                            }
                        }
                    });

                }

                // TODO 处理自定义颜色
                if (attr.isAlertBackgroundColorByCustom()) {
                    if (StringUtils.isEmpty(attr.colorValueCustom())) {
                        throw new RuntimeException("@Excel为设置自定义颜色值！");
                    }
                    if (rowIndex == attr.alertBackgroundColorByCustomRowIndex() + this.tableDataStartIndex) {
                        String color = attr.colorValueCustom();
                        if (StringUtils.isNotEmpty(color)) {
                            this.handleHexBackgroundColor(finalCell, color);
                        }
                    }
                }


                //默认样式
                if (!attr.isAlertBackgroundColorByDynamic() && !attr.isAlertBackgroundColorByCustom()) {
                    cell.setCellStyle(styles.get("data" + (align >= 1 && align <= 3 ? align : "")));
                }

                // 用于读取对象中的属性
                Object value = getTargetValue(vo, field, attr);

                //记录上一个对象属性
                Object valuePrevious = null;
                if (voPrevious != null) {
                    valuePrevious = getTargetValue(voPrevious, field, attr);
                }
                String dateFormat = attr.dateFormat();
                String readConverterExp = attr.readConverterExp();
                String separator = attr.separator();
                String spanCell = attr.spanCell();
                String spanRow = attr.spanRow();
                Long index = attr.index();
                Excel.QuickSpanRule quickSpanRule = attr.quickSpanRule();
                boolean isQuickSpan = attr.isQuickSpan();


                if (StringUtils.isNotEmpty(dateFormat) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(parseDateToStr(dateFormat, (Date) value));
                } else if (StringUtils.isNotEmpty(readConverterExp) && StringUtils.isNotNull(value)) {
                    cell.setCellValue(convertByExp(Convert.toStr(value), readConverterExp, separator));
                } else if (value instanceof BigDecimal && -1 != attr.scale()) {
                    cell.setCellValue((((BigDecimal) value).setScale(attr.scale(), attr.roundingMode())).toString());
                } else if (!attr.handler().equals(ExcelHandlerAdapter.class)) {
                    cell.setCellValue(dataFormatHandlerAdapter(value, attr));
                } else {
                    // 设置列类型
                    setCellVo(value, attr, cell);
                }
                addStatisticsData(column, Convert.toStr(value), attr);

                //快速合并
                if (isQuickSpan) {

                    if (quickSpanRule.value() == Excel.QuickSpanRule.IDENTICAL_ROW_MERGE.value()) {
                        ExcelFieldAdditionParams fieldAdditionParams = this.fieldAdditionParamsMap.get(index);
                        if (value.equals(valuePrevious)) {
                            if (fieldAdditionParams.getMergeRowStart() == 0) {
                                fieldAdditionParams.setMergeRowStart(thisLine - 1);
                            }
                            fieldAdditionParams.setMergeRowEnd(thisLine);
                            //this.mergeRowEnd = thisLine;
                        } else {
                            if (fieldAdditionParams.getMergeRowStart() != 0 && fieldAdditionParams.getMergeRowEnd() != 0) {
                                if (fieldAdditionParams.getMergeRowStart() != fieldAdditionParams.getMergeRowEnd()) {
                                    CellRangeAddress region = new CellRangeAddress(fieldAdditionParams.getMergeRowStart(), fieldAdditionParams.getMergeRowEnd(), index.intValue(), index.intValue());
                                    sheet.addMergedRegion(region);
                                    //this.mergeRowStart = 0;
                                    //this.mergeRowEnd = 0;
                                }

                                //多列同时设置这两个数据会混乱 解决方法：每一列生成一个单独的对象，相互独立
                                //this.mergeRowStart = 0;
                                //this.mergeRowEnd = 0;
                                fieldAdditionParams.setMergeRowStart(0);
                                fieldAdditionParams.setMergeRowEnd(0);
                            }
                        }
                    }

                }


                //通用合并行
                if (StringUtils.isNotEmpty(spanRow)) {
                    String[] spanRowArray = spanRow.split("\\|");
                    List<String[]> spanRowRuleList = Arrays.stream(spanRowArray).map(i -> {
                        return i.split(",");
                    }).collect(Collectors.toList());
                    spanRowRuleList.stream().forEach(i -> {
                        Integer spanRowStart = Integer.parseInt(i[0].trim());
                        Integer spanRowEnd = Integer.parseInt(i[1].trim());
                        Long fieldIndex = null;
                        if (i.length == 3) {
                            fieldIndex = Long.parseLong(i[2].trim());
                        } else if (index != -1) {
                            fieldIndex = index;
                        } else {
                            throw new RuntimeException("列索引规则配置错误，请检查字段索引值是否配置！");
                        }

                        CellRangeAddress region = new CellRangeAddress(spanRowStart + this.tableDataStartIndex, spanRowEnd + this.tableDataStartIndex, fieldIndex.intValue(), fieldIndex.intValue());
                        sheet.addMergedRegion(region);
                    });
                }
                //通用合并单元格（包括行合并和列合并）
                if (StringUtils.isNotEmpty(spanCell)) {
                    String[] spanCellArray = spanCell.split("\\|");
                    List<String[]> spanCellRuleList = Arrays.stream(spanCellArray).map(i -> {
                        return i.split(",");
                    }).collect(Collectors.toList());
                    spanCellRuleList.stream().forEach(i -> {
                        Integer spanRowStart = Integer.parseInt(i[0].trim());
                        Integer spanRowEnd = Integer.parseInt(i[1].trim());
                        Integer spanCellStart = Integer.parseInt(i[2].trim());
                        Integer spanCellEnd = Integer.parseInt(i[3].trim());
                        CellRangeAddress region = new CellRangeAddress(spanRowStart + this.tableDataStartIndex, spanRowEnd + this.tableDataStartIndex, spanCellStart, spanCellEnd);
                        sheet.addMergedRegion(region);
                    });
                }
                //CellRangeAddress region1 = new CellRangeAddress(1, 18, 0, 0);
                //sheet.addMergedRegion(region1);
                //CellRangeAddress region2 = new CellRangeAddress(19, 21, 0, 1);
                //sheet.addMergedRegion(region2);

            }
        } catch (Exception e) {
            log.error("导出Excel失败{}", e);
        }
        return cell;
    }

    /**
     * 处理单元格背景颜色根据16进制颜色代码
     *
     * @param cell
     * @param color
     */
    public void handleHexBackgroundColor(Cell cell, String color) {

        try {
            if (StringUtils.isEmpty(color)) {
                throw new RuntimeException("颜色代码为空！");
            }
            if (!color.contains("#")) {
                throw new RuntimeException("规则中的颜色代码格式不正确，请使用颜色16进制代码！");
            }
            String rgbS = color.substring(1);
            byte[] rgbB = new byte[0]; // get byte array from hex string
            rgbB = Hex.decodeHex(rgbS);
            XSSFColor finalColor = new XSSFColor(rgbB, null); //IndexedColorMap has no usage until now. So it can be set null.
            XSSFCellStyle style = (XSSFCellStyle) wb.createCellStyle();
            style.setAlignment(HorizontalAlignment.CENTER);
            style.setVerticalAlignment(VerticalAlignment.CENTER);
            style.setBorderRight(BorderStyle.THIN);
            style.setRightBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderLeft(BorderStyle.THIN);
            style.setLeftBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderTop(BorderStyle.THIN);
            style.setTopBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            style.setBorderBottom(BorderStyle.THIN);
            style.setBottomBorderColor(IndexedColors.GREY_50_PERCENT.getIndex());
            Font dataFont = wb.createFont();
            dataFont.setFontName("Arial");
            dataFont.setFontHeightInPoints((short) 10);
            style.setFont(dataFont);
            style.setWrapText(true);
            style.setFillForegroundColor(finalColor);
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
        } catch (DecoderException e) {
            e.printStackTrace();
        }

    }

    /**
     * 设置 POI XSSFSheet 单元格提示
     *
     * @param sheet         表单
     * @param promptTitle   提示标题
     * @param promptContent 提示内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     */
    public void setXSSFPrompt(Sheet sheet, String promptTitle, String promptContent, int firstRow, int endRow,
                              int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        DataValidationConstraint constraint = helper.createCustomConstraint("DD1");
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        dataValidation.createPromptBox(promptTitle, promptContent);
        dataValidation.setShowPromptBox(true);
        sheet.addValidationData(dataValidation);
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public void setXSSFValidation(Sheet sheet, String[] textlist, int firstRow, int endRow, int firstCol, int endCol) {
        DataValidationHelper helper = sheet.getDataValidationHelper();
        // 加载下拉列表内容
        DataValidationConstraint constraint = helper.createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow, endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation dataValidation = helper.createValidation(constraint, regions);
        // 处理Excel兼容性问题
        if (dataValidation instanceof XSSFDataValidation) {
            dataValidation.setSuppressDropDownArrow(true);
            dataValidation.setShowErrorBox(true);
        } else {
            dataValidation.setSuppressDropDownArrow(false);
        }

        sheet.addValidationData(dataValidation);
    }

    /**
     * 数据处理器
     *
     * @param value 数据值
     * @param excel 数据注解
     * @return
     */
    public String dataFormatHandlerAdapter(Object value, Excel excel) {
        try {
            Object instance = excel.handler().newInstance();
            Method formatMethod = excel.handler().getMethod("format", new Class[]{Object.class, String[].class});
            value = formatMethod.invoke(instance, value, excel.args());
        } catch (Exception e) {
            log.error("不能格式化数据 " + excel.handler(), e.getMessage());
        }
        return Convert.toStr(value);
    }

    /**
     * 合计统计信息
     */
    private void addStatisticsData(Integer index, String text, Excel entity) {
        if (entity != null && entity.isStatistics()) {
            Double temp = 0D;
            if (!statistics.containsKey(index)) {
                statistics.put(index, temp);
            }
            try {
                temp = Double.valueOf(text);
            } catch (NumberFormatException e) {
            }
            statistics.put(index, statistics.get(index) + temp);
        }
    }

    /**
     * 创建统计行
     */
    public void addStatisticsRow() {
        if (statistics.size() > 0) {
            Row row = sheet.createRow(sheet.getLastRowNum() + 1);
            Set<Integer> keys = statistics.keySet();
            Cell cell = row.createCell(0);
            cell.setCellStyle(styles.get("total"));
            cell.setCellValue("合计");

            for (Integer key : keys) {
                cell = row.createCell(key);
                cell.setCellStyle(styles.get("total"));
                cell.setCellValue(DOUBLE_FORMAT.format(statistics.get(key)));
            }
            statistics.clear();
        }
    }

    /**
     * 获取bean中的属性值
     *
     * @param vo    实体对象
     * @param field 字段
     * @param excel 注解
     * @return 最终的属性值
     * @throws Exception
     */
    private Object getTargetValue(T vo, Field field, Excel excel) throws Exception {
        Object o = field.get(vo);
        if (StringUtils.isNotEmpty(excel.targetAttr())) {
            String target = excel.targetAttr();
            if (target.contains(".")) {
                String[] targets = target.split("[.]");
                for (String name : targets) {
                    o = getValue(o, name);
                }
            } else {
                o = getValue(o, target);
            }
        }
        return o;
    }

    /**
     * 以类的属性的get方法方法形式获取值
     *
     * @param o
     * @param name
     * @return value
     * @throws Exception
     */
    private Object getValue(Object o, String name) throws Exception {
        if (StringUtils.isNotNull(o) && StringUtils.isNotEmpty(name)) {
            Class<?> clazz = o.getClass();
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            o = field.get(o);
        }
        return o;
    }

    /**
     * 得到所有定义字段
     */
    private void createExcelField() {
        this.fields = getFields();
        this.fields = this.fields.stream().sorted(Comparator.comparing(objects -> ((Excel) objects[1]).sort())).collect(Collectors.toList());
        this.fieldAdditionParamsMap = this.fields.stream().map(i -> {
            Excel excel = (Excel) i[1];
            return excel;
        }).filter(excel -> excel.index() != -1).collect(Collectors.toMap(Excel::index, i -> new ExcelFieldAdditionParams()));
        this.maxHeight = getRowHeight();
    }

    /**
     * 获取字段注解信息
     */
    public List<Object[]> getFields() {
        List<Object[]> fields = new ArrayList<Object[]>();
        List<Field> tempFields = new ArrayList<>();
        tempFields.addAll(Arrays.asList(clazz.getSuperclass().getDeclaredFields()));
        tempFields.addAll(Arrays.asList(clazz.getDeclaredFields()));
        for (Field field : tempFields) {
            // 单注解
            if (field.isAnnotationPresent(Excel.class)) {
                Excel attr = field.getAnnotation(Excel.class);
                if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
                    field.setAccessible(true);
                    fields.add(new Object[]{field, attr});
                }
            }

            // 多注解
            if (field.isAnnotationPresent(Excels.class)) {
                Excels attrs = field.getAnnotation(Excels.class);
                Excel[] excels = attrs.value();
                for (Excel attr : excels) {
                    if (attr != null && (attr.type() == Type.ALL || attr.type() == type)) {
                        field.setAccessible(true);
                        fields.add(new Object[]{field, attr});
                    }
                }
            }
        }
        return fields;
    }

    /**
     * 根据注解获取最大行高
     */
    public short getRowHeight() {
        double maxHeight = 0;
        for (Object[] os : this.fields) {
            Excel excel = (Excel) os[1];
            maxHeight = Math.max(maxHeight, excel.height());
        }
        return (short) (maxHeight * 20);
    }

    /**
     * 创建一个工作簿
     */
    public void createWorkbook() {
        this.wb = new SXSSFWorkbook(500);
        this.sheet = wb.createSheet();
        wb.setSheetName(0, sheetName);
        this.styles = createStyles(wb);
    }

    /**
     * 创建工作表
     *
     * @param sheetNo sheet数量
     * @param index   序号
     */
    public void createSheet(int sheetNo, int index) {
        // 设置工作表的名称.
        if (sheetNo > 1 && index > 0) {
            this.sheet = wb.createSheet();
            this.createTitle();
            wb.setSheetName(index, sheetName + index);
        }
    }

    /**
     * 获取单元格值
     *
     * @param row    获取的行
     * @param column 获取单元格列号
     * @return 单元格值
     */
    public Object getCellValue(Row row, int column) {
        if (row == null) {
            return row;
        }
        Object val = "";
        try {
            Cell cell = row.getCell(column);
            if (StringUtils.isNotNull(cell)) {
                if (cell.getCellType() == CellType.NUMERIC || cell.getCellType() == CellType.FORMULA) {
                    val = cell.getNumericCellValue();
                    if (DateUtil.isCellDateFormatted(cell)) {
                        val = DateUtil.getJavaDate((Double) val); // POI Excel 日期格式转换
                    } else {
                        if ((Double) val % 1 != 0) {
                            val = new BigDecimal(val.toString());
                        } else {
                            val = new DecimalFormat("0").format(val);
                        }
                    }
                } else if (cell.getCellType() == CellType.STRING) {
                    val = cell.getStringCellValue();
                } else if (cell.getCellType() == CellType.BOOLEAN) {
                    val = cell.getBooleanCellValue();
                } else if (cell.getCellType() == CellType.ERROR) {
                    val = cell.getErrorCellValue();
                }

            }
        } catch (Exception e) {
            return val;
        }
        return val;
    }

    /**
     * 判断是否是空行
     *
     * @param row 判断的行
     * @return
     */
    private boolean isRowEmpty(Row row) {
        if (row == null) {
            return true;
        }
        for (int i = row.getFirstCellNum(); i < row.getLastCellNum(); i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }

    /**
     * 格式化不同类型的日期对象
     *
     * @param dateFormat 日期格式
     * @param val        被格式化的日期对象
     * @return 格式化后的日期字符
     */
    public String parseDateToStr(String dateFormat, Object val) {
        if (val == null) {
            return "";
        }
        String str;
        if (val instanceof Date) {
            str = DateUtils.parseDateToStr(dateFormat, (Date) val);
        } else if (val instanceof LocalDateTime) {
            str = DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDateTime) val));
        } else if (val instanceof LocalDate) {
            str = DateUtils.parseDateToStr(dateFormat, DateUtils.toDate((LocalDate) val));
        } else {
            str = val.toString();
        }
        return str;
    }

    public Integer getTitleRowIndex() {
        return titleRowIndex;
    }

    public void setTitleRowIndex(Integer titleRowIndex) {
        this.titleRowIndex = titleRowIndex;
    }

    public boolean isOpenMultilevelTableTop() {
        return openMultilevelTableTop;
    }

    public void setOpenMultilevelTableTop(boolean openMultilevelTableTop) {
        this.openMultilevelTableTop = openMultilevelTableTop;
    }

    public String getTableTopJsonData() {
        return tableTopJsonData;
    }

    public void setTableTopJsonData(String tableTopJsonData) {
        this.tableTopJsonData = tableTopJsonData;
    }

    public List<JsonData> getTableTopJsonDataList() {
        return tableTopJsonDataList;
    }

    public void setTableTopJsonDataList(List<JsonData> tableTopJsonDataList) {
        this.tableTopJsonDataList = tableTopJsonDataList;
    }

    public Map<Integer, Map<String, String>> getAlertBackgroundColorByDynamicRuleMap() {
        return alertBackgroundColorByDynamicRuleMap;
    }

    public void setAlertBackgroundColorByDynamicRuleMap(Map<Integer, Map<String, String>> alertBackgroundColorByDynamicRuleMap) {
        this.alertBackgroundColorByDynamicRuleMap = alertBackgroundColorByDynamicRuleMap;
    }

    /**
     * 解析的表头实例对象
     */
    @Data
    static class JsonData {
        /**
         * 唯一标识
         */
        private String code;
        /**
         * 子集
         */
        private List<JsonData> children;
        /**
         * 父唯一标识
         */
        private String parentCode;
        /**
         * 单元格内容
         */
        private String name;
        /**
         * 列索引，从0开始
         */
        private Long colIndex;
        /**
         * 行索引，从0开始，使用时不考虑标题列
         */
        private Long rowIndex;
        /**
         * 行合并，比如0,4
         */
        private String spanRow;
        /**
         * 列合并，比如0,4
         */
        private String spanCol;
    }

    /**
     * Excel列辅助参数对象类
     * 解决：
     * 多列同时设置这两个数据会混乱 解决方法：每一列生成一个单独的对象，相互独立
     * this.mergeRowStart = 0;
     * this.mergeRowEnd = 0;
     */
    @Data
    public class ExcelFieldAdditionParams {

        private int mergeRowStart = 0;
        private int mergeRowEnd = 0;
        private int mergeCellStart = 0;
        private int mergeCellEnd = 0;
    }
}

