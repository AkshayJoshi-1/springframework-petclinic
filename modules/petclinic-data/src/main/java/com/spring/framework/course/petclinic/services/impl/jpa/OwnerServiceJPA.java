package com.spring.framework.course.petclinic.services.impl.jpa;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.repository.OwnerRepository;
import com.spring.framework.course.petclinic.repository.PetRepository;
import com.spring.framework.course.petclinic.repository.PetTypeRepository;
import com.spring.framework.course.petclinic.services.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Profile("jpa")
public class OwnerServiceJPA implements OwnerService {

    private final OwnerRepository ownerRepository;
    private final PetRepository petRepository;
    private final PetTypeRepository petTypeRepository;

    public OwnerServiceJPA(OwnerRepository ownerRepository, PetRepository petRepository, PetTypeRepository petTypeRepository) {
        this.ownerRepository = ownerRepository;
        this.petRepository = petRepository;
        this.petTypeRepository = petTypeRepository;
    }


    @Override
    public Owner findByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {

        // '%' is used as wild card entry
        return ownerRepository.findAllByLastNameLike("%" + lastName + "%");
    }

    @Override
    public Set<Owner> findAll() {

        Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owners::add);
        return owners;
    }

    @Override
    public Owner findById(Long aLong) {
        return ownerRepository.findById(aLong).orElse(null);
    }

    @Override
    public Owner save(Owner object) {
        return ownerRepository.save(object);
    }

    @Override
    public void delete(Owner object) {
        ownerRepository.delete(object);
    }

    @Override
    public void deleteByID(Long aLong) {
        ownerRepository.deleteById(aLong);
    }
}
