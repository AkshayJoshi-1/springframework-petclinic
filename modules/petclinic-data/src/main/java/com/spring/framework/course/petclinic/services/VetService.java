package com.spring.framework.course.petclinic.services;

import com.spring.framework.course.petclinic.model.Vet;

public interface VetService extends CrudService<Vet, Long> {
    Vet findByLastName(String lastName);
}
