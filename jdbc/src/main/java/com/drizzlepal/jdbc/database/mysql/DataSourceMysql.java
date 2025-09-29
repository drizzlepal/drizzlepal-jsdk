package com.drizzlepal.jdbc.database.mysql;

import com.drizzlepal.jdbc.DefaultDatabaseConfig;
import com.drizzlepal.jdbc.DefaultDataSource;
import com.drizzlepal.utils.StringUtils;

public class DataSourceMysql extends DefaultDataSource {

    public DataSourceMysql(DefaultDatabaseConfig configCommon) {
        super(configCommon);
    }

    @Override
    public String buildJdbcUrl(DefaultDatabaseConfig configCommon) {
        StringBuilder stringBuilder = new StringBuilder("jdbc:mysql://");
        stringBuilder.append(configCommon.getHost());
        stringBuilder.append(":");
        stringBuilder.append(configCommon.getPort());
        if (StringUtils.isNotBlank(configCommon.getDatabase())) {
            stringBuilder.append("/");
            stringBuilder.append(configCommon.getDatabase());
        }
        stringBuilder.append("?");
        configCommon.getConnectionParams().forEach((k, v) -> stringBuilder.append("&").append(k).append("=").append(v));
        return stringBuilder.toString();
    }

    @Override
    protected boolean checkDatabaseNameConfigExists() {
        return configCommon.getDatabase() != null && !configCommon.getDatabase().isEmpty();
    }

}
