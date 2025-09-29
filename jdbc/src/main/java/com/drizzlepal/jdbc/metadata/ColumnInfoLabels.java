package com.drizzlepal.jdbc.metadata;

public class ColumnInfoLabels {
    /*
     * 表所属的 catalog 名称
     * 类型：String
     * 在支持 catalog 的数据库中表示数据库名，如 MySQL 中的 schema。
     * 某些数据库（如 Oracle）可能不支持 catalog，此字段值可能为 null。
     */
    public static final String TABLE_CAT = "TABLE_CAT";

    /*
     * 表所属的 schema 名称
     * 类型：String
     * 在支持 schema 的数据库中表示命名空间或逻辑分组。
     * 在 PostgreSQL 中 schema 表示用户下的命名空间；在 Oracle 中为用户。
     */
    public static final String TABLE_SCHEM = "TABLE_SCHEM";

    /*
     * 表名
     * 类型：String
     * 表示当前列所属的数据库表名称。
     */
    public static final String TABLE_NAME = "TABLE_NAME";

    /*
     * 列名
     * 类型：String
     * 表示数据库表中的列字段名称。
     */
    public static final String COLUMN_NAME = "COLUMN_NAME";

    /*
     * SQL 类型代码
     * 类型：int
     * 与 java.sql.Types 中的常量一一对应，例如：VARCHAR = 12，INTEGER = 4。
     * 可用于程序中判断列的实际 SQL 数据类型。
     */
    public static final String DATA_TYPE = "DATA_TYPE";

    /*
     * 数据库特定的数据类型名称
     * 类型：String
     * 如 "VARCHAR", "INT", "DECIMAL" 等，通常用于展示。
     */
    public static final String TYPE_NAME = "TYPE_NAME";

    /*
     * 列大小
     * 类型：int
     * 对于字符类型表示最大字符数，对于数值类型表示数值精度。
     */
    public static final String COLUMN_SIZE = "COLUMN_SIZE";

    /*
     * 缓冲区长度
     * 类型：int
     * JDBC 保留字段，通常未使用，值为 null。
     */
    public static final String BUFFER_LENGTH = "BUFFER_LENGTH";

    /*
     * 小数位数
     * 类型：int
     * 仅适用于小数类（如 DECIMAL, NUMERIC），表示小数点右边的位数。
     */
    public static final String DECIMAL_DIGITS = "DECIMAL_DIGITS";

    /*
     * 精度基数
     * 类型：int
     * 通常为 10（十进制）或 2（二进制），用于指定精度单位。
     */
    public static final String NUM_PREC_RADIX = "NUM_PREC_RADIX";

    /*
     * 是否允许为 NULL
     * 类型：int
     * 值为：columnNoNulls（0）、columnNullable（1）、columnNullableUnknown（2）。
     */
    public static final String NULLABLE = "NULLABLE";

    /*
     * 列备注
     * 类型：String
     * 表示该列的注释信息，通常来源于数据库定义中的 COMMENT。
     */
    public static final String REMARKS = "REMARKS";

    /*
     * 列的默认值
     * 类型：String
     * 表示列在未赋值时默认填充的值，如 "0"、"CURRENT_TIMESTAMP"。
     */
    public static final String COLUMN_DEF = "COLUMN_DEF";

    /*
     * SQL 通用类型（保留字段）
     * 类型：int
     * 通常未使用，返回值为 0。
     */
    public static final String SQL_DATA_TYPE = "SQL_DATA_TYPE";

    /*
     * SQL 时间子类型
     * 类型：int
     * 用于描述 SQL 时间类字段的子类型，如 TIME、DATE、TIMESTAMP。
     */
    public static final String SQL_DATETIME_SUB = "SQL_DATETIME_SUB";

    /*
     * 字符类型最大字节长度
     * 类型：int
     * 仅适用于 CHAR、VARCHAR 类型，按字节长度计算（非字符数）。
     */
    public static final String CHAR_OCTET_LENGTH = "CHAR_OCTET_LENGTH";

    /*
     * 列在表中的位置（从 1 开始）
     * 类型：int
     * 表示列在表结构中定义的顺序位置。
     */
    public static final String ORDINAL_POSITION = "ORDINAL_POSITION";

    /*
     * 是否允许为 NULL（字符串形式）
     * 类型：String
     * 值为 "YES"、"NO" 或 ""（未知）。
     */
    public static final String IS_NULLABLE = "IS_NULLABLE";

    /*
     * REF 类型的 catalog 引用（仅在列为 REF 类型时使用）
     * 类型：String
     * 表示引用目标列所在的 catalog。
     */
    public static final String SCOPE_CATALOG = "SCOPE_CATALOG";

    /*
     * REF 类型的 schema 引用（仅在列为 REF 类型时使用）
     * 类型：String
     * 表示引用目标列所在的 schema。
     */
    public static final String SCOPE_SCHEMA = "SCOPE_SCHEMA";

    /*
     * REF 类型的表名引用（仅在列为 REF 类型时使用）
     * 类型：String
     * 表示引用目标列所在的表名。
     */
    public static final String SCOPE_TABLE = "SCOPE_TABLE";

    /*
     * 来源 SQL 类型（适用于 DISTINCT 类型）
     * 类型：int
     * 表示用户自定义类型（如 ENUM、DOMAIN）所基于的 SQL 类型。
     */
    public static final String SOURCE_DATA_TYPE = "SOURCE_DATA_TYPE";

    /*
     * 是否为自增列
     * 类型：String
     * 值为 "YES"、"NO" 或 ""（未知），用于标识该列是否为自动增长。
     */
    public static final String IS_AUTOINCREMENT = "IS_AUTOINCREMENT";

    /*
     * 是否为计算列
     * 类型：String
     * 值为 "YES"、"NO" 或 ""（未知），表示是否为由表达式生成的虚拟列。
     */
    public static final String IS_GENERATEDCOLUMN = "IS_GENERATEDCOLUMN";

}
