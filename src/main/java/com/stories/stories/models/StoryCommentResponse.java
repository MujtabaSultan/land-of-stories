package com.stories.stories.models;

import lombok.Data;

@Data
public class StoryCommentResponse {
    private Long id;
    private String content;
    private StoryWriterResponse writer;
}
