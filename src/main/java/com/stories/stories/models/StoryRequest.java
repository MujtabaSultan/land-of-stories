package com.stories.stories.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class StoryRequest {

    private String title;
    private String content;
}
