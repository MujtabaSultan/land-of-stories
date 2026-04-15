package com.stories.stories.services;

import com.stories.stories.models.*;
import com.stories.stories.repositories.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

@Service
public class StoryService {
    private static final Map<String, ReadWriteLock> TITLE_LOCKS = new ConcurrentHashMap<>();
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
    public Story createStory(StoryRequest request) {
        User user = userService.getUser();
        checker(request.getTitle());
        checker(request.getContent());
        String normalizedTitle = normalizeTitle(request.getTitle());
        ReadWriteLock titleLock = TITLE_LOCKS.computeIfAbsent(normalizedTitle, key -> new ReentrantReadWriteLock());

        titleLock.writeLock().lock();
        try {
            if (storyRepository.existsByTitleIgnoreCase(request.getTitle().trim())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Story title already exists");
            }

            Story story = new Story();
            story.setTitle(request.getTitle().trim());
            story.setContent(request.getContent().trim());
            story.setPublishDate(LocalDateTime.now());
            story.setProfile(user.getProfile());
            return storyRepository.save(story);
        } finally {
            titleLock.writeLock().unlock();
        }
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
        checker(request.getComplaint());

        Story story = getStoryOrThrow(storyId);
        User user = userService.getUser();

        Report report = reportRepository
                .findByStoryAndProfile(story, user.getProfile());

        report.setStory(story);
        report.setProfile(user.getProfile());
        report.setComplaint(request.getComplaint().trim());
        return reportRepository.save(report);
    }

    public List<StorySummaryResponse> allStories() {
        return storyRepository.findAll().stream()
                .map(this::toStorySummaryResponse)
                .collect(Collectors.toList());
    }
    public StoryDetailsResponse singleStory(Long id) {
        Story story = getStoryOrThrow(id);
        return toStoryDetailsResponse(story);
    }

    public Comment addComment(Long storyId, CommentRequest request) {

        checker(request.getContent());

        Story story = getStoryOrThrow(storyId);
        User user = userService.getUser();

        Comment comment = new Comment();
        comment.setContent(request.getContent().trim());
        comment.setStory(story);
        comment.setProfile(user.getProfile());
        return commentRepository.save(comment);
    }
    private void checker(String content){
        if (content == null || content.trim().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "content is required");
        }
    }

    private String normalizeTitle(String title) {
        return title.trim().toLowerCase();
    }

    private StorySummaryResponse toStorySummaryResponse(Story story) {
        StorySummaryResponse response = new StorySummaryResponse();
        response.setId(story.getId());
        response.setTitle(story.getTitle());
        response.setContent(story.getContent());
        response.setPublishDate(story.getPublishDate());
        response.setAverageRating(story.getAverageRating());
        response.setRatingsCount(story.getRatings() == null ? 0 : story.getRatings().size());
        response.setWriter(toWriterResponse(story.getProfile()));
        return response;
    }

    private StoryDetailsResponse toStoryDetailsResponse(Story story) {
        StoryDetailsResponse response = new StoryDetailsResponse();
        response.setId(story.getId());
        response.setTitle(story.getTitle());
        response.setContent(story.getContent());
        response.setPublishDate(story.getPublishDate());
        response.setAverageRating(story.getAverageRating());
        response.setRatingsCount(story.getRatings() == null ? 0 : story.getRatings().size());
        response.setWriter(toWriterResponse(story.getProfile()));

        List<StoryCommentResponse> commentResponses = story.getComment() == null
                ? List.of()
                : story.getComment().stream().map(comment -> {
                    StoryCommentResponse commentResponse = new StoryCommentResponse();
                    commentResponse.setId(comment.getId());
                    commentResponse.setContent(comment.getContent());
                    commentResponse.setWriter(toWriterResponse(comment.getProfile()));
                    return commentResponse;
                }).collect(Collectors.toList());
        response.setComments(commentResponses);
        return response;
    }

    private StoryWriterResponse toWriterResponse(Profile profile) {
        if (profile == null) {
            return null;
        }
        StoryWriterResponse writerResponse = new StoryWriterResponse();
        writerResponse.setId(profile.getId());
        writerResponse.setFirstName(profile.getFirstName());
        writerResponse.setLastName(profile.getLastName());
        return writerResponse;
    }

}
