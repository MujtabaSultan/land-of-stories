package com.stories.stories.controllers;


import com.stories.stories.models.Story;
import com.stories.stories.models.StoryRequest;
import com.stories.stories.services.StoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public List<Story> listStories() {

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

}
