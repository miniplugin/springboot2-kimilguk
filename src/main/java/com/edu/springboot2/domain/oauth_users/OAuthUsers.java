package com.edu.springboot2.domain.oauth_users;

import com.edu.springboot2.auth.Role;
import com.edu.springboot2.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 이 클래스는 OAuth 인증 후 DB 저장용 @엔티티 객체(테이블)을 생성하는 기능
 */
@Getter
@NoArgsConstructor
@Entity
public class OAuthUsers extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder
    public OAuthUsers(String name, String email, String picture, Role role){
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }
    public OAuthUsers update(String name, String picture){
        this.name = name;
        this.picture = picture;

        return this;
    }
    public String getRoleKey(){
        return this.role.getKey();
    }
}