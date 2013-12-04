package no.mesan.controllers.validators;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import no.mesan.model.User;
import no.mesan.persistence.user.UserDao;

public class EmailUniqueValidator implements ConstraintValidator<EmailUnique, String> {

    @Inject
    private UserDao userDao;

    public void initialize(final EmailUnique email){
    }

    public boolean isValid(final String email, final ConstraintValidatorContext context) {
        final User user = userDao.getUserByEmail(email);
        return user == null;
    }
}
