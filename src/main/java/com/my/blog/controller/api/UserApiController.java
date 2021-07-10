package com.my.blog.controller.api;

import com.my.blog.dto.ResponseDto;
import com.my.blog.model.RoleType;
import com.my.blog.model.User;
import com.my.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class UserApiController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/user")
    public ResponseDto<Integer> save(@RequestBody User user){ //username, password,email
        System.out.println("UserApiController: save호출됨");
        //실제로 DB에 insert하고 아래에서 리턴하면 된다.
        user.setRole(RoleType.USER);
        userService.save(user);
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }

    //기본
    @PostMapping("/api/user/login")
    public ResponseDto<Integer> login(@RequestBody User user, HttpSession session){
        System.out.println("UserApiController: login호출됨");
        User principal = userService.login(user); //principal--접근주체

        if(principal!=null){
            session.setAttribute("principal",principal); //세션에 담기
        }
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}