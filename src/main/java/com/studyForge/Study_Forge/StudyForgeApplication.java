package com.studyForge.Study_Forge;

import com.studyForge.Study_Forge.Revision.Revision;
import com.studyForge.Study_Forge.Revision.RevisionRepository;
import com.studyForge.Study_Forge.Subject.SubjectRepository;
import com.studyForge.Study_Forge.Subject.SubjectService;
import com.studyForge.Study_Forge.Topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.databind.type.LogicalType.DateTime;

@SpringBootApplication
public class StudyForgeApplication implements CommandLineRunner {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private RevisionRepository revisionRepository;

	@Autowired
	private TopicRepository topicRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudyForgeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
//		//set all topic's revision properties default
//		topicRepository.findAll().forEach(topic -> {
//			topic.setRepetition(0);
//			topic.setInterval(0);
//			topic.setEaseFactor(2.5);
//			topic.setUpdatedAt(topic.getCreatedAt());
//			topic.setNextReviewDate(topic.getCreatedAt());
//			topic.setHave_revised(false);
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
//					.lastReviewDate(topic.getCreatedAt())
//					.nextReviewDate(topic.getCreatedAt().plusDays(1))
//					.build();
//			revisionRepository.save(revision);
//		});
	}
}
