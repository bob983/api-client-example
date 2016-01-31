package net.helpscout.mapper;

import java.util.List;

public interface Mapper {

    <A, B> B map(A entity, Class<B> dtoClass);

    <A, B> List<B> map(List<A> entities, Class<B> dtoClass);

}