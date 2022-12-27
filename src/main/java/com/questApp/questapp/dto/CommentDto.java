package com.questApp.questapp.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Long id;
    private Long postId;
    private Long userId;
    private String text;

}
