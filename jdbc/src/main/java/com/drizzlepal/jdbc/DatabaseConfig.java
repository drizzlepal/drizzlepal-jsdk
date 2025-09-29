package com.drizzlepal.jdbc;

import com.drizzlepal.jdbc.exception.ConfigInvalidException;

/**
 * 数据库可配置的配置信息相较于数据库类型驱动类名和探活sql
 */
public interface DatabaseConfig {

    void isValid() throws ConfigInvalidException;

}
