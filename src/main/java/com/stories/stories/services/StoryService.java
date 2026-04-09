package com.stories.stories.services;

import com.stories.stories.models.Story;
import com.stories.stories.models.StoryRequest;
import com.stories.stories.models.User;
import com.stories.stories.repositories.*;

import java.time.LocalDateTime;
import java.util.List;

public class StoryService {
    private final StoryRepository storyRepository;
    private final CommentRepository commentRepository;
    private final RatingRepository ratingRepository;
    private final ReportRepository reportRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    public StoryService(StoryRepository storyRepository,
            CommentRepository commentRepository, RatingRepository ratingRepository,
            ReportRepository reportRepository, UserRepository userRepository,UserService userService
    ) {
        this.storyRepository = storyRepository;
        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
        this.userService=userService;
    }
    public List<Story> getAllStories() {
        return storyRepository.findAll();
    }

    public Story createStory(StoryRequest request) {
        User user = userService.getUser();

        Story story = new Story();
        story.setTitle(request.getTitle().trim());
        story.setContent(request.getContent().trim());
        story.setPublishDate(LocalDateTime.now());
        story.setProfile(user.getProfile());
        return storyRepository.save(story);
    }
}
