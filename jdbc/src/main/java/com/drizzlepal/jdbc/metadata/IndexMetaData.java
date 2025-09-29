package com.drizzlepal.jdbc.metadata;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndexMetaData {

    private String indexName;

    private Boolean nonUnique;

    private String columnName;

    private Integer ordinalPosition;

    private String ascOrDesc;

    private String type;

}
