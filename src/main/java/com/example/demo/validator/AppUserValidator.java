package com.example.demo.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.example.demo.entity.AppUser;
import com.example.demo.repository.AppUserRepository;

public class AppUserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return AppUser.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        AppUser user = (AppUser) target;
        if (firstNameIsNullOrEmptyString(user.getFirstName())) {
            errors.rejectValue("firstName", "firstName.empty", "firstNameを入力してください。");
        }
    }

    private boolean firstNameIsNullOrEmptyString(final String input) {
        return (input == null || input.trim().length() == 0);
    }

}
