package com.csv.clientutility.filter;

import com.csv.clientutility.ClientUtilityApplication;
import com.csv.clientutility.domain.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// PersonBirthdateValidator class
public class PersonBirthdateValidator implements PersonValidator {

    private static final Logger logger = LoggerFactory.getLogger(PersonBirthdateValidator.class);

    @Override
    public boolean isValidPerson(Person person) {
        if (person == null || person.getBirthdate() == null || !person.getGender().isValid()) {
            return false;
        }

        // Additional validation for birthdate format and values
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            LocalDate birthdate = LocalDate.parse(person.getBirthdate().toString(), dateFormatter);

            int year = birthdate.getYear();
            int month = birthdate.getMonthValue();
            int day = birthdate.getDayOfMonth();

            // Check if year, month, and day fall within valid ranges
            boolean isValidYear = year >= 1900 && year <= 9999;
            boolean isValidDay = day >= 1 && day <= birthdate.lengthOfMonth();

            return isValidYear && isValidDay && birthdate.isBefore(LocalDate.now());
        } catch (DateTimeParseException e) {
            logger.error("Invalid date format: {}", person.getBirthdate());
            return false;
        }
    }
}
