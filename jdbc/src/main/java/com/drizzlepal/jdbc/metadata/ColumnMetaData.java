package com.drizzlepal.jdbc.metadata;

import lombok.Data;

@Data
public class ColumnMetaData {

    /*
     * 列名
     * 类型：String
     * 表示数据库表中的列字段名称。
     */
    private String name;

    /*
     * 列备注
     * 类型：String
     * 表示该列的注释信息，通常来源于数据库定义中的 COMMENT。
     */
    private String remarks;

    /*
     * SQL 类型代码
     * 类型：int
     * 与 java.sql.Types 中的常量一一对应，例如：VARCHAR = 12，INTEGER = 4。
     * 可用于程序中判断列的实际 SQL 数据类型。
     */
    private int datatype;

    /*
     * 数据库特定的数据类型名称
     * 类型：String
     * 如 "VARCHAR", "INT", "DECIMAL" 等，通常用于展示。
     */
    private String typeName;

    /*
     * 列大小
     * 类型：int
     * 对于字符类型表示最大字符数，对于数值类型表示数值精度。
     */
    private Integer length;

    /*
     * 列在表中的位置（从 1 开始）
     * 类型：int
     * 表示列在表结构中定义的顺序位置。
     */
    private Integer ordinalPosition;

    /*
     * 是否允许为 NULL
     * 类型：int
     * 值为：columnNoNulls（0）、columnNullable（1）、columnNullableUnknown（2）。
     */
    private Integer nullable;

    /*
     * 小数位数
     * 类型：int
     * 仅适用于小数类（如 DECIMAL, NUMERIC），表示小数点右边的位数。
     */
    private Integer decimalDigits;

    /*
     * 列的默认值
     * 类型：String
     * 表示列在未赋值时默认填充的值，如 "0"、"CURRENT_TIMESTAMP"。
     */
    private String defaultValue;

}
