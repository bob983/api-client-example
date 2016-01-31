package net.helpscout.mapper;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import java.util.Optional;

public class BiDirectionalResolver {

    private BiMap<Class<?>, Class<?>> map = HashBiMap.create();

    public Optional<Class<?>> resolveClass(Class<?> inputClass) {
        Class<?> result = map.get(inputClass);
        return Optional.ofNullable(result);
    }

    public void add(Class<?> a, Class<?> b) {
        map.put(a, b);
    }

}
