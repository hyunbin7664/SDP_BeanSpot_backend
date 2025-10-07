package com.beanspot.backend.common.response;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 컨트롤러가 ApiResponse를 리턴하면, 그 안의 httpStatus를 실제 HTTP 상태코드로 반영.
 */
@RestControllerAdvice
public class ResponseInterceptor implements ResponseBodyAdvice<Object> {
    @Override public boolean supports(MethodParameter returnType, Class converterType) { return true; }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType contentType,
                                  Class converterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse<?> api) {
            response.setStatusCode(api.httpStatus());
        }
        return body;
    }
}
