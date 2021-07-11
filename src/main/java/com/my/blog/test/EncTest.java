package com.my.blog.test;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncTest {

    public void hashing(){
        String encPassword=new BCryptPasswordEncoder().encode("1234");
        System.out.println("1234 해쉬"+encPassword);
    }
}
