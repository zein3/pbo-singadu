package net.sytes.zeinhaddad.singadu.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sytes.zeinhaddad.singadu.entity.ProblemType;
import net.sytes.zeinhaddad.singadu.entity.User;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReportDto {
    private Long id;

    @NotEmpty(message = "Tolong masukan deskripsi")
    private String description;

    @NotNull
    private Boolean solved;

    @NotNull
    private User reporter;

    @NotNull
    private ProblemType problemType;

    @Temporal(TemporalType.DATE)
    private Date reportedDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date createdOn;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private Date updatedOn;
}
