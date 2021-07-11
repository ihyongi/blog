package com.my.blog.repository;

import com.my.blog.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BoardRepository extends JpaRepository<Board,Integer> {


}
