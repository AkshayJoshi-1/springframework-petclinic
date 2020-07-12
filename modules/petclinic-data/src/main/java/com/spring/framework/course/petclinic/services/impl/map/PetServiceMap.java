package com.spring.framework.course.petclinic.services.impl.map;

import com.spring.framework.course.petclinic.model.Pet;
import com.spring.framework.course.petclinic.services.PetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@Profile({"default", "map"})
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
        return super.save(object);
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
