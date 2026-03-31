package com.stories.stories.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
@Data
public class UserDto {

    private Long id;

    private String userName;
    private String emailAddress;
    private String password;

    private Profile profile;

    private boolean accountVerified;
    private boolean isActivated;
    private Long id;

    private String firstName;

    private String lastName;

    private String profileDescription;
    private User user;
    private Image image;
    private List<Report> report;
    private List<Story> stories;
    private List<Comment> comment;



}
