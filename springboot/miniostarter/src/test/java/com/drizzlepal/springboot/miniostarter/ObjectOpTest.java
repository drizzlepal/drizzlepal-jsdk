package com.drizzlepal.springboot.miniostarter;

import java.io.InputStream;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.drizzlepal.springboot.miniostarter.exception.DrizzlepalMinioObjectOpException;
import com.drizzlepal.utils.FileUtils;

@SpringBootTest
@ContextConfiguration(classes = MinioStarterConfig.class)
public class ObjectOpTest {

    @Resource
    private MinioTemplate minioTemplate;

    @Test
    public void testPutObject() throws Exception {
        FileUtils.resourcesFileInputStreamConsumer(sc -> {
            minioTemplate.putObject("test", "test", sc);
        }, "/home/songyu/Desktop/clickhouse-jdbc-0.3.2-patch10-all.jar");
    }

    @Test
    public void testGetObject() throws Exception {
        InputStream object = minioTemplate.getObject("test", "test");
        FileUtils.saveInputStreamToFile(object, "/home/songyu/Desktop/test.jar");
    }

    @Test
    public void testRemoveObject() throws DrizzlepalMinioObjectOpException {
        minioTemplate.removeObject("test", "test");
    }

    @Test
    public void removeBucket() throws DrizzlepalMinioObjectOpException {
        minioTemplate.removeBucket("test");
    }

}
