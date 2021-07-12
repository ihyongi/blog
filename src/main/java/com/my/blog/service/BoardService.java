package com.my.blog.service;

import com.my.blog.model.Board;
import com.my.blog.model.RoleType;
import com.my.blog.model.User;
import com.my.blog.repository.BoardRepository;
import com.my.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    //글목록 호출시 ->페이징처리@Pageable
    public Page<Board> boardList(Pageable pageable) {
        Page<Board> page = boardRepository.findAll(pageable);
        return page; //이렇게 넘겨주면 페이징을해서 호출가능하게함 wow..리턴타입!! Page인거!
    }
}
