package com.drizzlepal.jdbc;

import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter.Feature;
import com.drizzlepal.jdbc.exception.JdbcException;
import com.drizzlepal.jdbc.metadata.ColumnMetaData;

public class DatasourceTest {

    @Test
    public void testGetDatasource() throws JdbcException, SQLException {
        DataSource datasource = DataSourceBuilder.builder(DatabaseType.MYSQL).host("127.0.0.1").port(3306)
                .userName("root").password("KJGBKAHJSKDAHBS@$@Qadf@$!@wf(*&").database("drptest").build();
        ArrayList<ColumnMetaData> columnMetaDataFormSql = datasource.getColumnMetaDataFormSql(
                "SELECT current_tasks FROM drptest.lab_management;");
        System.out.println(JSON.toJSONString(columnMetaDataFormSql, Feature.PrettyFormat));
        ArrayList<ColumnMetaData> columnMetaDataFormSql2 = datasource.getColumnMetaDataFormSql(
                "SELECT concat(current_tasks,'') as current_tasks FROM drptest.lab_management;");
        System.out.println(JSON.toJSONString(columnMetaDataFormSql2, Feature.PrettyFormat));
    }

}
