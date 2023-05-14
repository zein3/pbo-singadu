package net.sytes.zeinhaddad.singadu.mapper;

import net.sytes.zeinhaddad.singadu.dto.ReportDto;
import net.sytes.zeinhaddad.singadu.entity.Report;

public class ReportMapper {
    public static ReportDto mapToDto(Report report) {
        ReportDto dto = ReportDto.builder()
            .id(report.getId())
            .description(report.getDescription())
            .solved(report.getSolved())
            .reporter(report.getReporter())
            .problemType(report.getProblemType())
            .reportedDate(report.getReportedDate())
            .createdOn(report.getCreatedOn())
            .updatedOn(report.getUpdatedOn())
            .build();

        return dto;
    }

    public static Report mapToEntity(ReportDto report) {
        Report entity = Report.builder()
            .id(report.getId())
            .description(report.getDescription())
            .solved(report.getSolved())
            .reporter(report.getReporter())
            .problemType(report.getProblemType())
            .reportedDate(report.getReportedDate())
            .createdOn(report.getCreatedOn())
            .updatedOn(report.getUpdatedOn())
            .build();

        return entity;
    }
}
