package net.sytes.zeinhaddad.singadu.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import net.sytes.zeinhaddad.singadu.validator.PasswordMatches;

@PasswordMatches
public class ChangePasswordForm implements Serializable {
    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;

    @NotEmpty
    private String confirmPassword;

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

}
