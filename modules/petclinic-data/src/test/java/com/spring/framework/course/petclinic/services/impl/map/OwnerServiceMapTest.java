package com.spring.framework.course.petclinic.services.impl.map;

import com.spring.framework.course.petclinic.model.Owner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class OwnerServiceMapTest {

    private OwnerServiceMap ownerServiceMap;

    private final Long ownerId = 1L;

    private final String lastName = "lastname";

    @BeforeEach
    void setUp() {
        ownerServiceMap = new OwnerServiceMap(new PetTypeServiceMap(), new PetServiceMap());

        Owner owner = Owner.builder().id(1L).lastName(lastName).address("11").build();
        ownerServiceMap.save(owner);
    }

    @Test
    void findByLastName() {
        Owner owner = ownerServiceMap.findByLastName(lastName);

        assertNotNull(owner);
        assertEquals(ownerId, owner.getId());
        assertEquals(lastName, owner.getLastName());
    }

    @Test
    void findByLastNameNotFound() {
        Owner owner = ownerServiceMap.findByLastName("DNE");

        assertNull(owner);
    }

    @Test
    void findAll() {

        Set<Owner> ownerSet = ownerServiceMap.findAll();
        assertEquals(1, ownerSet.size());
    }

    @Test
    void delete() {
        ownerServiceMap.delete(ownerServiceMap.findById(ownerId));
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void deleteById() {
        ownerServiceMap.deleteById(ownerId);
        assertEquals(0, ownerServiceMap.findAll().size());
    }

    @Test
    void saveExistingID() {

        Owner owner2 = Owner.builder().id(2L).build();

        Owner savedOwner = ownerServiceMap.save(owner2);

        assertEquals(2L, savedOwner.getId());
    }

    @Test
    void savedNoId() {
        Owner savedOwner = ownerServiceMap.save(Owner.builder().build());

        assertNotNull(savedOwner);
        assertNotNull(savedOwner.getId());
    }

    @Test
    void findById() {
        Owner owner = ownerServiceMap.findById(ownerId);
        assertEquals(ownerId, owner.getId());
    }

    @Test
    void findAllByLastNameLike() {

        Owner owner = Owner.builder().lastName("Test").build();

        ownerServiceMap.save(owner);

        owner = Owner.builder().lastName("Tes").build();

        ownerServiceMap.save(owner);

        List<Owner> returnedListWithLastName = ownerServiceMap.findAllByLastNameLike("Tes");
        List<Owner> returnedListWithoutLastName = ownerServiceMap.findAllByLastNameLike("");

        assertEquals(2, returnedListWithLastName.size());
        assertEquals(3, returnedListWithoutLastName.size());
    }
}