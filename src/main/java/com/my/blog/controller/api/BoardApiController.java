package com.my.blog.controller.api;

import com.my.blog.config.auth.PrincipalDetail;
import com.my.blog.dto.ResponseDto;
import com.my.blog.model.Board;
import com.my.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {


    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal){ //title,content
        boardService.write(board,principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);

    }



}