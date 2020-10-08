package com.spring.framework.course.petclinic.controller;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    public OwnerController(@Autowired OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder webDataBinder) {
        webDataBinder.setDisallowedFields("id");
    }

    @GetMapping("{ownerId}")
    public ModelAndView getOwnerDetails(@PathVariable Long ownerId) {

        ModelAndView modelAndView = new ModelAndView("owners/ownerDetails");
        modelAndView.addObject("owner", ownerService.findById(ownerId));

        return modelAndView;
    }

    @GetMapping("find")
    public String getFindOwnersForm(Model model) {
        model.addAttribute("owner", Owner.builder().build());
        return "owners/findOwners";
    }

    @GetMapping
    public String processFindForm(Owner owner, BindingResult bindingResult, Model model) {

        if(owner.getLastName() == null) {
            owner.setLastName("");
        }

        List<Owner> ownerList = ownerService.findAllByLastNameLike(owner.getLastName());

        if(ownerList.isEmpty()) {
            bindingResult.rejectValue("lastName", "notFound", "notFound");
            return "owners/findOwners";
        } else if(ownerList.size() == 1) {
            return "redirect:/owners/" + ownerList.get(0).getId();
        } else {
            model.addAttribute("selections", ownerList);
            return "owners/ownersList";
        }
    }
}
