package com.spring.framework.course.petclinic.services.impl.jpa;

import com.spring.framework.course.petclinic.model.Visit;
import com.spring.framework.course.petclinic.repository.VisitRepository;
import com.spring.framework.course.petclinic.services.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("jpa")
public class VisitServiceJPA implements VisitService {

    private final VisitRepository visitRepository;

    public VisitServiceJPA(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {

        Set<Visit> result = new HashSet<>();
        visitRepository.findAll().forEach(result::add);
        return result;
    }

    @Override
    public Visit findById(Long aLong) {
        return visitRepository.findById(aLong).orElse(null);
    }

    @Override
    public Visit save(Visit object) {
        return visitRepository.save(object);
    }

    @Override
    public void delete(Visit object) {
        visitRepository.delete(object);
    }

    @Override
    public void deleteByID(Long aLong) {
        visitRepository.deleteById(aLong);
    }
}
