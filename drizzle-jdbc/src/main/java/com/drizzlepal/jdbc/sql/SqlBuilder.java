package com.drizzlepal.jdbc.sql;

import com.drizzlepal.jdbc.metadata.TableMetaData;

public interface SqlBuilder {

    String CreateTable(TableMetaData tableMetaData);

    String Insert();

    String Delete();

    String Update();

    String Select();

    String TruncateTable();

}
