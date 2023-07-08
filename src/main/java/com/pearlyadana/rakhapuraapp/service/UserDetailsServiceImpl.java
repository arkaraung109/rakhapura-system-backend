package com.pearlyadana.rakhapuraapp.service;

import com.pearlyadana.rakhapuraapp.entity.UserRole;
import com.pearlyadana.rakhapuraapp.model.AuthenticatedUser;
import com.pearlyadana.rakhapuraapp.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserTableRepository userTableRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AtomicReference<AuthenticatedUser> userRef = new AtomicReference<>();
        this.userTableRepository.findByLoginUserName(username).ifPresentOrElse(userTable->{
            AuthenticatedUser authUser = new AuthenticatedUser();
            authUser.setLoginUserName(userTable.getLoginUserName());
            authUser.setId(userTable.getId());
            authUser.setPassword(userTable.getPassword());
            authUser.setFirstName(userTable.getFirstName());
            authUser.setLastName(userTable.getLastName());
            UserRole role = new UserRole();
            role.setId(userTable.getRole().getId());
            role.setName(userTable.getRole().getName());
            authUser.setRole(role);
            userRef.set(authUser);
        }, () -> {
            throw new UsernameNotFoundException("User is not found.");
        });
        return userRef.get();
    }

}
