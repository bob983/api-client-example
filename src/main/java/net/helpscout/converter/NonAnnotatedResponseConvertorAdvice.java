package net.helpscout.converter;

import lombok.extern.slf4j.Slf4j;
import net.helpscout.mapper.BiDirectionalResolver;
import net.helpscout.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.List;

@Slf4j
@ControllerAdvice
public class NonAnnotatedResponseConvertorAdvice implements ResponseBodyAdvice<Object> {

    private final BiDirectionalResolver biDirectionalResolver;
    private final Mapper mapper;

    @Autowired
    public NonAnnotatedResponseConvertorAdvice(BiDirectionalResolver biDirectionalResolver, Mapper mapper) {
        this.biDirectionalResolver = biDirectionalResolver;
        this.mapper = mapper;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object response, MethodParameter methodParameter, MediaType mediaType, Class<? extends HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse) {
        if (response instanceof List) {
            List<?> collection = (List<?>) response;
            Class<?> sourceClass = methodParameter.getMethodAnnotation(MappedType.class).value();
            Class<?> destinationClass = getDestinationClass(sourceClass);
            return mapper.map(collection, destinationClass);
        } else {
            Class<?> sourceClass = response.getClass();
            Class<?> destinationClass = getDestinationClass(sourceClass);
            return mapper.map(response, destinationClass);
        }
    }

    private Class<?> getDestinationClass(Class<?> sourceClass) {
        return biDirectionalResolver
                .resolveClass(sourceClass)
                .orElse(sourceClass);
    }
}
