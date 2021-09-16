package com.edu.springboot2;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * 이 클래스틑 초기 프로젝트 생성시 자동으로 생성되는 파일로서 logger 로 초기 JUnit 테스트를 확인하는 기능
 */
@SpringBootTest
class ApplicationTests {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Test
	void contextLoads() {
		logger.info("Junit 테스트 기본 OK");
	}

}
