package com.flexisaf.simp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@TestPropertySource("classpath:test.properties")
@SpringBootTest
class SimpApplicationTests {

	@Test
	void contextLoads() {
	}

}
