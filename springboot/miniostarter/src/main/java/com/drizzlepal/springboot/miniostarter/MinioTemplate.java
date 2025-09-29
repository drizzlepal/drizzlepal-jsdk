package com.drizzlepal.springboot.miniostarter;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import com.drizzlepal.springboot.miniostarter.exception.DrizzlepalMinioObjectOpException;
import com.drizzlepal.springboot.miniostarter.props.DrizzlepalMinioStarterProps;

import io.minio.BucketExistsArgs;
import io.minio.GetObjectArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveBucketArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.ServerException;
import io.minio.errors.XmlParserException;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;

/**
 * Minio 操作模板类，封装了与 Minio 服务器交互的基本操作
 */
public class MinioTemplate {

    /**
     * Minio 客户端实例，用于执行与 Minio 服务器的通信
     */
    private final MinioClient minioClient;

    /**
     * 构造函数，初始化 MinioClient
     *
     * @param props Minio 连接属性，包含访问密钥、秘密密钥和端点信息
     */
    public MinioTemplate(DrizzlepalMinioStarterProps props) {
        // 自定义 OkHttpClient 连接池
        OkHttpClient customHttpClient = new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(10, 5, TimeUnit.MINUTES)) // 最大连接数 10，闲置连接存活 5 分钟
                .readTimeout(30, TimeUnit.SECONDS) // 读取超时时间
                .writeTimeout(30, TimeUnit.SECONDS) // 写入超时时间
                .connectTimeout(10, TimeUnit.SECONDS) // 连接超时时间
                .build();
        // 构建 MinioClient
        minioClient = MinioClient.builder()
                .endpoint(props.getEndpoint())
                .credentials(props.getAccessKey(), props.getSecretKey())
                .httpClient(customHttpClient) // 使用自定义 OkHttpClient
                .build();
    }

    /**
     * 确保指定的桶存在，如果不存在则创建
     *
     * @param bucketName 桶名称
     * @throws DrizzlepalMinioObjectOpException 如果操作失败
     */
    public void makeSureBucketExists(String bucketName) throws DrizzlepalMinioObjectOpException {
        try {
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            throw new DrizzlepalMinioObjectOpException("make sure bucket exists error", e);
        }
    }

    /**
     * 将对象放入指定的桶中
     *
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @param stream     对象数据流
     * @throws DrizzlepalMinioObjectOpException 如果操作失败
     */
    public void putObject(String bucketName, String objectName, InputStream stream)
            throws DrizzlepalMinioObjectOpException {
        try {
            makeSureBucketExists(bucketName);
            minioClient.putObject(
                    PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(stream, -1, 10485760)
                            .build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            throw new DrizzlepalMinioObjectOpException("put object error", e);
        }
    }

    /**
     * 获取指定桶中的对象
     *
     * @param bucketName 桶名称
     * @param objectName 对象名称
     * @return 对象的数据流
     * @throws DrizzlepalMinioObjectOpException 如果操作失败
     */
    public InputStream getObject(String bucketName, String objectName) throws DrizzlepalMinioObjectOpException {
        try {
            return minioClient.getObject(GetObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            throw new DrizzlepalMinioObjectOpException("get object error", e);
        }
    }

    /**
     * 删除指定存储桶中的对象
     * 
     * @param bucketName 存储桶名称
     * @param objectName 对象名称
     * @throws DrizzlepalMinioObjectOpException 如果操作MinIO对象时发生错误
     */
    public void removeObject(String bucketName, String objectName) throws DrizzlepalMinioObjectOpException {
        try {
            // 执行删除对象的操作
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            // 异常处理：重新抛出异常，由调用者处理
            throw new DrizzlepalMinioObjectOpException("Failed to remove object", e);
        }
    }

    /**
     * 删除指定的存储桶
     * 
     * @param bucketName 存储桶名称
     * @throws DrizzlepalMinioObjectOpException 如果操作MinIO对象时发生错误
     */
    public void removeBucket(String bucketName) throws DrizzlepalMinioObjectOpException {
        try {
            // 执行删除存储桶的操作
            minioClient.removeBucket(RemoveBucketArgs.builder().bucket(bucketName).build());
        } catch (InvalidKeyException | ErrorResponseException | InsufficientDataException | InternalException
                | InvalidResponseException | NoSuchAlgorithmException | ServerException | XmlParserException
                | IllegalArgumentException | IOException e) {
            // 异常处理：重新抛出异常，由调用者处理
            throw new DrizzlepalMinioObjectOpException("Failed to remove bucket", e);
        }
    }

}