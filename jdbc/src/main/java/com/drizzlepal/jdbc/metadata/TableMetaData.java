package com.drizzlepal.jdbc.metadata;

import java.util.ArrayList;
import java.util.Map;

import lombok.Data;

@Data
public class TableMetaData {

    private String database;

    private String name;

    private String remarks;

    private ArrayList<ColumnMetaData> columns;

    private ArrayList<PrimaryKeyMetaData> primaryKeys;

    private Map<String, ArrayList<IndexMetaData>> indexes;

}
