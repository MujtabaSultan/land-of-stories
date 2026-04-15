package com.stories.stories.models;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class StoryDetailsResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime publishDate;
    private double averageRating;
    private int ratingsCount;
    private StoryWriterResponse writer;
    private List<StoryCommentResponse> comments;
}
