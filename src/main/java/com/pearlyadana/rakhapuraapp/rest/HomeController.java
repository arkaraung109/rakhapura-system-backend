package com.pearlyadana.rakhapuraapp.rest;

import com.pearlyadana.rakhapuraapp.repository.UserTableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/HOME")
public class HomeController {

    @Autowired
    private UserTableRepository userTableRepository;

    @GetMapping("/")
    public String getHome() {
        return this.userTableRepository.findByLoginUserName("yya").get().getFirstName();
    }

}
