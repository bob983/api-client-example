package net.helpscout.mapper;

import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrikaMapperAdapter implements Mapper {

    private final MapperFacade mapperFacade;

    @Autowired
    public OrikaMapperAdapter(MapperFacade mapperFacade) {
        this.mapperFacade = mapperFacade;
    }

    @Override
    public <A, B> B map(A entity, Class<B> dtoClass) {
        return mapperFacade.map(entity, dtoClass);
    }

    @Override
    public <A, B> List<B> map(List<A> entities, Class<B> dtoClass) {
        return mapperFacade.mapAsList(entities, dtoClass);
    }

}

