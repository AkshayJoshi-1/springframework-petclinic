package com.spring.framework.course.petclinic.controller;

import com.spring.framework.course.petclinic.model.Pet;
import com.spring.framework.course.petclinic.model.Visit;
import com.spring.framework.course.petclinic.services.PetService;
import com.spring.framework.course.petclinic.services.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@Controller
@RequestMapping("/owners/{ownerId}/pets/{petId}/visits")
public class VisitController {

    private final PetService petService;
    private final VisitService visitService;

    public static final String VISIT_CREATE_UPDATE_FORM = "pets/createOrUpdateVisitForm";

    public VisitController(@Autowired PetService petService,
                           @Autowired VisitService visitService) {
        this.petService = petService;
        this.visitService = visitService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @ModelAttribute("visit")
    public Visit loadPetWithVisit(@PathVariable("petId") Long petId, Map<String, Object> model) {
        Pet pet = petService.findById(petId);
        model.put("pet", pet);
        Visit visit = new Visit();
        pet.getVisits().add(visit);
        visit.setPet(pet);
        return visit;
    }

    @GetMapping("new")
    public String initNewVisitForm(@PathVariable("petId") Long petId, Map<String, Object> model) {
        return VISIT_CREATE_UPDATE_FORM;
    }

    @PostMapping("new")
    public String processNewVisitForm(@PathVariable Long petId,
                                      @Valid Visit visit, BindingResult result) {
        if (result.hasErrors()) {
            return VISIT_CREATE_UPDATE_FORM;
        } else {
            
            Visit savedVisit = visitService.save(visit);

            return "redirect:/owners/" + savedVisit.getPet().getOwner().getId();
        }
    }
}
