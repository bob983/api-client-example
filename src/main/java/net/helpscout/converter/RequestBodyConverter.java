package net.helpscout.converter;

import lombok.extern.slf4j.Slf4j;
import net.helpscout.mapper.BiDirectionalResolver;
import net.helpscout.mapper.Mapper;
import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Service
@Slf4j
public class RequestBodyConverter implements HandlerMethodArgumentResolver {

    private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";

    private final BiDirectionalResolver biDirectionalResolver;
    private final Mapper mapper;

    @Autowired
    public RequestBodyConverter(BiDirectionalResolver biDirectionalResolver, Mapper mapper) {
        this.biDirectionalResolver = biDirectionalResolver;
        this.mapper = mapper;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        return methodParameter.hasParameterAnnotation(ConvertedRequestBody.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        Class<?> destinationClass = methodParameter.getParameterType();
        Class<?> sourceClass = biDirectionalResolver
                .resolveClass(destinationClass)
                .orElseThrow(() -> new RuntimeException("Not a convertible class " + destinationClass));
        String json = getRequestBody(nativeWebRequest);
        Object sourceObject = new ObjectMapper().readValue(json, sourceClass);
        Object destinationObject = mapper.map(sourceObject, destinationClass);
        return destinationObject;
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
        String jsonBody = (String) servletRequest.getAttribute(JSONBODYATTRIBUTE);
        if (jsonBody == null) {
            try {
                String body = IOUtils.toString(servletRequest.getInputStream());
                servletRequest.setAttribute(JSONBODYATTRIBUTE, body);
                return body;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";

    }

}
