package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.ApplicationUser;
import com.pearlyadana.rakhapuraapp.mapper.ApplicationUserMapper;
import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
import com.pearlyadana.rakhapuraapp.model.response.PaginationResponse;
import com.pearlyadana.rakhapuraapp.repository.UserTableRepository;
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
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class UserTableServiceImpl implements UserTableService {

    @Autowired
    private UserTableRepository userTableRepository;

    @Autowired
    private PaginationUtil paginationUtil;

    private ApplicationUserMapper mapper = Mappers.getMapper(ApplicationUserMapper.class);

    @Override
    public ApplicationUserDto findById(Long id) {
        Optional<ApplicationUser> optional = this.userTableRepository.findById(id);
        return optional.map(this.mapper::mapEntityToDto).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicationUserDto> findUserTableByLoginUsername(String userName) {
        AtomicReference<ApplicationUserDto> userRef = new AtomicReference<>();
        this.userTableRepository.findByLoginUserName(userName).ifPresent((userTable)->{
            userRef.set(this.mapper.mapEntityToDto(userTable));
        });
        return (userRef.get()!=null) ? Optional.of(userRef.get()) : Optional.empty();
    }

    @Transactional(readOnly = true)
    @Override
    public List<ApplicationUserDto> findAllBySearching(Long roleId, String keyword) {
        List<ApplicationUser> applicationUserList;
        if(roleId == 0) {
            applicationUserList = this.userTableRepository.findAllByKeyword(keyword);
        } else {
            applicationUserList = this.userTableRepository.findAllByRoleAndKeyword(roleId, keyword);
        }
        return applicationUserList
                .stream()
                .map(this.mapper::mapEntityToDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    @Override
    public PaginationResponse<ApplicationUserDto> findEachPageBySearchingSortById(int pageNumber, boolean isAscending, Long roleId, String keyword) {
        Pageable sortedById;
        if(isAscending) {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").ascending());
        } else {
            sortedById = PageRequest.of(PaginationUtil.pageNumber(pageNumber),
                    paginationUtil.getPageSize(), Sort.by("id").descending());
        }
        Page<ApplicationUser> page = null;
        if(roleId == 0) {
            page = this.userTableRepository.findAllByKeyword(keyword, sortedById);
        } else {
            page = this.userTableRepository.findAllByRoleAndKeyword(roleId, keyword, sortedById);
        }
        PaginationResponse<ApplicationUserDto> res = new PaginationResponse<ApplicationUserDto>();
        res.addList(page.stream().map(this.mapper::mapEntityToDto).collect(Collectors.toList()))
                .addTotalElements(page.getTotalElements())
                .addTotalPages(page.getTotalPages())
                .addPageSize(page.getSize());
        return res;
    }

    @Override
    public ApplicationUserDto save(ApplicationUserDto applicationUserDto) {
        applicationUserDto.setActiveStatus(true);
        return this.mapper.mapEntityToDto(this.userTableRepository.save(this.mapper.mapDtoToEntity(applicationUserDto)));
    }

    @Override
    public ApplicationUserDto update(ApplicationUserDto applicationUserDto, Long id) {
        applicationUserDto.setId(id);
        return this.mapper.mapEntityToDto(this.userTableRepository.save(this.mapper.mapDtoToEntity(applicationUserDto)));
    }

}
