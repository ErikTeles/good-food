package br.com.goodfood.controller;

import br.com.goodfood.domain.user.UserDTO;
import br.com.goodfood.domain.user.UserRegisterDTO;
import br.com.goodfood.domain.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Transactional
    public UserDTO userRegister(@RequestPart @Valid UserRegisterDTO data, @RequestParam(required = false) MultipartFile profilePic) {
        return userService.userRegister(data, profilePic);
    }

}
