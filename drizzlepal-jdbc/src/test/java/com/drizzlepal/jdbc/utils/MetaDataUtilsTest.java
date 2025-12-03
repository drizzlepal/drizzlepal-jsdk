package com.drizzlepal.jdbc.utils;

import java.io.IOException;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Test;

import com.alibaba.fastjson2.JSON;
import com.drizzlepal.jdbc.config.MetaDataUtilsConfig;
import com.drizzlepal.utils.FileUtils;
import com.drizzlepal.utils.exception.FileNotFoundException;

public class MetaDataUtilsTest {

    @Test
    public void test() throws FileNotFoundException, IOException, URISyntaxException {
        String jsonString = FileUtils.resourcesFileContent(MetaDataUtilsTest.class, "test.json");
        MetaDataUtilsConfig config = JSON.parseObject(
                FileUtils.resourcesFileContent(MetaDataUtilsTest.class, "test-config.json"),
                MetaDataUtilsConfig.class);
        MetaDataUtils.jsonStringAsColumnMetaDatas(jsonString, config).forEach(column -> {
            System.out.println(column);
        });
    }

}
