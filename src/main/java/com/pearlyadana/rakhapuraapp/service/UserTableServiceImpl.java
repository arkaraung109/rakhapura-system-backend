package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.mapper.ApplicationUserMapper;
import com.pearlyadana.rakhapuraapp.model.response.ApplicationUserDto;
import com.pearlyadana.rakhapuraapp.repository.UserTableRepository;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserTableServiceImpl implements UserTableService {

    private ApplicationUserMapper mapper = Mappers.getMapper(ApplicationUserMapper.class);

    @Autowired
    private UserTableRepository userTableRepository;

    @Transactional(readOnly = true)
    @Override
    public Optional<ApplicationUserDto> findUserTableByLoginUsername(String userName) {
        AtomicReference<ApplicationUserDto> userRef = new AtomicReference<>();
        this.userTableRepository.findByLoginUserName(userName).ifPresent((userTable)->{
            userRef.set(this.mapper.mapEntityToDto(userTable));
        });
        return (userRef.get()!=null) ? Optional.of(userRef.get()) : Optional.empty();
    }

}
