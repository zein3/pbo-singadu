package net.sytes.zeinhaddad.singadu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sytes.zeinhaddad.singadu.dto.ReportDto;
import net.sytes.zeinhaddad.singadu.entity.Report;
import net.sytes.zeinhaddad.singadu.mapper.ReportMapper;
import net.sytes.zeinhaddad.singadu.repository.ReportRepository;

@Service
public class ReportService implements IReportService {
    @Autowired
    private ReportRepository reportRepository;

	@Override
	public List<ReportDto> getReports() {
		List<Report> reports = this.reportRepository.findAll();
        return reports.stream()
            .map((report) -> (ReportMapper.mapToDto(report)))
            .collect(Collectors.toList());
	}

	@Override
	public ReportDto getReport(Long id) {
		return null;
	}

	@Override
	public Long save(ReportDto reportDto) {
        try {
            this.reportRepository.save(ReportMapper.mapToEntity(reportDto));
            return 1l;
        } catch(Exception e) {
            return 0l;
        }
	}

	@Override
	public Long destroy(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
