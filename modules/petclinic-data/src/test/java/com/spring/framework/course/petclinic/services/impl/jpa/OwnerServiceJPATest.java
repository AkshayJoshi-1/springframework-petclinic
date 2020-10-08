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

import java.util.*;

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
        verify(ownerRepository).findByLastName(matches(LAST_NAME));
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
        verify(ownerRepository, times(1)).save(isA(Owner.class));
    }

    @Test
    void delete() {

        doNothing().when(ownerRepository).delete(any());

        service.delete(returnOwner);

        verify(ownerRepository, times(1)).delete(isA(Owner.class));
    }

    @Test
    void deleteByID() {

        doNothing().when(ownerRepository).deleteById(any());

        service.deleteByID(ID);

        verify(ownerRepository, times(1)).deleteById(ID);
    }

    @Test
    void findAllByLastNameLike() {

        Owner owner = Owner.builder().build();
        owner.setLastName("Test");

        List<Owner> ownerList = new ArrayList<>();
        ownerList.add(owner);

        owner = Owner.builder().build();
        owner.setLastName("Tes");

        ownerList.add(owner);

        when(ownerRepository.findAllByLastNameLike(any())).thenReturn(ownerList);

        List<Owner> returnedList = service.findAllByLastNameLike("Tes");

        assertEquals(2, returnedList.size());
        verify(ownerRepository, times(1)).findAllByLastNameLike(matches("\\%Tes\\%"));
    }
}