package com.drizzlepal.jdbc.utils;

import java.sql.Types;
import java.util.ArrayList;

import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.drizzlepal.jdbc.config.MetaDataUtilsConfig;
import com.drizzlepal.jdbc.metadata.ColumnMetaData;

public class MetaDataUtils {

    public static ArrayList<ColumnMetaData> jsonStringAsColumnMetaDatas(String jsonString, MetaDataUtilsConfig config) {
        JSONObject object = (JSONObject) JSONPath.eval(jsonString, config.getRowJsonPath());
        Object[] keys = object.keySet().toArray();
        ArrayList<ColumnMetaData> result = new ArrayList<>(keys.length);
        for (int i = 0; i < keys.length;) {
            String key = (String) keys[i];
            ColumnMetaData columnMetaData = new ColumnMetaData();
            Object value = object.get(key);
            columnMetaData.setName(key);
            columnMetaData.setDatatype(0);
            columnMetaData.setLength(null);
            if (value == null) {
                columnMetaData.setNullable(1);
                columnMetaData.setDefaultValue(null);
                columnMetaData.setTypeName(null);
            } else {
                columnMetaData.setNullable(2);
                columnMetaData.setDefaultValue(value.toString());
                columnMetaData.setTypeName(value.getClass().getName());
            }
            columnMetaData.setOrdinalPosition(++i);
            columnMetaData.setDecimalDigits(null);
            columnMetaData.setRemarks("");
            result.add(columnMetaData);
        }
        return result;
    }

    public static Class<?> mapSqlTypeToJavaClass(int sqlType) {
        switch (sqlType) {
            case Types.BIT:
            case Types.BOOLEAN:
                return Boolean.class;

            case Types.TINYINT:
                return Byte.class;

            case Types.SMALLINT:
                return Short.class;

            case Types.INTEGER:
                return Integer.class;

            case Types.BIGINT:
                return Long.class;

            case Types.REAL:
            case Types.FLOAT:
                return Float.class;

            case Types.DOUBLE:
                return Double.class;

            case Types.NUMERIC:
            case Types.DECIMAL:
                return java.math.BigDecimal.class;

            case Types.CHAR:
            case Types.VARCHAR:
            case Types.LONGVARCHAR:
            case Types.NCHAR:
            case Types.NVARCHAR:
            case Types.LONGNVARCHAR:
                return String.class;

            case Types.DATE:
                return java.sql.Date.class;

            case Types.TIME:
                return java.sql.Time.class;

            case Types.TIMESTAMP:
                return java.sql.Timestamp.class;

            case Types.TIME_WITH_TIMEZONE:
                return java.time.OffsetTime.class;

            case Types.TIMESTAMP_WITH_TIMEZONE:
                return java.time.OffsetDateTime.class;

            case Types.BINARY:
            case Types.VARBINARY:
            case Types.LONGVARBINARY:
                return byte[].class;

            case Types.NULL:
                return Object.class;

            case Types.OTHER:
            case Types.JAVA_OBJECT:
            case Types.DISTINCT:
                return Object.class;

            case Types.STRUCT:
                return java.sql.Struct.class;

            case Types.ARRAY:
                return java.sql.Array.class;

            case Types.BLOB:
                return java.sql.Blob.class;

            case Types.CLOB:
                return java.sql.Clob.class;

            case Types.NCLOB:
                return java.sql.NClob.class;

            case Types.REF:
            case Types.REF_CURSOR:
                return java.sql.Ref.class;

            case Types.ROWID:
                return java.sql.RowId.class;

            case Types.SQLXML:
                return java.sql.SQLXML.class;

            case Types.DATALINK:
                return java.net.URL.class;

            default:
                return Object.class;
        }
    }

}
