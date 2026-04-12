package com.stories.stories.services;

import com.stories.stories.models.Rating;
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
    public void deleteStory(Long id){
        Story story =storyRepository.findById(id).orElseGet(null);
        User currentUser = userService.getUser();
        if(story.getProfile().getId().equals(currentUser.getProfile().getId())){
            storyRepository.deleteById(id);
        }
        else {
            System.out.println("thats not yours to delete");
        }
    }
    public void rateStory(Long storyId,int rating){

        User user = userService.getUser();

        Story story = storyRepository.findById(storyId).orElseThrow(()->new RuntimeException("story doesnt exist " + storyId));

        Rating newRating = new Rating();
        newRating.setScore(rating);
        newRating.setStory(story);
        newRating.setProfile(user.getProfile());
        ratingRepository.save(newRating);


    }

    public List<Story> allStories(){
        return storyRepository.findAll();
    }
    public Story singleStory(Long id){
        return storyRepository.findById(id).orElseThrow(()->new RuntimeException("this story doesnt exist" + id));
    }
}
