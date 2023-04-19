package net.sytes.zeinhaddad.singadu.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import net.sytes.zeinhaddad.singadu.form.ChangePasswordForm;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

	@Override
	public boolean isValid(Object obj, ConstraintValidatorContext context) {
        ChangePasswordForm form = (ChangePasswordForm) obj;
		return form.getNewPassword().equals(form.getConfirmPassword());
	}

}
