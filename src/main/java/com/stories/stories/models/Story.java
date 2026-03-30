package com.stories.stories.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Data
public class Story {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private String content;

    private LocalDateTime publishDate;


    @OneToMany(mappedBy = "story" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Story> stories;

    @OneToMany(mappedBy = "story" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comment;

    @OneToMany(mappedBy = "story" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Report> reports;




}
