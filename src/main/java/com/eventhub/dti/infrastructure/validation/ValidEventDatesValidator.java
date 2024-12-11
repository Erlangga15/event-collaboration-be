package com.eventhub.dti.infrastructure.validation;

import com.eventhub.dti.infrastructure.dto.EventCreateRequestDTO;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidEventDatesValidator implements ConstraintValidator<ValidEventDates, EventCreateRequestDTO> {

    @Override
    public void initialize(ValidEventDates constraintAnnotation) {
    }

    @Override
    public boolean isValid(EventCreateRequestDTO value, ConstraintValidatorContext context) {
        if (value.getStartDate() == null || value.getEndDate() == null) {
            return true;
        }

        boolean isValid = value.getEndDate().isAfter(value.getStartDate());

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("End date must be after start date")
                    .addPropertyNode("endDate")
                    .addConstraintViolation();
        }

        if (value.getPromotion() != null) {
            if (value.getPromotion().getStartDate() != null && value.getPromotion().getEndDate() != null) {
                boolean isPromotionValid = value.getPromotion().getEndDate()
                        .isAfter(value.getPromotion().getStartDate());

                if (!isPromotionValid) {
                    context.buildConstraintViolationWithTemplate("Promotion end date must be after start date")
                            .addPropertyNode("promotion")
                            .addPropertyNode("endDate")
                            .addConstraintViolation();
                    isValid = false;
                }

                if (value.getPromotion().getStartDate().isBefore(value.getStartDate()) ||
                        value.getPromotion().getEndDate().isAfter(value.getEndDate())) {
                    context.buildConstraintViolationWithTemplate("Promotion dates must be within event dates")
                            .addPropertyNode("promotion")
                            .addConstraintViolation();
                    isValid = false;
                }
            }
        }

        return isValid;
    }
}