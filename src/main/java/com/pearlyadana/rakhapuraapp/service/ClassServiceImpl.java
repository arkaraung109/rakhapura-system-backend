package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.Class;
import com.pearlyadana.rakhapuraapp.mapper.ClassMapper;
import com.pearlyadana.rakhapuraapp.model.request.ClassDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.ClassRepository;
import com.pearlyadana.rakhapuraapp.util.PaginationUtil;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClassServiceImpl implements ClassService {

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private final ClassMapper mapper = Mappers.getMapper(ClassMapper.class);

    @Transactional(readOnly = true)
    @Override
    public ClassDto findById(Long id) {
        Optional<Class> optional = this.classRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<String> findDistinctAll() {
        return this.classRepository.findDistinctAll();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClassDto> findAll() {
        return this.classRepository.findAll()
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClassDto> findAllByNameAndAcademicYearAndGrade(String name, Long academicYearId, Long gradeId) {
        return this.classRepository.findAllByNameAndAcademicYearIdAndGradeId(name, academicYearId, gradeId)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClassDto> findAllByAuthorizedStatus(boolean authorizedStatus) {
        return this.classRepository.findAllByAuthorizedStatus(authorizedStatus)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public List<ClassDto> findAllFilteredByAcademicYearAndGrade(Long academicYearId, Long gradeId) {
        return this.classRepository.findAllByAcademicYearIdAndGradeIdAndAuthorizedStatus(academicYearId, gradeId, true)
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<ClassDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long academicYearId, Long gradeId, String keyword) {
        Pageable sortedById = null;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<Class> page = null;
        if(academicYearId == 0 && gradeId == 0) {
            page = this.classRepository.findAllByKeyword(keyword, sortedById);
        } else if(academicYearId != 0 && gradeId == 0) {
            page = this.classRepository.findAllByAcademicYearAndKeyword(academicYearId, keyword, sortedById);
        } else if(academicYearId == 0 && gradeId != 0) {
            page = this.classRepository.findAllByGradeAndKeyword(gradeId, keyword, sortedById);
        } else {
            page = this.classRepository.findAllByAcademicYearAndGradeAndKeyword(academicYearId, gradeId, keyword, sortedById);
        }

        PaginationResponse<ClassDto> res = new PaginationResponse<ClassDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Transactional
    @Override
    public ClassDto save(ClassDto classDto) {
        classDto.setAuthorizedStatus(false);
        return this.mapper.mapEntityToDto(this.classRepository.save(this.mapper.mapDtoToEntity(classDto)));
    }

    @Transactional
    @Override
    public ClassDto update(ClassDto classDto, Long id) {
        classDto.setId(id);
        return this.mapper.mapEntityToDto(this.classRepository.save(this.mapper.mapDtoToEntity(classDto)));
    }

    @Transactional
    @Override
    public void deleteById(Long id) {
        this.classRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void authorizeById(Long id, Long authorizedUserId) {
        this.classRepository.authorizeById(id, authorizedUserId);
    }

}
