package com.stories.stories.services;

import com.stories.stories.models.*;
import com.stories.stories.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

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

    private Story getStoryOrThrow(Long storyId) {
        return storyRepository.findById(storyId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Story not found"));
    }
    public Report reportStory(Long storyId, ReportRequest request) {
        if (request == null || request.getComplaint() == null || request.getComplaint().trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "the complaint is missing");
        }

        Story story = getStoryOrThrow(storyId);
        User user = userService.getUser();

        Report report = reportRepository
                .findByStoryAndProfile(story, user.getProfile());

        report.setStory(story);
        report.setProfile(user.getProfile());
        report.setComplaint(request.getComplaint().trim());
        return reportRepository.save(report);
    }

    public List<Story> allStories(){
        return storyRepository.findAll();
    }
    public Story singleStory(Long id){
        return getStoryOrThrow(id);}
}
