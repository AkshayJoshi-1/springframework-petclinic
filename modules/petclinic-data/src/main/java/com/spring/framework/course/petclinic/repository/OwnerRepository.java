package com.spring.framework.course.petclinic.repository;

import com.spring.framework.course.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
