package com.spring.framework.course.petclinic.controller;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.model.Pet;
import com.spring.framework.course.petclinic.model.PetType;
import com.spring.framework.course.petclinic.services.OwnerService;
import com.spring.framework.course.petclinic.services.PetService;
import com.spring.framework.course.petclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@Controller
@RequestMapping("/owners/{ownerId}/pets")
public class PetController {

    public static final String PETS_CREATE_OR_UPDATE_FORM = "pets/createOrUpdatePetForm";
    private final OwnerService ownerService;
    private final PetService petService;
    private final PetTypeService petTypeService;


    public PetController(@Autowired OwnerService ownerService,
                         @Autowired PetService petService,
                         @Autowired PetTypeService petTypeService) {
        this.ownerService = ownerService;
        this.petService = petService;
        this.petTypeService = petTypeService;
    }

    @ModelAttribute("types")
    public Collection<PetType> populatePetTypes() {
        return petTypeService.findAll();
    }

    @ModelAttribute("owner")
    public Owner populateOwner(@PathVariable Long ownerId) {
        return ownerService.findById(ownerId);
    }

    @InitBinder("owner")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("new")
    public String getNewPetForm(Owner owner, Model model) {
        Pet pet = Pet.builder().build();
        owner.getPets().add(pet);
        pet.setOwner(owner);
        model.addAttribute("pet", pet);

        return PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("new")
    public String saveNewPet(Owner owner, @Valid Pet pet, BindingResult bindingResult, Model model) {

        if (StringUtils.hasLength(pet.getName()) && pet.isNew() && owner.getPet(pet.getName(), true) != null){
            bindingResult.rejectValue("name", "duplicate", "already exists");
        }

        if(bindingResult.hasErrors()) {
            model.addAttribute("pet", pet);
            return PETS_CREATE_OR_UPDATE_FORM;
        }

        owner.getPets().add(pet);
        Owner savedOwner = ownerService.save(owner);

        return "redirect:/owners/" + savedOwner.getId();
    }

    @GetMapping("{petId}/edit")
    public String getPetEditForm(@PathVariable Long petId, Model model) {
        model.addAttribute(petService.findById(petId));
        return PETS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping("{petId}/edit")
    public String saveEditedPet(Owner owner, @Valid Pet pet, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            pet.setOwner(owner);
            model.addAttribute("pet", pet);
            return PETS_CREATE_OR_UPDATE_FORM;
        } else {
            owner.getPets().add(pet);
            petService.save(pet);
            return "redirect:/owners/" + owner.getId();
        }
    }

}
