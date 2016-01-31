package net.helpscout.mapper;

import lombok.val;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrikaMapperConfiguration {

    @Bean
    public BiDirectionalResolver biDirectionalResolver() {
        val resolver = new BiDirectionalResolver();

        resolver.add(net.helpscout.core.Conversation.class, net.helpscout.outside.Conversation.class);

        return resolver;
    }

    @Bean
    public MapperFacade mapperFacade() {
        val mapperFactory = new DefaultMapperFactory.Builder().build();

        mapperFactory.classMap(net.helpscout.core.Conversation.class, net.helpscout.outside.Conversation.class)
                .field("coreId", "id")
                .register();

        mapperFactory.classMap(net.helpscout.outside.Conversation.class, net.helpscout.core.Conversation.class)
                .field("id", "coreId")
                .register();

        return mapperFactory.getMapperFacade();
    }

}
