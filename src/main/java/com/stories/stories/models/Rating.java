package com.stories.stories.models;

import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Rating {


    private long id;

    private int score;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;

    @ManyToOne
    @JoinColumn(name = "story_id")
    private Story story;
}
