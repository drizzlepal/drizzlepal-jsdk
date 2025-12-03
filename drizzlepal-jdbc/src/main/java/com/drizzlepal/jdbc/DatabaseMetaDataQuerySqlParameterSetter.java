package com.drizzlepal.jdbc;

import java.sql.Statement;

@FunctionalInterface
public interface DatabaseMetaDataQuerySqlParameterSetter {

    void setParameters(Statement statement, String schema, String database);

}
