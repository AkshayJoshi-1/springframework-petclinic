package com.spring.framework.course.petclinic.services;

import com.spring.framework.course.petclinic.model.PetType;

public interface PetTypeService extends CrudService<PetType, Long> {

    PetType findTypeByName(String petTypeName);
}
