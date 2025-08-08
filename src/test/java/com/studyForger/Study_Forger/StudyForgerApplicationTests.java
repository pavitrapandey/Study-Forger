package com.studyForger.Study_Forger;

import com.studyForger.Study_Forger.Security.Jwt.JwtHelper;
import com.studyForger.Study_Forger.User.User;
import com.studyForger.Study_Forger.User.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StudyForgerApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private JwtHelper helper;


	@Test
	void contextLoads() {
	}

	@Test
	void testToken(){


	}

}
