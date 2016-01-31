package net.helpscout.converter;

import lombok.extern.slf4j.Slf4j;
import net.helpscout.mapper.BiDirectionalResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
@Slf4j
public class RequestBodyConfiguration extends WebMvcConfigurerAdapter {

    @Autowired
    RequestBodyConverter requestBodyConverter;

    @Autowired
    BiDirectionalResolver biDirectionalResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(requestBodyConverter);
    }

}
