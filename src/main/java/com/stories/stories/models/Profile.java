package com.stories.stories.models;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString()
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "profiles")
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    private String profileDescription;
    @JsonIgnore
    @OneToOne(mappedBy = "profile",fetch = FetchType.EAGER)
    private User user;

    @OneToOne(cascade = CascadeType.ALL)
    private Image image;

    @JsonIgnore
    @OneToMany(mappedBy = "profile" ,orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Report> report;

    @OneToMany(mappedBy = "profile" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Story> stories;

    @OneToMany(mappedBy = "profile" , orphanRemoval = true , fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Comment> comment;

}