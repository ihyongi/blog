package com.my.blog.service;

import com.my.blog.model.User;
import com.my.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//스프링이 컴포넌트스캔을 통해 빈에 등록을 해줌 ,loc한다
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    //회원가입
    @Transactional
    public void save(User user){
            userRepository.save(user);
    }

    //로그인
    @Transactional(readOnly = true) //select시 트랜잭션시작, 서비스종료시에 트랙잭션종료.. (정합성유지에 도움을 준다)
    public User login(User user) {
        return userRepository.findByUsernameAndPassword(user.getUsername(),user.getPassword());
    }
}
