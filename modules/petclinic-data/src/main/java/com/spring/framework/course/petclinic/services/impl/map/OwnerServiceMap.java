package com.spring.framework.course.petclinic.services.impl.map;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.model.Pet;
import com.spring.framework.course.petclinic.services.OwnerService;
import com.spring.framework.course.petclinic.services.PetService;
import com.spring.framework.course.petclinic.services.PetTypeService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Profile({"default", "map"})
public class OwnerServiceMap extends AbstractMapService<Owner, Long> implements OwnerService{

    private final PetTypeService petTypeService;
    private final PetService petService;

    public OwnerServiceMap(PetTypeService petTypeService, PetService petService) {
        this.petTypeService = petTypeService;
        this.petService = petService;
    }

    @Override
    public Owner findByLastName(String lastName) {

        return this.findAll()
                .stream()
                .filter(owner -> owner.getLastName().equalsIgnoreCase(lastName))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Owner> findAllByLastNameLike(String lastName) {

        return this.findAll()
                .stream()
                .filter(owner -> owner.getLastName().contains(lastName))
                .collect(Collectors.toList());
    }

    @Override
    public Set<Owner> findAll() {
        return super.findAll();
    }

    @Override
    public void delete(Owner object) {
        super.delete(object);
    }

    @Override
    public Owner save(Owner object) {
        if(object != null) {
            if(object.getPets() != null){
                object.getPets().forEach(this::saveData);
            }
            return super.save(object);
        }
        return null;
    }

    private void saveData(Pet pet) {
        if(pet.getPetType() != null) {
            if(pet.getPetType().getId() == null) {
                pet.setPetType(petTypeService.save(pet.getPetType()));
            }
        } else {
            throw new RuntimeException("Pet Type is required");
        }

        if(pet.getId() == null) {
            Pet savedPet = petService.save(pet);
            pet.setId(savedPet.getId());
        }
    }

    @Override
    public void deleteByID(Long aLong) {
        super.deleteById(aLong);
    }

    @Override
    public Owner findById(Long id) {
        return super.findById(id);
    }
}
