package com.my.blog.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity //user클래스가 MYSQL에 테이블 생성이 된다.
@Data
@NoArgsConstructor //빈생성자
@AllArgsConstructor //전체생성자
@Builder //빌더패턴
//@DynamicInsert //role같이 디폴트값잇는밸류들은 제끼고 insert(insert시 null인 필드는 제외) 이게 결코좋은방법은 아님
public class User {

    @Id  @GeneratedValue(strategy = GenerationType.IDENTITY) //pk, 프로젝트에 연결된 db의 넘버링 전략
    private int id; //시퀀스 auto_increment

    @Column(nullable = false, length = 30) //널값을 허용안한다
    private String username;//아이디

    @Column(nullable = false, length = 100) //12345->해쉬(비밀번호 암호화)
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

    //@ColumnDefault("user")
    @Enumerated(EnumType.STRING)//여기까지
    private RoleType role; //enum ->어떤 데이터의 도메인을 만들어줄수있다.. admin, user,manager 권한..
    //도메인이란..?범위 여자/남자..이런식으로..?

    @CreationTimestamp //시간이 자동입력
    private Timestamp createDate;

}
