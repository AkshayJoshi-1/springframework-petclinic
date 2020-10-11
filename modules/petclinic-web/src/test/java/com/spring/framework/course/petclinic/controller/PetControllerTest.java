package com.spring.framework.course.petclinic.controller;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.model.Pet;
import com.spring.framework.course.petclinic.services.OwnerService;
import com.spring.framework.course.petclinic.services.PetService;
import com.spring.framework.course.petclinic.services.PetTypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class PetControllerTest {

    @Mock
    private OwnerService ownerService;

    @Mock
    private PetService petService;

    @Mock
    private PetTypeService petTypeService;

    @InjectMocks
    private PetController petController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {

        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
    }

    @Test
    public void getNewPetFormTest() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(PetController.PETS_CREATE_OR_UPDATE_FORM))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService, times(1)).findById(anyLong());
    }

    @Test
    public void saveNewPetTest() throws Exception {

        Owner owner = Owner.builder().id(1L).build();
        when(ownerService.findById(anyLong())).thenReturn(owner);
        when(ownerService.save(any())).thenReturn(owner);

        mockMvc.perform(post("/owners/1/pets/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService, times(1)).findById(anyLong());
        verify(ownerService, times(1)).save(any());

    }

    @Test
    public void getPetEditFormTest() throws Exception {

        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        when(petService.findById(any())).thenReturn(Pet.builder().id(1L).build());

        mockMvc.perform(get("/owners/1/pets/1/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name(PetController.PETS_CREATE_OR_UPDATE_FORM))
                .andExpect(model().attributeExists("owner"))
                .andExpect(model().attributeExists("pet"));

        verify(ownerService, times(1)).findById(anyLong());
        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    public void savePetEditFormTest() throws Exception {

        when(ownerService.findById(anyLong())).thenReturn(Owner.builder().id(1L).build());
        when(petService.save(any())).thenReturn(Pet.builder().id(1L).build());

        mockMvc.perform(post("/owners/1/pets/1/edit"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("owner"));

        verify(ownerService, times(1)).findById(anyLong());
        verify(petService, times(1)).save(any());
    }
}