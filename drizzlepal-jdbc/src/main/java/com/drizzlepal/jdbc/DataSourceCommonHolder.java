package com.drizzlepal.jdbc;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

import com.drizzlepal.jdbc.exception.JdbcException;

public class DataSourceCommonHolder {

    private static final ConcurrentHashMap<String, DataSource> DataSourceMap = new ConcurrentHashMap<>();

    public static DataSource getDataSource(String key, DatabaseType databaseType,
            Consumer<DataSourceBuilder> builderHandler) {
        return DataSourceMap.computeIfAbsent(key, k -> {
            DataSourceBuilder builder = DataSourceBuilder.builder(databaseType);
            builderHandler.accept(builder);
            try {
                return builder.build();
            } catch (JdbcException e) {
                throw new RuntimeException(e);
            }
        });
    }

}
