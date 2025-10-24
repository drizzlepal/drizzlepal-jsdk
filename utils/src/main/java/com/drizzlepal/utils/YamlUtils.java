package com.drizzlepal.utils;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import org.yaml.snakeyaml.Yaml;

import com.drizzlepal.utils.exception.FileInputstreamOperationException;
import com.drizzlepal.utils.exception.FileNotFoundException;
import com.drizzlepal.utils.exception.YamlUtilException;

public class YamlUtils {

    /**
     * 从指定资源路径加载并解析YAML文件为指定类型的对象
     * 
     * @param <T>   目标对象类型参数
     * @param clazz 需要反序列化的目标类型Class对象
     * @param paths YAML文件资源路径(支持多个路径参数，按参数顺序查找存在的文件)
     * @return 包含YAML文件数据的对象实例
     * @throws FileNotFoundException 当所有路径参数对应的文件均不存在时抛出
     * @throws IOException           当文件读取过程中发生IO异常时抛出
     * @throws YamlUtilException     当YAML解析过程出现异常时抛出
     * @throws URISyntaxException    当资源路径转换成URI对象时发生异常时抛出
     */
    public static <T> T load(Class<?> runTimeMainJarClass, Class<T> clazz, String... paths)
            throws FileNotFoundException, IOException, YamlUtilException, URISyntaxException {
        Yaml yaml = new Yaml();
        try {
            // 通过文件工具类获取资源输入流，并使用SnakeYAML进行反序列化
            return FileUtils.resourcesFileInputStreamFunction(runTimeMainJarClass, i -> {
                return yaml.loadAs(i, clazz);
            }, paths);
        } catch (FileInputstreamOperationException e) {
            // 转换文件操作异常为YAML工具异常
            throw new YamlUtilException(e);
        }
    }

    /**
     * 将指定对象序列化为YAML文件
     * 
     * @param object 需要序列化的对象
     * @param paths  YAML文件资源路径(支持多个路径参数，按参数顺序查找存在的文件)
     * @throws IOException           当文件写入过程中发生IO异常时抛出
     * @throws FileNotFoundException 当所有路径参数对应的文件均不存在时抛出
     * @throws URISyntaxException    当资源路径转换成URI对象时发生异常时抛出
     */
    public static void dump(Class<?> runTimeMainJarClass, Object object, String... paths)
            throws IOException, FileNotFoundException, URISyntaxException {
        Yaml yaml = new Yaml();
        File file = new File(FileUtils.resourcesFile(true, runTimeMainJarClass, paths).toURI());
        FileUtils.createFileIfNotExist(file, false);
        FileUtils.writeFile(file, yaml.dumpAsMap(object));
    }

}