package com.drizzlepal.jdbc.metadata;

import java.util.ArrayList;

import lombok.Data;

/**
 * 数据库元数据信息
 */
@Data
public class DatabaseMetaData {

    private String name;

    private String schema;

    private ArrayList<TableMetaData> tables;

}
