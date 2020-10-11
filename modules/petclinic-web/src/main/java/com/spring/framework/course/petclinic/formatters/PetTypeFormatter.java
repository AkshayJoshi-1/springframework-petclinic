package com.spring.framework.course.petclinic.formatters;

import com.spring.framework.course.petclinic.model.PetType;
import com.spring.framework.course.petclinic.services.PetTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Locale;

@Component
public class PetTypeFormatter implements Formatter<PetType> {

    private final PetTypeService petTypeService;

    public PetTypeFormatter(@Autowired PetTypeService petTypeService) {
        this.petTypeService = petTypeService;
    }


    @Override
    public PetType parse(String text, Locale locale) throws ParseException {
        PetType petType = petTypeService.findTypeByName(text);

        if (petType == null) {
            throw new ParseException("Unable to parse " + text, 0);
        }

        return petType;
    }

    @Override
    public String print(PetType petType, Locale locale) {
        return petType.getName();
    }
}
