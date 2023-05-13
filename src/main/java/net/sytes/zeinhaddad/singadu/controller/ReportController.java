package net.sytes.zeinhaddad.singadu.controller;

import jakarta.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sytes.zeinhaddad.singadu.dto.ProblemTypeDto;
import net.sytes.zeinhaddad.singadu.dto.ReportDto;
import net.sytes.zeinhaddad.singadu.dto.UserDto;
import net.sytes.zeinhaddad.singadu.entity.ProblemType;
import net.sytes.zeinhaddad.singadu.form.CreateReportForm;
import net.sytes.zeinhaddad.singadu.mapper.ProblemTypeMapper;
import net.sytes.zeinhaddad.singadu.mapper.UserMapper;
import net.sytes.zeinhaddad.singadu.repository.ProblemTypeRepository;
import net.sytes.zeinhaddad.singadu.service.IProblemTypeService;
import net.sytes.zeinhaddad.singadu.service.IReportService;
import net.sytes.zeinhaddad.singadu.service.IUserService;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.validation.FieldError;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private IReportService reportService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IProblemTypeService problemTypeService;

    @GetMapping
    public List<ReportDto> index() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }

        UserDto user = userService.getUserByEmail(auth.getName());
        List<ReportDto> reports = reportService.getReports();
        if (user.getRole().equals("ADMIN")) {
            return reports;
        } else if (user.getRole().equals("PENGAWAS")) {
            List<UserDto> diawasi = userService.getUsersSupervisedBy(user.getId());
            Set<Long> diawasiId = diawasi.stream().map(u -> u.getId()).collect(Collectors.toSet());

            return reports.stream()
                .filter(report -> diawasiId.contains(report.getReporter().getId()))
                .collect(Collectors.toList());
        } else {
            return reports.stream()
                .filter(report -> report.getReporter().getId() == user.getId())
                .collect(Collectors.toList());
        }
    }

    @PostMapping
    public Long store(@Valid @RequestBody CreateReportForm form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return 0l;
        }

        UserDto reporter = userService.getUserByEmail(auth.getName());
        ProblemTypeDto problemType = problemTypeService.getProblemType(form.getProblemTypeId());

        ReportDto report = ReportDto.builder()
            .description(form.getDescription())
            .solved(false)
            .reporter(UserMapper.mapToUser(reporter))
            .problemType(ProblemTypeMapper.mapToEntity(problemType))
            .build();
        return reportService.save(report);
    }

    @PutMapping
    public Long update(@Valid @RequestBody ReportDto report) {
        ReportDto r = reportService.getReport(report.getId());
        if (r == null) {
            return 0l;
        }

        report.setCreatedOn(r.getCreatedOn());
        report.setUpdatedOn(new Date());
        return reportService.save(report);
    }

    @DeleteMapping("/{id}")
    public Long destroy(@PathVariable Long id) {
        return reportService.destroy(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationException(MethodArgumentNotValidException err) {
        Map<String, String> errors = new HashMap<>();
        err.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return errors;
    }
}
