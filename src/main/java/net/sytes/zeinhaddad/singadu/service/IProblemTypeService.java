package net.sytes.zeinhaddad.singadu.service;

import java.util.List;

import net.sytes.zeinhaddad.singadu.dto.ProblemTypeDto;

public interface IProblemTypeService {
    List<ProblemTypeDto> getAllProblemTypes();
    ProblemTypeDto getProblemType(Long id);
    void saveProblemType(ProblemTypeDto pdto);
    void deleteProblemTypeById(Long id);
}
