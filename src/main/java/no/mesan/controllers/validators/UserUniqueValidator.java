package no.mesan.controllers.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.mesan.model.User;
import no.mesan.persistence.UserDao;

public class UserUniqueValidator implements ConstraintValidator<UserUnique, String> {
    
    @Inject
    private UserDao userDao;
    
    public void initialize(final UserUnique user){
    }
    
    public boolean isValid(final String user, final ConstraintValidatorContext context) {
        final User userFromDatabase = userDao.getUserByUsername(user);
        return userFromDatabase == null;
    }
}
