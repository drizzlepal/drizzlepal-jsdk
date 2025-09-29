package com.drizzlepal.rpc;

import java.lang.reflect.Type;

import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.annotation.JSONType;
import com.alibaba.fastjson2.reader.ObjectReader;
import com.alibaba.fastjson2.writer.ObjectWriter;

@JSONType(deserializer = RpcStatusCommon.Deserializer.class, serializer = RpcStatusCommon.Serializer.class)
public enum RpcStatusCommon implements RpcStatus {

    SUCCESS("成功"),
    SERVICE_NOT_FOUND("服务未找到"),
    METHOD_NOT_FOUND("方法未找到"),
    PARAM_INVALID("参数无效"),
    PERMISSION_DENIED("权限不足"),
    TIMEOUT("请求超时"),
    INTERNAL_ERROR("内部错误"),
    UNAUTHORIZED("未授权"),
    BAD_REQUEST("错误请求"),
    NOT_FOUND("未找到"),
    UNSUPPORTED_MEDIA_TYPE("不支持的媒体类型"),
    TOO_MANY_REQUESTS("请求过多"),
    GATEWAY_TIMEOUT("网关超时"),
    SERVICE_UNAVAILABLE("服务不可用"),
    ALREADY_EXISTS("资源已存在"),
    UNKNOWN_ERROR("未归档异常,请联系技术支持");

    private String message;

    RpcStatusCommon(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
    }

    @Override
    public void setCode(String code) {
    }

    @Override
    public String toString() {
        return name();
    }

    public static class Deserializer implements ObjectReader<RpcStatusCommon> {

        @Override
        public RpcStatusCommon readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
            String code = jsonReader.readString();
            for (RpcStatusCommon status : values()) {
                if (status.name() == code) {
                    return status;
                }
            }
            return null;
        }
    }

    public static class Serializer implements ObjectWriter<RpcStatusCommon> {

        @Override
        public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
            jsonWriter.writeString(object.toString());
        }

    }
}
