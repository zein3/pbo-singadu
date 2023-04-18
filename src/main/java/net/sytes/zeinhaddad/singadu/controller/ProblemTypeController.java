package net.sytes.zeinhaddad.singadu.controller;

import java.util.List;

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

import net.sytes.zeinhaddad.singadu.entity.ProblemType;
import net.sytes.zeinhaddad.singadu.repository.ProblemTypeRepository;

@RestController
@RequestMapping("/api/v1/problem-type")
@Validated
public class ProblemTypeController {
    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    @GetMapping
    public List<ProblemType> index() {
        return this.problemTypeRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long store(@RequestBody ProblemType problemType) {
        this.problemTypeRepository.save(problemType);
        return 1l;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Long destroy(@PathVariable Long id) {
        this.problemTypeRepository.deleteById(id);
        return 1l;
    }
}
