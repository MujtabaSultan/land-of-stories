package com.stories.stories.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class StorySummaryResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime publishDate;
    private double averageRating;
    private int ratingsCount;
    private StoryWriterResponse writer;
}
