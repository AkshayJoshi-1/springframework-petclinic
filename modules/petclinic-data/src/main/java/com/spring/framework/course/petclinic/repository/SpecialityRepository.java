package com.spring.framework.course.petclinic.repository;

import com.spring.framework.course.petclinic.model.Speciality;
import org.springframework.data.repository.CrudRepository;

public interface SpecialityRepository extends CrudRepository<Speciality, Long> {
}
