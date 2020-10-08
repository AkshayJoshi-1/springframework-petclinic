package com.spring.framework.course.petclinic.repository;

import com.spring.framework.course.petclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Owner findByLastName(String lastName);

    List<Owner> findAllByLastNameLikeIgnoreCase(String lastName);
}
