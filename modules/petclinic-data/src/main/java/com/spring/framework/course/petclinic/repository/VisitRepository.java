package com.spring.framework.course.petclinic.repository;

import com.spring.framework.course.petclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitRepository extends CrudRepository<Visit, Long> {
}
