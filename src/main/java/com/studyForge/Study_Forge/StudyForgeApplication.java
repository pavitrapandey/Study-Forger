package com.studyForge.Study_Forge;

import com.studyForge.Study_Forge.Subject.SubjectRepository;
import com.studyForge.Study_Forge.Subject.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class StudyForgeApplication implements CommandLineRunner {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private SubjectRepository subjectRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudyForgeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		subjectRepository.deleteById("dfcca894-1be3-4f3a-a903-8d6aa1fc371c");
	}
}
