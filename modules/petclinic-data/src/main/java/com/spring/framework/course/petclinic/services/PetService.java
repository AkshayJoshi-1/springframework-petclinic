package com.spring.framework.course.petclinic.services;

import com.spring.framework.course.petclinic.model.Pet;

import java.util.Set;

public interface PetService {

    Pet findById(Long id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
