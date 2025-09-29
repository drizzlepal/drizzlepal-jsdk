package com.drizzlepal.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.channels.Channels;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.drizzlepal.utils.exception.FileInputstreamOperationException;
import com.drizzlepal.utils.exception.FileNotFoundException;
import com.drizzlepal.utils.functions.ConsumerThrowable;
import com.drizzlepal.utils.functions.FunctionThrowable;

/**
 * 文件操作工具类，提供文件流处理、资源加载、文件内容读取等功能
 */
public class FileUtils {

    /**
     * 将输入流内容保存到指定路径的文件
     * 
     * @param inputStream  需要保存的输入流对象
     * @param absolutePath 目标文件的绝对路径
     */
    public static void saveInputStreamToFile(InputStream inputStream, String absolutePath) {
        Path path = Paths.get(absolutePath);
        try {
            Files.copy(inputStream, path);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除指定文件（如果存在）
     * 
     * @param file 需要删除的文件对象
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 加载文件资源为输入流，支持从JAR包所在目录或类路径加载
     * 
     * @param paths 可变参数，文件路径组成部分
     * @return 文件输入流
     * @throws FileNotFoundException 当路径为空或文件不存在时抛出
     * 
     *                               加载策略：
     *                               1. 优先从JAR文件同级目录查找
     *                               2. 找不到时从类路径资源加载
     */
    private static InputStream loadFile(String... paths) throws FileNotFoundException {
        if (paths == null || paths.length == 0) {
            throw new FileNotFoundException("文件路径不可为空");
        }
        StringJoiner joiner = new StringJoiner(File.separator);
        for (String path : paths) {
            joiner.add(path);
        }
        String filename = joiner.toString();
        try {
            Path jarDir = Paths.get(FileUtils.class.getProtectionDomain()
                    .getCodeSource().getLocation().toURI()).getParent();

            Path filePath = jarDir.resolve(filename);
            if (Files.exists(filePath)) {
                return new FileInputStream(filePath.toFile());
            }
        } catch (URISyntaxException | IOException e) {
            e.printStackTrace();
        }
        InputStream classpathStream = FileUtils.class.getClassLoader().getResourceAsStream(filename);
        return classpathStream;
    }

    /**
     * 对指定文件进行流式消费操作
     * 
     * @param streamConsumer 可抛出异常的输入流消费者
     * @param absolutePath   目标文件的绝对路径
     * @throws Throwable 执行过程中可能抛出的异常
     * 
     *                   资源管理：
     *                   - 使用try-with-resources自动关闭字节通道和输入流
     */
    public static void fileInputStreamConsumer(ConsumerThrowable<InputStream> streamConsumer,
            String absolutePath) throws Throwable {
        Path path = Paths.get(absolutePath);
        try (
                SeekableByteChannel sbc = Files.newByteChannel(path);
                InputStream in = Channels.newInputStream(sbc);) {
            streamConsumer.accept(in);
        }
    }

    /**
     * 对指定文件进行流式转换操作
     * 
     * @param functionThrowable 可抛出异常的输入流转换函数
     * @param absolutePath      目标文件的绝对路径
     * @return 转换函数返回的结果
     * @throws IOException                       文件操作异常
     * @throws FileInputstreamOperationException 流处理异常
     * 
     *                                           异常处理：
     *                                           -
     *                                           将底层Throwable包装为FileInputstreamOperationException
     */
    public static <T> T fileInputStreamFunction(FunctionThrowable<InputStream, T> functionThrowable,
            String absolutePath) throws IOException, FileInputstreamOperationException {
        Path path = Paths.get(absolutePath);
        try (
                SeekableByteChannel sbc = Files.newByteChannel(path);
                InputStream in = Channels.newInputStream(sbc);) {
            try {
                return functionThrowable.apply(in);
            } catch (Throwable e) {
                throw new FileInputstreamOperationException(e);
            }
        }
    }

    /**
     * 对资源文件进行流式消费操作
     * 
     * @param streamConsumer 可抛出异常的输入流消费者
     * @param paths          资源文件路径组成部分（自动拼接）
     * @throws FileInputstreamOperationException 流处理异常
     * @throws FileNotFoundException             文件未找到异常
     * @throws IOException                       文件操作异常
     */
    public static void resourcesFileInputStreamConsumer(ConsumerThrowable<InputStream> streamConsumer,
            String... paths) throws FileInputstreamOperationException, FileNotFoundException, IOException {
        try (InputStream inputStream = loadFile(paths);) {
            try {
                streamConsumer.accept(inputStream);
            } catch (Throwable e) {
                throw new FileInputstreamOperationException(e);
            }
        }
    }

    /**
     * 对资源文件进行流式转换操作
     * 
     * @param functionThrowable 可抛出异常的输入流转换函数
     * @param paths             资源文件路径组成部分（自动拼接）
     * @return 转换函数返回的结果
     * @throws FileInputstreamOperationException 流处理异常
     * @throws FileNotFoundException             文件未找到异常
     * @throws IOException                       文件操作异常
     */
    public static <T> T resourcesFileInputStreamFunction(FunctionThrowable<InputStream, T> functionThrowable,
            String... paths) throws FileInputstreamOperationException, FileNotFoundException, IOException {
        try (InputStream inputStream = loadFile(paths);) {
            try {
                return functionThrowable.apply(inputStream);
            } catch (Throwable e) {
                throw new FileInputstreamOperationException(e);
            }
        }
    }

    /**
     * 读取资源文件内容为字符串
     * 
     * @param paths 资源文件路径组成部分（自动拼接）
     * @return 文件内容字符串（行间用换行符连接）
     * @throws FileNotFoundException 文件未找到异常
     * @throws IOException           文件操作异常
     */
    public static String resourcesFileContent(String... paths) throws FileNotFoundException, IOException {
        try (InputStream inpustream = loadFile(paths);) {
            return new BufferedReader(new InputStreamReader(inpustream)).lines().collect(Collectors.joining("\n"));
        }
    }

    /**
     * 逐行处理资源文件内容
     * 
     * @param consumerThrowable 可抛出异常的流消费者
     * @param paths             资源文件路径组成部分（自动拼接）
     * @throws FileNotFoundException             文件未找到异常
     * @throws IOException                       文件操作异常
     * @throws FileInputstreamOperationException 流处理异常
     * 
     *                                           注意事项：
     *                                           - 流对象由方法内部管理，调用方无需关闭
     */
    public static void resourcesFileContentLineByLine(ConsumerThrowable<Stream<String>> consumerThrowable,
            String... paths)
            throws FileNotFoundException, IOException, FileInputstreamOperationException {
        try (InputStream inpustream = loadFile(paths);) {
            Stream<String> lines = new BufferedReader(new InputStreamReader(inpustream)).lines();
            try {
                consumerThrowable.accept(lines);
            } catch (Throwable e) {
                throw new FileInputstreamOperationException(e);
            }
        }
    }

    /**
     * 将文件名中可能为路径符号的字符去除
     * 
     * @param fileNamePathWith 可能有路径分隔符号的文件名
     * @return 去除后的结果
     */
    public static String makeValidFileName(String fileNamePathWith) {
        return fileNamePathWith.replaceAll(File.separator, "");
    }

    /**
     * 获取指定类所在JAR包的目录
     * 
     * @param baseClass 基准类，用于定位其所在的JAR包
     * @return JAR包所在的目录
     * @throws FileNotFoundException 当无法获取JAR包路径时抛出此异常
     */
    public static File jarDir(Class<?> baseClass) throws FileNotFoundException {
        try {
            // 获取类所在位置的路径
            String path = baseClass.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            File jarFile = new File(path);
            return jarFile.getParentFile();
        } catch (Exception e) {
            throw new FileNotFoundException("无法获取 JAR 包路径", e);
        }
    }

}