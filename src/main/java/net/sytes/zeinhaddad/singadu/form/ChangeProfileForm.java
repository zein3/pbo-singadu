package net.sytes.zeinhaddad.singadu.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;

public class ChangeProfileForm implements Serializable {
    @NotEmpty
    private String name;

    @NotEmpty
    private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
