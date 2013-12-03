package no.mesan.controllers.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.mesan.model.User;
import no.mesan.persistence.UserDao;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {
    
    @Inject
    private UserDao userDao;
    
    public void initialize(EmailUnique email){
    }
    
    public boolean isValid(String email, ConstraintValidatorContext context) {
        User user = userDao.getUserByEmail(email);
        return user == null;
    }
}
