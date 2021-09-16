package com.edu.springboot2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 이 클래스는 Jpa 데이터베이스 처리 관련 설정을 하는 기능
 */
@Configuration
@EnableJpaAuditing //대표적으로 생성일자, 수정일자와 같은 도메인마다 공통으로 존재하는 중복 값을 자동으로 입력하는 기능을 사용한다.
public class JpaConfig {
}
