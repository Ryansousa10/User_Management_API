package com.compassuol.sp.challenge03.validator;

import com.compassuol.sp.challenge03.model.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (user.getFirstName() != null && user.getFirstName().length() < 3) {
            errors.rejectValue("firstName", "user.firstName.minlength", "O campo firstName deve ter no mínimo 3 caracteres");
        }
    }
}
