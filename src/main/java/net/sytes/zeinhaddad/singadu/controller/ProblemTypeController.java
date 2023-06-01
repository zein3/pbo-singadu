package net.sytes.zeinhaddad.singadu.controller;

import jakarta.validation.Valid;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.FieldError;

import net.sytes.zeinhaddad.singadu.dto.ProblemTypeDto;
import net.sytes.zeinhaddad.singadu.service.ProblemTypeService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/api/v1/problem-type")
@Validated
public class ProblemTypeController {
    @Autowired
    private ProblemTypeService problemTypeService;

    private Logger logger = LoggerFactory.getLogger(ProblemTypeController.class);

    @GetMapping
    public List<ProblemTypeDto> index() {
        return problemTypeService.getAllProblemTypes();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long store(@Valid @RequestBody ProblemTypeDto problemType) {
        this.problemTypeService.saveProblemType(problemType);
        return 1l;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long destroy(@PathVariable Long id) {
        this.problemTypeService.deleteProblemTypeById(id);
        return 1l;
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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Map<String, String> handleValidationException(SQLIntegrityConstraintViolationException err) {
        Map<String, String> errors = new HashMap<>();
        int errorCode = err.getErrorCode();

        switch(errorCode) {
            default:
                errors.put("Error code", String.valueOf(errorCode));
                break;
        }

        return errors;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public Map<String, String> handleValidationException(NullPointerException err) {
        Map<String, String> errors = new HashMap<>();
        errors.put("Error", "The server encountered an error");
        logger.warn(err.getMessage());

        return errors;
    }
}
