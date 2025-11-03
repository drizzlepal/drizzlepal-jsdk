package com.drizzlepal.jdbc.database.kingbase.mode.mysql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionKingBaseMysqlMode {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.kingbase8.Driver");
        try (Connection connection = DriverManager.getConnection("jdbc:kingbase8://127.0.0.1:54321/test",
                "system",
                "123456")) {
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getDatabaseProductName());
            System.out.println(metaData.getDatabaseProductVersion());
        }
    }

}
