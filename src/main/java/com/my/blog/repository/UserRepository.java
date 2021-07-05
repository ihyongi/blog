package com.my.blog.repository;

import com.my.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

//dao
//자동으로 bean등록이 된다
//@Repository생략가능
public interface UserRepository extends JpaRepository<User,Integer> {


}
