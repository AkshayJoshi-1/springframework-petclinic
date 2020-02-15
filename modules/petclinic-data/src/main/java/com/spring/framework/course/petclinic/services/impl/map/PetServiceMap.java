package com.spring.framework.course.petclinic.services.impl.map;

import com.spring.framework.course.petclinic.model.Pet;
import com.spring.framework.course.petclinic.services.PetService;

import java.util.Set;

public class PetServiceMap extends AbstractMapService<Pet, Long> implements PetService {
    @Override
    public Set<Pet> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Pet object) {
        super.delete(object);
    }

    @Override
    public Pet save(Pet object) {
        return super.save(object.getId(), object);
    }

    @Override
    public void deleteByID(Long aLong) {
        super.deleteById(aLong);
    }

    @Override
    public Pet findById(Long id) {
        return super.findById(id);
    }
}
