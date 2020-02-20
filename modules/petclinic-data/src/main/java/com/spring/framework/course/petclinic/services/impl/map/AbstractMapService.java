package com.spring.framework.course.petclinic.services.impl.map;

import com.spring.framework.course.petclinic.model.BaseEntity;

import java.util.*;

public abstract class AbstractMapService<T extends BaseEntity, ID extends Long> {

    protected Map<Long, T> map = new HashMap<>();

    Set<T> findAll(){
        return new HashSet<>(map.values());
    }

    T findById(ID id) {
        return map.get(id);
    }

    T save(T object){

        if(object != null){
            if(object.getId() == null) {
                object.setId(getNextId());
            }
            map.put(object.getId(), object);
            return object;
        }
        throw new IllegalArgumentException("Object cannot be null");
    }

    void deleteById(ID id){
        map.remove(id);
    }

    void delete(T object) {
        map.entrySet().removeIf(entry -> entry.getValue().equals(object));
    }

    private Long getNextId(){
        if(map.keySet().isEmpty())
            return 1L;
        return Collections.max(map.keySet()) + 1;
    }
}
