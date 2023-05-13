package net.sytes.zeinhaddad.singadu.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sytes.zeinhaddad.singadu.dto.ProblemTypeDto;
import net.sytes.zeinhaddad.singadu.entity.ProblemType;
import net.sytes.zeinhaddad.singadu.mapper.ProblemTypeMapper;
import net.sytes.zeinhaddad.singadu.repository.ProblemTypeRepository;

@Service
public class ProblemTypeService implements IProblemTypeService {

    @Autowired
    private ProblemTypeRepository problemTypeRepository;

    @Override
    public List<ProblemTypeDto> getAllProblemTypes() {
        List<ProblemType> problemTypes = this.problemTypeRepository.findAll();
        return problemTypes.stream()
            .map((ptype) -> ProblemTypeMapper.mapToDto(ptype))
            .collect(Collectors.toList());
    }

    @Override
    public void saveProblemType(ProblemTypeDto pdto) {
        ProblemType ptype = ProblemTypeMapper.mapToEntity(pdto);
        this.problemTypeRepository.save(ptype);
    }

    @Override
    public void deleteProblemTypeById(Long id) {
        this.problemTypeRepository.deleteById(id);
    }
    
}
