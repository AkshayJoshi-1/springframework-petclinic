package com.spring.framework.course.petclinic.controller;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.model.Pet;
import com.spring.framework.course.petclinic.model.Visit;
import com.spring.framework.course.petclinic.services.PetService;
import com.spring.framework.course.petclinic.services.VisitService;
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
class VisitControllerTest {

    @Mock
    private PetService petService;

    @Mock
    private VisitService visitService;

    @InjectMocks
    private VisitController visitController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(visitController).build();
        when(petService.findById(anyLong())).thenReturn(Pet.builder().id(1L).build());
    }

    @Test
    public void initNewVisitFormTest() throws Exception {

        mockMvc.perform(get("/owners/1/pets/1/visits/new"))
                .andExpect(status().isOk())
                .andExpect(view().name(VisitController.VISIT_CREATE_UPDATE_FORM))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"));

        verify(petService, times(1)).findById(anyLong());
    }

    @Test
    public void processNewVisitFormTest() throws Exception {

        Visit visit = Visit.builder().build();
        visit.setId(1L);
        visit.setPet(Pet.builder().id(1L).owner(Owner.builder().id(1L).build()).build());
        when(visitService.save(any())).thenReturn(visit);

        mockMvc.perform(post("/owners/1/pets/1/visits/new"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("visit"));

        verify(petService, times(1)).findById(anyLong());
        verify(visitService, times(1)).save(any());

    }
}