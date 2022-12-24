package com.questApp.questapp.service.impl;


import com.questApp.questapp.dto.PostDto;
import com.questApp.questapp.entity.Post;
import com.questApp.questapp.entity.User;
import com.questApp.questapp.repository.PostRepository;
import com.questApp.questapp.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private UserServiceImpl userService;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, UserServiceImpl userService, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Post> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()) {
            list = postRepository.findByUserId(userId.get());
        }
        else {
            list = postRepository.findAll();
        }
        return list;
    }

    @Override
    public Post createPost(PostDto postRequest) {
        User user = userService.getOneUser(postRequest.getUserId());
        if(user == null){
            return null;
        }
        // convert DTO to entity
        Post toSave = modelMapper.map(postRequest, Post.class);

        return postRepository.save(toSave);
    }

    @Override
    public Post getOnePost(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    @Override
    public Post updatePost(Long postId, PostDto postDto) {
        Optional<Post> post = postRepository.findById(postId);

        if(post.isEmpty()){
          return null;
        }
        Post toUpdate = post.get();
        toUpdate.setText(postDto.getText());
        toUpdate.setTitle(postDto.getTitle());
        postRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public void deletePost(Long postId) {
        try {
            postRepository.deleteById(postId);
        }catch (EmptyResultDataAccessException e){
            System.out.println("Post" + postId +" does not exist"); // if you want, make log.
        }
    }
}
