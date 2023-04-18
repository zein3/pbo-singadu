package net.sytes.zeinhaddad.singadu.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.sytes.zeinhaddad.singadu.dto.ReportDto;
import net.sytes.zeinhaddad.singadu.entity.ProblemType;
import net.sytes.zeinhaddad.singadu.entity.User;
import net.sytes.zeinhaddad.singadu.form.CreateReportForm;
import net.sytes.zeinhaddad.singadu.repository.ProblemTypeRepository;
import net.sytes.zeinhaddad.singadu.service.IReportService;
import net.sytes.zeinhaddad.singadu.service.IUserService;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private IReportService reportService;

    @Autowired
    private IUserService userService;

    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    @GetMapping
    public List<ReportDto> index() {
        return reportService.getReports();
    }

    @PostMapping
    public Long store(@RequestBody CreateReportForm form) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return 0l;
        }

        User reporter = userService.getUserByEmail(auth.getName());
        Optional<ProblemType> problemType = problemTypeRepository.findById(form.getProblemTypeId());
        if (problemType.isEmpty()) {
            return 0l;
        }

        ReportDto report = ReportDto.builder()
            .description(form.getDescription())
            .solved(false)
            .reporter(reporter)
            .problemType(problemType.get())
            .build();
        return reportService.save(report);
    }

    @DeleteMapping("/{id}")
    public Long destroy(@PathVariable Long id) {
        return reportService.destroy(id);
    }
}
