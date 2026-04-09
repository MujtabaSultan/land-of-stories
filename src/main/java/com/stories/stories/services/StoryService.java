package com.stories.stories.services;

import com.stories.stories.models.Story;
import com.stories.stories.repositories.*;

import java.util.List;

public class StoryService {
    private final StoryRepository storyRepository;
    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    public StoryService(StoryRepository storyRepository,
            CommentRepository commentRepository, RatingRepository ratingRepository,
            ReportRepository reportRepository, UserRepository userRepository
    ) {
        this.storyRepository = storyRepository;
        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

}
