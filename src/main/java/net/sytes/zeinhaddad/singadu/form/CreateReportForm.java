package net.sytes.zeinhaddad.singadu.form;

import java.io.Serializable;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class CreateReportForm implements Serializable {

    @NotEmpty
    private String description;

    @NotNull
    private Long problemTypeId;

    public CreateReportForm() {}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getProblemTypeId() {
		return problemTypeId;
	}

	public void setProblemTypeId(Long problemTypeId) {
		this.problemTypeId = problemTypeId;
	}

}
