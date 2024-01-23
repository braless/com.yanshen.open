package com.yanshen.common.core.annotation;


import com.yanshen.common.core.util.poi.ExcelHandlerAdapter;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.math.BigDecimal;

/**
 * 自定义导出Excel数据注解
 *
 * @author Yanshen
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Excel {
    /**
     * 导出时在excel中排序
     */
    public int sort() default Integer.MAX_VALUE;

    /**
     * 导出到Excel中的名字.
     */
    public String name() default "";

    /**
     * 日期格式, 如: yyyy-MM-dd
     */
    public String dateFormat() default "";

    /**
     * 读取内容转表达式 (如: 0=男,1=女,2=未知)
     */
    public String readConverterExp() default "";

    /**
     * 分隔符，读取字符串组内容
     */
    public String separator() default ",";

    /**
     * BigDecimal 精度 默认:-1(默认不开启BigDecimal格式化)
     */
    public int scale() default -1;

    /**
     * BigDecimal 舍入规则 默认:BigDecimal.ROUND_HALF_EVEN
     */
    public int roundingMode() default BigDecimal.ROUND_HALF_EVEN;

    /**
     * 导出类型（0数字 1字符串）
     */
    public ColumnType cellType() default ColumnType.STRING;

    /**
     * 导出时在excel中每个列的高度 单位为字符
     */
    public double height() default 14;

    /**
     * 导出时在excel中每个列的宽 单位为字符
     */
    public double width() default 16;

    /**
     * 文字后缀,如% 90 变成90%
     */
    public String suffix() default "";

    /**
     * 当值为空时,字段的默认值
     */
    public String defaultValue() default "";

    /**
     * 提示信息
     */
    public String prompt() default "";

    /**
     * 设置只能选择不能输入的列内容.
     */
    public String[] combo() default {};

    /**
     * 是否需要纵向合并单元格,应对需求:含有list集合单元格)
     */
    public boolean needMerge() default false;

    /**
     * 是否导出数据,应对需求:有时我们需要导出一份模板,这是标题需要但内容需要用户手工填写.
     */
    public boolean isExport() default true;

    /**
     * 另一个类中的属性名称,支持多级获取,以小数点隔开
     */
    public String targetAttr() default "";

    /**
     * 是否自动统计数据,在最后追加一行统计数据总和
     */
    public boolean isStatistics() default false;

    /**
     * 导出字段对齐方式（0：默认；1：靠左；2：居中；3：靠右）
     */
    public Align align() default Align.AUTO;

    /**
     * 导出列头背景色
     */
    public IndexedColors headerBackgroundColor() default IndexedColors.GREY_50_PERCENT;

    /**
     * 导出列头字体颜色
     */
    public IndexedColors headerColor() default IndexedColors.WHITE;

    /**
     * 导出单元格背景色
     */
    public IndexedColors backgroundColor() default IndexedColors.WHITE;

    /**
     * 导出单元格字体颜色
     */
    public IndexedColors color() default IndexedColors.BLACK;

    /**
     * 导出字段对齐方式
     */
    public HorizontalAlignment aligns() default HorizontalAlignment.CENTER;

    /**
     * 自定义数据处理器
     */
    public Class<?> handler() default ExcelHandlerAdapter.class;

    /**
     * 自定义数据处理器参数
     */
    public String[] args() default {};

    /**
     * 合并列
     *
     * @return
     */
    public String spanCell() default "";

    /**
     * 合并行
     *
     * @return
     */
    public String spanRow() default "";


    /**
     * 字段索引
     *
     * @return
     */
    public long index() default -1;

    /**
     * 是否快速合并
     */
    public boolean isQuickSpan() default false;

    /**
     * 是否根据动态数据改变背景色
     *
     * @return
     */
    public boolean isAlertBackgroundColorByDynamic() default false;

    /**
     * 指定根据列表数据实体类的属性进行变色，默认为color属性
     *
     * @return
     */
    public String colorValueField() default "color";

    /**
     * 是否根据自定义颜色值变色
     *
     * @return
     */
    public boolean isAlertBackgroundColorByCustom() default false;

    /**
     * 自定义颜色值
     *
     * @return
     */
    public String colorValueCustom() default "";

    /**
     * 自定改变颜色指定行索引
     *
     * @return
     */
    public int alertBackgroundColorByCustomRowIndex() default 0;

    /**
     * 快速合并规则
     *
     * @return
     */
    public QuickSpanRule quickSpanRule() default QuickSpanRule.NULL;

    /**
     * 字段类型（0：导出导入；1：仅导出；2：仅导入）
     */
    Type type() default Type.ALL;

    /**
     * 快速合并规则枚举
     */
    public enum QuickSpanRule {
        NULL(-1, "无规则"),
        IDENTICAL_ROW_MERGE(0, "相同行合并");

        private final int value;
        private final String desc;

        QuickSpanRule(int value, String desc) {
            this.value = value;
            this.desc = desc;
        }

        public int value() {
            return this.value;
        }
    }

    public enum Align {
        AUTO(0), LEFT(1), CENTER(2), RIGHT(3);
        private final int value;

        Align(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public enum Type {
        ALL(0), EXPORT(1), IMPORT(2);
        private final int value;

        Type(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }

    public enum ColumnType {
        NUMERIC(0), STRING(1), IMAGE(2);
        private final int value;

        ColumnType(int value) {
            this.value = value;
        }

        public int value() {
            return this.value;
        }
    }
}
