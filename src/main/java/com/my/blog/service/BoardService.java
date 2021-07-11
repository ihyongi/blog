package com.my.blog.service;

import com.my.blog.model.Board;
import com.my.blog.model.RoleType;
import com.my.blog.model.User;
import com.my.blog.repository.BoardRepository;
import com.my.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

//스프링이 컴포넌트스캔을 통해 빈에 등록을 해줌 ,loc한다
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Transactional
    public void write(Board board, User user){ //title, content
        board.setCount(0);
        //유저정보 필요
        board.setUser(user);
        boardRepository.save(board);
    }

    public List<Board> boardList() {
       return boardRepository.findAll();
    }
}
