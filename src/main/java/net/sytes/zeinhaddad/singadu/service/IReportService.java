package net.sytes.zeinhaddad.singadu.service;

import java.util.List;

import net.sytes.zeinhaddad.singadu.dto.ReportDto;

public interface IReportService {
    List<ReportDto> getReports();
    ReportDto getReport(Long id);
    Long save(ReportDto reportDto);
    Long destroy(Long id);
}
