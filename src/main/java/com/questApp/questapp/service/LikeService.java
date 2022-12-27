package com.questApp.questapp.service;

import com.questApp.questapp.dto.LikeDto;
import com.questApp.questapp.entity.Like;

import java.util.List;
import java.util.Optional;

public interface LikeService {

    List<Like> getAllLikes(Optional<Long> userId, Optional<Long> postId);

    Like createLike(LikeDto likeDto);

    Like getOneLike(Long likeId);

    void deleteLike(Long likeId, LikeDto likeDto);
}
