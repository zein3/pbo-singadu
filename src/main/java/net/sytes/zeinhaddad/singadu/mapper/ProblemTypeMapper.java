package net.sytes.zeinhaddad.singadu.mapper;

import net.sytes.zeinhaddad.singadu.dto.ProblemTypeDto;
import net.sytes.zeinhaddad.singadu.entity.ProblemType;

public class ProblemTypeMapper {
    public static ProblemTypeDto mapToDto(ProblemType problemDto) {
        ProblemTypeDto problemTypeDto = ProblemTypeDto.builder()
            .id(problemDto.getId())
            .name(problemDto.getName())
            .build();

        return problemTypeDto;
    }

    public static ProblemType mapToEntity(ProblemTypeDto problemTypeDto) {
        ProblemType problemType = ProblemType.builder()
            .id(problemTypeDto.getId())
            .name(problemTypeDto.getName())
            .build();

        return problemType;
    }
}
