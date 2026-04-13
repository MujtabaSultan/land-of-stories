package com.stories.stories.controllers;


import com.stories.stories.models.Story;
import com.stories.stories.services.StoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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



}
