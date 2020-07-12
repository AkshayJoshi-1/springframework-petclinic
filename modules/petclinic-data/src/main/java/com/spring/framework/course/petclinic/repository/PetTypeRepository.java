package com.spring.framework.course.petclinic.repository;

import com.spring.framework.course.petclinic.model.PetType;
import org.springframework.data.repository.CrudRepository;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {
}
