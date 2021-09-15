package com.edu.springboot2.domain.simple_users;

import com.edu.springboot2.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;

@Getter
@ToString//ToString 매서드 자동생성.
@NoArgsConstructor
@Entity
@Table(
        name="SimpleUsers",
        uniqueConstraints={
                @UniqueConstraint(
                        columnNames={"username"} //여기서 username 필드는 로그인 아이디로 사용됨.
                )
        }
)
public class SimpleUsers extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)//, unique=true
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Boolean enabled;

    @Builder //Set 매서드로 값을 저장시킬때 빌더 매서드로 대체하면 한번에 작업 가능함.
    public SimpleUsers(String username, String password, String role, Boolean enabled){
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
    //수정시 DB 쿼리 없이 아래 메서드로 DB 데이터 바로 수정 가능
    public void update(String username, String password, String role, Boolean enabled){
        String encPassword = null;
        if(!password.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            encPassword = passwordEncoder.encode(password);
        }
        this.username = username;
        if(!password.isEmpty()) {//에러페이지 만들기 encPassword
            this.password = encPassword;
        }
        this.role = role;
        this.enabled = enabled;
    }
    /*
    @Override
    public String toString() {
        return "SimpleUsers{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                '}';
    }
    */
}