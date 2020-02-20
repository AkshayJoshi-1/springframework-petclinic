package com.spring.framework.course.petclinic.bootstrap;

import com.spring.framework.course.petclinic.model.Owner;
import com.spring.framework.course.petclinic.model.Vet;
import com.spring.framework.course.petclinic.services.OwnerService;
import com.spring.framework.course.petclinic.services.VetService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final OwnerService ownerService;
    private final VetService vetService;

    public DataLoader(OwnerService ownerService, VetService vetService) {
        this.ownerService = ownerService;
        this.vetService = vetService;
    }


    @Override
    public void run(String... args) throws Exception {

        Owner owner1 = new Owner();
        owner1.setFirstName("ABCD");
        owner1.setLastName("EFGH");

        ownerService.save(owner1);

        Owner owner2 = new Owner();
        owner2.setId(2L);
        owner2.setFirstName("PQRS");
        owner2.setLastName("TUVW");

        ownerService.save(owner2);

        System.out.println("Owners Loaded");

        Vet vet1 = new Vet();
        vet1.setFirstName("Dr. VET1");
        vet1.setLastName("PETCLINIC");

        vetService.save(vet1);

        Vet vet2 = new Vet();
        vet2.setFirstName("Dr. VET2");
        vet2.setLastName("PETCLINIC2");

        vetService.save(vet2);

        System.out.println("Vets loaded");
    }
}
