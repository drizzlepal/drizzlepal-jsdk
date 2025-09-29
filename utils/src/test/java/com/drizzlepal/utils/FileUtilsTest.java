package com.drizzlepal.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class FileUtilsTest {
    @Test
    public void testGetClassPathOutputStream() throws IOException {
        String validFileName = FileUtils.makeValidFileName("门诊处方/医嘱");
        assertEquals("门诊处方医嘱", validFileName);
    }

}
