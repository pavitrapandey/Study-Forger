package com.studyForger.Study_Forger.Dashboard;

import com.studyForger.Study_Forger.Topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardServiceImpl implements DashboardService{

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public DashboardResponseDto getDashboard(String userId){
        LocalDate today=LocalDate.now();
        int totalTopics=topicRepository.countTopicsDueToday(userId,today)+topicRepository.countByUserIdCreatedToday(userId,today);
        int totalTopicsCompletedToday=topicRepository.countByUserIdHaveRevisedToday(userId,today);

        return DashboardResponseDto.builder().totalTopics(totalTopics).totalTopicsCompleted(totalTopicsCompletedToday).build();
    }
}
