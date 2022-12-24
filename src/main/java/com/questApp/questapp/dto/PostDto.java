package com.questApp.questapp.dto;

import lombok.Data;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String text;

    private Long userId;

}
