package com.stories.stories.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
public class Story {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private LocalDateTime publishDate;


    @OneToMany(mappedBy = "story" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Rating> ratings;

    @OneToMany(mappedBy = "story" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comment;

    @OneToMany(mappedBy = "story" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Report> reports;

    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile profile;



    public double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }
        return ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);
    }
}
