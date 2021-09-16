package com.edu.springboot2.web.dto;

import com.edu.springboot2.domain.simple_users.SimpleUsers;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

/**
 * 이 클래스는 서비스와 컨트롤러에서 전송되는 data 를 임시 저장하는 기능
 */
@NoArgsConstructor
@ToString
@Getter
@Setter
public class SimpleUsersDto {
    private Long id;
    private String username;
    private String password;
    private String role;
    private Boolean enabled;
    private LocalDateTime modifiedDate;

    //조회
    public SimpleUsersDto(SimpleUsers entity){
        this.id = entity.getId();
        this.username = entity.getUsername();
        this.password = entity.getPassword();
        this.role = entity.getRole();
        this.enabled = entity.getEnabled();
        this.modifiedDate = entity.getModifiedDate();
    }
    //저장,수정
    @Builder//Set 매서드로 값을 저장시킬때 빌더 매서드로 대체하면 한번에 작업 가능함.
    public SimpleUsersDto(String username, String password, String role, Boolean enabled) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.enabled = enabled;
    }
    //DB 저장 후 PK 값 반환 가능
    public SimpleUsers toEntity(){
        String encPassword = null;
        if(!password.isEmpty()) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            encPassword = passwordEncoder.encode(password);
        }
        return SimpleUsers.builder()
                .username(username)
                .password(encPassword)
                .role(role)
                .enabled(enabled)
                .build();
    }
    /*
    @Override
    public String toString() {
        return "SimpleUsersDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", enabled=" + enabled +
                ", modifiedDate=" + modifiedDate +
                '}';
    }
    */
}