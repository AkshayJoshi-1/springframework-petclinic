package com.spring.framework.course.petclinic.repository;

import com.spring.framework.course.petclinic.model.Pet;
import org.springframework.data.repository.CrudRepository;

public interface PetRepository extends CrudRepository<Pet, Long> {
}
