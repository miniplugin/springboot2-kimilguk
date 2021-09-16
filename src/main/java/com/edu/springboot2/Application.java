package com.edu.springboot2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 이 클래스는 프로젝트 생성시 자동생성되며 프로젝트를 실행시키는 진입점을 처리하는 기능
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
