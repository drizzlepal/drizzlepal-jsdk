package com.drizzlepal.springboot3.webstarter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.ErrorResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver;

import com.alibaba.fastjson2.JSON;
import com.drizzlepal.rpc.RpcResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WebHandlerExceptionResolver extends DefaultHandlerExceptionResolver {

    @Override
    @NonNull
    protected ModelAndView handleErrorResponse(@NonNull ErrorResponse errorResponse,
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response, @Nullable Object handler) throws IOException {
        if (!response.isCommitted()) {
            // 复制 ErrorResponse HttpHeader
            HttpHeaders headers = errorResponse.getHeaders();
            headers.forEach((name, values) -> values.forEach(value -> response.addHeader(name, value)));
            // 复制 ErrorResponse HttpStatus
            int status = errorResponse.getStatusCode().value();
            response.setStatus(status);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.getWriter()
                    .write(JSON.toJSONString(RpcResponse.failed(errorResponse.getBody().getDetail())));
            response.flushBuffer();
        } else {
            log.warn("Ignoring exception, response committed. : " + errorResponse);
        }
        return new ModelAndView();
    }

}