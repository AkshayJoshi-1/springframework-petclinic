package com.spring.framework.course.petclinic.services.impl.jpa;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.repository.OwnerRepository;
import com.spring.framework.course.petclinic.repository.PetRepository;
import com.spring.framework.course.petclinic.repository.PetTypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceJPATest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetTypeRepository petTypeRepository;

    @InjectMocks
    private OwnerServiceJPA service;

    private final String LAST_NAME = "Smith";

    private final long ID = 1L;

    private Owner returnOwner;

    @BeforeEach
    void setUp() {

        returnOwner = Owner.builder().build();
        returnOwner.setId(ID);
        returnOwner.setLastName(LAST_NAME);
    }

    @Test
    void findByLastName() {
        when(ownerRepository.findByLastName(any())).thenReturn(returnOwner);

        Owner owner = service.findByLastName(LAST_NAME);

        assertEquals(LAST_NAME, owner.getLastName());
        verify(ownerRepository).findByLastName(any());
    }

    @Test
    void findAll() {

        Set<Owner> ownerSet = new HashSet<>();
        ownerSet.add(returnOwner);

        when(ownerRepository.findAll()).thenReturn(ownerSet);

        assertEquals(1, service.findAll().size());
        verify(ownerRepository, times(1)).findAll();

    }

    @Test
    void findById() {

        when(ownerRepository.findById(ID)).thenReturn(Optional.of(returnOwner));

        Owner owner = service.findById(ID);
        assertNotNull(owner);
        assertEquals(ID, owner.getId());
        verify(ownerRepository, times(1)).findById(ID);

    }

    @Test
    void save() {

        when(ownerRepository.save(any())).thenReturn(returnOwner);

        Owner savedOwner = service.save(returnOwner);

        assertNotNull(savedOwner);
        assertEquals(ID, savedOwner.getId());
        verify(ownerRepository, times(1)).save(any());
    }

    @Test
    void delete() {
    }

    @Test
    void deleteByID() {
    }
}