package com.spring.framework.course.petclinic.repository;

import com.spring.framework.course.petclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {

    Optional<PetType> findByNameIgnoreCase(String petTypeName);
}
