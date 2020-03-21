package com.spring.framework.course.petclinic.bootstrap;

import com.spring.framework.course.petclinic.model.*;
import com.spring.framework.course.petclinic.services.OwnerService;
import com.spring.framework.course.petclinic.services.PetTypeService;
import com.spring.framework.course.petclinic.services.SpecialtyService;
import com.spring.framework.course.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
    }


    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if(count == 0) {
            loadData();
        }
    }

    private void loadData() {
        PetType dog = new PetType();
        dog.setName("dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("cat");
        PetType savedCat = petTypeService.save(cat);

        Owner owner1 = new Owner();
        owner1.setFirstName("Micheal");
        owner1.setLastName("Scott");
        owner1.setAddress("123 SBP");
        owner1.setCity("Scraton");
        owner1.setTelephone("0123465789");

        Pet mikesPet = new Pet();
        mikesPet.setPetType(savedDog);
        mikesPet.setBirthDate(LocalDate.now());
        mikesPet.setName("Goo Goo Gaa Gaa");
        owner1.getPets().add(mikesPet);

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("Dwight");
        owner2.setLastName("Schrute");
        owner2.setAddress("456 SBP");
        owner2.setCity("Scraton");
        owner2.setTelephone("9876543210");

        Pet dwightsPet = new Pet();
        dwightsPet.setPetType(savedCat);
        dwightsPet.setBirthDate(LocalDate.now());
        dwightsPet.setName("Mose Schrute");
        owner2.getPets().add(dwightsPet);

        ownerService.save(owner2);

        System.out.println("Owners Loaded");

        Speciality radiology = new Speciality();
        radiology.setDescription("Radiology");
        Speciality savedRadiology = specialtyService.save(radiology);

        Speciality surgery = new Speciality();
        radiology.setDescription("Surgery");
        Speciality savedSurgery = specialtyService.save(radiology);


        Vet vet1 = new Vet();
        vet1.setFirstName("Dr. Phyllis");
        vet1.setLastName("Vance");
        vet1.getSpecialities().add(savedRadiology);

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Dr. Stanley");
        vet2.setLastName("Hudson");
        vet2.getSpecialities().add(savedSurgery);

        vetService.save(vet2);

        System.out.println("Vets loaded");
    }
}
