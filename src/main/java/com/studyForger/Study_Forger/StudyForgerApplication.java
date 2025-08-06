package com.studyForger.Study_Forger;

import com.studyForger.Study_Forger.Revision.Revision;
import com.studyForger.Study_Forger.Revision.RevisionRepository;
import com.studyForger.Study_Forger.Role.Role;
import com.studyForger.Study_Forger.Role.RoleRepository;
import com.studyForger.Study_Forger.Subject.SubjectRepository;
import com.studyForger.Study_Forger.Subject.SubjectService;
import com.studyForger.Study_Forger.Topic.TopicRepository;
import com.studyForger.Study_Forger.User.User;
import com.studyForger.Study_Forger.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@SpringBootApplication
public class StudyForgerApplication implements CommandLineRunner {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private TopicRepository topicRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(StudyForgerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		//Delete all revisions
//		revisionRepository.deleteAll();
//
//		//set all topic's revision properties default
//		topicRepository.findAll().forEach(topic -> {
//			topic.setRepetition(0);
//			topic.setInterval(0);
//			topic.setEaseFactor(2.5);
//			topic.setUpdatedAt(LocalDate.now());
//			topic.setNextReviewDate(LocalDate.now());
//			topic.setLastReviewDate(LocalDate.now());
//			topic.setCreatedAt(LocalDate.now().minusDays(1));
//			topic.setHaveRevised(false);
//			topicRepository.save(topic);
//		});
//		//add all topics to revision table
//		topicRepository.findAll().forEach(topic -> {
//			Revision revision = Revision.builder()
//					.id(UUID.randomUUID().toString())
//					.topic(topic)
//					.repetition(1)
//					.interval(1)
//					.easeFactor(2.5)
//					.lastReviewDate(LocalDate.now())
//					.nextReviewDate(LocalDate.now())
//					.build();
//			revisionRepository.save(revision);
//		});

		User user = userRepository.findByUsername("pavitra07").orElse(null);
		Role role=new Role();
		role.setRoleName("NORMAL");
		role.setId("abcd");
		roleRepository.save(role);
		if(user==null){
			user = User.builder()
					.username("pavitra07")
					.id(UUID.randomUUID().toString())
					.email("pavitrapandey77@gmail.com")
					.name("Pavitra Pandey")
					.roles(List.of(role))
					.about("Creator")
					.password(passwordEncoder.encode("12345"))
		.build();
			userRepository.save(user);
			System.out.println("User Created");
		}


	}
}
