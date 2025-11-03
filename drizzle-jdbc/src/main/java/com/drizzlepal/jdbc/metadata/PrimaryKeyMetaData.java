package com.drizzlepal.jdbc.metadata;

import lombok.Data;

@Data
public class PrimaryKeyMetaData {

    /**
     * 列名
     */
    private String columnName;

    /**
     * 列序号
     */
    private int keySeq;

    /**
     * 主键名称
     */
    private String pkName;

}
