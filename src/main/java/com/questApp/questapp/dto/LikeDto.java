package com.questApp.questapp.dto;

import lombok.Data;

@Data
public class LikeDto {
    private Long id;
    private Long postId;
    private Long userId;
}
