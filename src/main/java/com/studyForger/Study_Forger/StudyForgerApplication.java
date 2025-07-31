package com.studyForger.Study_Forger;

import com.studyForger.Study_Forger.Revision.Revision;
import com.studyForger.Study_Forger.Revision.RevisionRepository;
import com.studyForger.Study_Forger.Subject.SubjectRepository;
import com.studyForger.Study_Forger.Subject.SubjectService;
import com.studyForger.Study_Forger.Topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDate;
import java.util.UUID;

@SpringBootApplication
public class StudyForgerApplication implements CommandLineRunner {

	@Autowired
	private SubjectService subjectService;

	@Autowired
	private SubjectRepository subjectRepository;

	@Autowired
	private RevisionRepository revisionRepository;

	@Autowired
	private TopicRepository topicRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudyForgerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		//Delete all revisions
		revisionRepository.deleteAll();

		//set all topic's revision properties default
		topicRepository.findAll().forEach(topic -> {
			topic.setRepetition(0);
			topic.setInterval(0);
			topic.setEaseFactor(2.5);
			topic.setUpdatedAt(LocalDate.now());
			topic.setNextReviewDate(LocalDate.now());
			topic.setCreatedAt(LocalDate.now().minusDays(1));
			topic.setHaveRevised(false);
			topicRepository.save(topic);
		});
		//add all topics to revision table
		topicRepository.findAll().forEach(topic -> {
			Revision revision = Revision.builder()
					.id(UUID.randomUUID().toString())
					.topic(topic)
					.repetition(1)
					.interval(1)
					.easeFactor(2.5)
					.lastReviewDate(LocalDate.now())
					.nextReviewDate(LocalDate.now())
					.build();
			revisionRepository.save(revision);
		});
	}
}
