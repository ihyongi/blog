package com.my.blog.test;

import com.my.blog.model.RoleType;
import com.my.blog.model.User;
import com.my.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RestController
public class DummyControllerTest {

    @Autowired // 의존성 주입 (DI)
    private UserRepository userRepository;


    @DeleteMapping("/dummy/user/{id}")
    public String deleteUser(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }
        return "삭제되었습니다. id:"+id;
    }

    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //update--email, password
    @Transactional
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){ //json데이터를 요청->자바객체로 받아줌
        //json으로 받으려면 @RequestBody가 필요
        System.out.println("id = " + id);
        System.out.println("password = " + requestUser.getPassword());
        System.out.println("email = " + requestUser.getEmail());

        /*//객체로 save하면 null이 많음..읭 그냥 가져오면되는거아니누
        requestUser.setId(id);
        requestUser.setUsername("ssar");

        userRepository.save(requestUser);//원래는 insert이나 이미 있다면 update시 save잘 안함..?*/

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하였습니다");
        });
        user.setPassword(requestUser.getPassword()); //변경되었구나 알아차리고(변경감지) ->update를 수행한다 :더티체킹
        user.setEmail(requestUser.getEmail());

        //userRepository.save(user); //여기에는 null이 없다다

        /**
         * save함수는 id를 전달하지않으면 insert를 해주고
         * id를 전달하면 해당 id에 대한 데이터가 있으면 update
         * 없으면 insert를 한다
         *
         * @transactional붙이고 save주석처리--더티체킹 오진다!, 함수종료시에 자동 commit
         * save를 하지않아도 update가 된다 !!!!!오져요!!!!!!!!!!!
         */
        return null;
    }



    //한페이지당 2건에 데이터를 리턴받아 볼 예정
    //페이지는 0부터 시작~!
    @GetMapping("/dummy/user")
    public List<User>pageList(@PageableDefault(size = 1, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users =pagingUser.getContent();
        return users;

        /*public List<User>pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC) Pageable pageable){
        List<User> users = userRepository.findAll(pageable).getContent();
        return users;*/
    }

    //{id}주소로 파라미터를 전달 받을 수 있다
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){

        User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("해당유저는 없습니다. id:"+id);
            }
        });

        //user객체는 자바오브젝트 , 요청은 웹브라우저 return은 data를 리턴해주는 restcontroller
        //변환이 필요 ->json 웹브라우저가 이해할 수 있는 언어로
        //springboot는 messageConverter가 자동응답 , jackson라이브러리를 호출하여 json방식으로 브라우저에 던져줌
        return user;

        //람다식:익명으로 처리
        /*User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 사용자는 없습니다.")
        });
        return user;*/
    }


    //http://localhost:8000/blog/dummy/join (요청)
    //http의 body에 username, password, email 데이터를 가지고 요청
    @PostMapping("/dummy/join")
    public String join(User user){
        System.out.println("username:"+user.getUsername());
        System.out.println("password:"+user.getPassword());
        System.out.println("email:"+user.getEmail());

        user.setRole(RoleType.USER);

        userRepository.save(user);

        return "회원가입이 완료되었습니다.";
    }
}