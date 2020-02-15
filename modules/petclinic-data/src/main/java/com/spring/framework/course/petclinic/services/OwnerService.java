package com.spring.framework.course.petclinic.services;

import com.spring.framework.course.petclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);
}
