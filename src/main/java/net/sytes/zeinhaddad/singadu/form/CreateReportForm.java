package net.sytes.zeinhaddad.singadu.form;

import java.io.Serializable;
import java.util.Date;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateReportForm implements Serializable {

    @NotEmpty
    private String description;

    @NotNull
    private Long problemTypeId;

	@NotNull
	private Date reportedDate;

}
