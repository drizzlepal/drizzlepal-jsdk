package com.drizzlepal.jdbc;

import java.util.Map;

import com.drizzlepal.jdbc.exception.ConfigInvalidException;
import com.drizzlepal.utils.StringUtils;

import lombok.Data;

@Data
public abstract class DefaultDatabaseConfig implements DatabaseConfig {

    private final DatabaseType databaseType;

    private String host;

    private Integer port;

    private String username;

    private String password;

    private String schema;

    private String database;

    private int maxActive = 8;

    private int minIdle = 0;

    private long maxWaitMs = 10000;

    private final Map<String, String> connectionParams;

    @Override
    public void isValid() throws ConfigInvalidException {
        if (StringUtils.isBlank(host)) {
            throw new ConfigInvalidException("host is blank");
        }
        if (port <= 0) {
            throw new ConfigInvalidException("port is invalid");
        }
        if (StringUtils.isBlank(username)) {
            throw new ConfigInvalidException("username is blank");
        }
        if (StringUtils.isBlank(password)) {
            throw new ConfigInvalidException("password is blank");
        }
    }

}
