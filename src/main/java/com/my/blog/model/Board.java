package com.my.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Board {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 100)
    private String title;
    @Lob //대용량데이터
    private String content; //섬머노트라이브러리<html> 섞여서 디자인이 됨
    @ColumnDefault("0")
    private int count;//조회수

    @ManyToOne(fetch = FetchType.EAGER)//many:board, one:user , 기본전략이 eager
    @JoinColumn(name = "userId")
    private User user;// db는 오브젝트를 저장할수없다..fk 자바는 오브젝트 저장

    @OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //mappedBy가 있는쪽은 주인이아님,,기본전략이 lazy
    private List<Reply> reply=new ArrayList<>(); //하나의 게시글에는 답변이 여러개

    @CreationTimestamp
    private Timestamp createDate;
}
