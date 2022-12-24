package com.questApp.questapp.service;

import com.questApp.questapp.dto.PostDto;
import com.questApp.questapp.entity.Post;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PostService {
    List<Post> getAllPosts(Optional<Long> userId);

    Post createPost(PostDto postRequest);

    Post getOnePost(Long postId);

    Post updatePost(Long postId, PostDto postDto);

    void deletePost(Long postId);
}
