package com.spring.framework.course.petclinic.bootstrap;

import com.spring.framework.course.petclinic.model.*;
import com.spring.framework.course.petclinic.services.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;
    private final PetTypeService petTypeService;
    private final SpecialtyService specialtyService;
    private final VisitService visitService;
    private final PetService petService;

    public DataLoader(OwnerService ownerService, VetService vetService, PetTypeService petTypeService, SpecialtyService specialtyService, VisitService visitService, PetService petService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
        this.petTypeService = petTypeService;
        this.specialtyService = specialtyService;
        this.visitService = visitService;
        this.petService = petService;
    }


    @Override
    public void run(String... args) throws Exception {

        int count = petTypeService.findAll().size();
        if(count == 0) {
            loadData();
        }
    }

    @Transactional
    private void loadData() {
        PetType dog = new PetType();
        dog.setName("Dog");
        PetType savedDog = petTypeService.save(dog);

        PetType cat = new PetType();
        cat.setName("Cat");
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
        owner1.addPets(mikesPet);

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
        owner2.addPets(dwightsPet);

        ownerService.save(owner2);
        petService.save(dwightsPet);

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

        Visit visitForMike = new Visit();
        visitForMike.setPet(mikesPet);
        visitForMike.setDate(LocalDate.of(2020, 2, 24));
        visitForMike.setDescription("Burnt Foot");


        Visit visitForDwight = new Visit();
        visitForDwight.setPet(dwightsPet);
        visitForDwight.setDate(LocalDate.now());
        visitForDwight.setDescription("Concussion");

        visitService.save(visitForDwight);

        System.out.println("Vets loaded");
    }
}
