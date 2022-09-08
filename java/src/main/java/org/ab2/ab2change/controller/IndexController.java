package org.ab2.ab2change.controller;

import org.ab2.ab2change.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IndexController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/ping")
    String ping() {
        System.out.println(userRepository.getUsers());
        return "pong";
    }
}
