package com.stories.stories.controllers;


import com.stories.stories.services.StoryService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stories")
public class StoryController {
    private final StoryService storyService;

    public StoryController(StoryService storyService) {

        this.storyService = storyService;
    }


}
