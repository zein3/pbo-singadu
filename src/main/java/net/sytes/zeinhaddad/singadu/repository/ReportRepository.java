package net.sytes.zeinhaddad.singadu.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import net.sytes.zeinhaddad.singadu.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
    List<Report> findByReporterId(Long id);
    List<Report> findByProblemTypeId(Long id);
}
