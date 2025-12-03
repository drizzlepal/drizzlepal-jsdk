package com.drizzlepal.jdbc.database.mysql;

import java.util.HashMap;

import com.drizzlepal.jdbc.DefaultDatabaseConfig;
import com.drizzlepal.jdbc.DatabaseType;

public class DatabaseConfigMysql extends DefaultDatabaseConfig {

    public DatabaseConfigMysql() {
        super(DatabaseType.MYSQL, new HashMap<String, String>() {
            {
                put("useUnicode", "true");
                put("characterEncoding", "utf8");
                put("zeroDateTimeBehavior", "convertToNull");
                put("transformedBitIsBoolean", "true");
                put("allowMultiQueries", "true");
            }
        });
    }

}
