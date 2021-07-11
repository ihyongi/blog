package com.my.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

//인증이안된 사용자들이 출입할 수 있는 경우 /auth/를 허용
// /그냥주소가 이거일때 index.jsp를 허용
//static이하에 ..허용

@Controller
public class UserController {

    @GetMapping("/auth/joinForm")
    public String joinForm(){
        return "user/joinForm";
    }


    @GetMapping("/auth/loginForm")
    public String loginForm(){
        return "user/loginForm";
    }
}
