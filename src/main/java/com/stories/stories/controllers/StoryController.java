package com.stories.stories.controllers;


import com.stories.stories.models.*;
import com.stories.stories.services.StoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stories")
public class StoryController {
    private final StoryService storyService;

    public StoryController(StoryService storyService) {

        this.storyService = storyService;
    }
    @GetMapping
    public List<StorySummaryResponse> listStories() {

        return storyService.allStories();
    }

    @PostMapping
    public Story createStory(@RequestBody StoryRequest request) {

         return storyService.createStory(request);
    }

    @DeleteMapping("/{storyId}")
    public void deleteStory(@PathVariable Long storyId) {
        storyService.deleteStory(storyId);
    }


    @PostMapping("/{storyId}/comments")
    public void addComment(
            @PathVariable Long storyId,
            @RequestBody CommentRequest request
    ) {
     storyService.addComment(storyId, request);
    }
    @PostMapping("/{storyId}/rating")
    public void rateStory(
            @PathVariable Long storyId,
            @RequestBody Rating request
    ) {
        storyService.rateStory(storyId, request.getScore());
    }
    @GetMapping("{storyId}")
    public StoryDetailsResponse getStory(@PathVariable Long storyId){
        return storyService.singleStory(storyId);
    }

}
