package com.spring.framework.course.petclinic.controller;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class OwnerControllerTest {

    @Mock
    private OwnerService ownerService;

    @InjectMocks
    private OwnerController controller;

    private Set<Owner> owners;

    private Owner returnOwnerWithId1;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp(){
        owners = new HashSet<>();

        Owner owner = Owner.builder().build();
        owner.setId(1L);

        returnOwnerWithId1 = owner;

        owners.add(owner);

        owner = Owner.builder().build();
        owner.setId(2L);

        owners.add(owner);

        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findOwners() throws Exception {

        mockMvc.perform(get("/owners/find"))
                .andExpect(view().name("owners/findOwners"))
                .andExpect(model().attributeExists("owner"));

        verifyNoInteractions(ownerService);
    }

    @Test
    void getOwnerDetailsTest() throws Exception {
        when(ownerService.findById(anyLong())).thenReturn(returnOwnerWithId1);

        mockMvc.perform(get("/owners/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownerDetails"))
                .andExpect(model().attribute("owner", hasProperty("id", is(1L))));
    }

    @Test
    void findOwnerByLastNameNoMatch() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(new ArrayList<Owner>());

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/findOwners"));
    }

    @Test
    void findOwnerByLastNameOneMatch() throws Exception {
        Owner owner = Owner.builder().build();
        owner.setId(1L);
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Collections.singletonList(owner));

        mockMvc.perform(get("/owners"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/owners/1"));
    }

    @Test
    void findOwnerByLastNameManyMatch() throws Exception {
        when(ownerService.findAllByLastNameLike(anyString())).thenReturn(Arrays.asList(Owner.builder().build(),
                                                                                        Owner.builder().build()));

        mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(view().name("owners/ownersList"))
                .andExpect(model().attribute("selections", hasSize(2)));
    }
}