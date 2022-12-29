package com.questApp.questapp.service.impl;

import com.questApp.questapp.dto.LikeDto;
import com.questApp.questapp.entity.Like;
import com.questApp.questapp.entity.Post;
import com.questApp.questapp.entity.User;
import com.questApp.questapp.repository.LikeRepository;
import com.questApp.questapp.service.LikeService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private LikeRepository likeRepository;
    private ModelMapper modelMapper;

    private UserServiceImpl userService;

    private PostServiceImpl postService;


    @Override
    public List<Like> getAllLikes(Optional<Long> userId, Optional<Long> postId) {
        List<Like> likes;
        if(userId.isPresent() && postId.isPresent()){
            likes = likeRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent()){
            likes = likeRepository.findByUserId(userId);
        }
        else if(postId.isPresent()){
            likes = likeRepository.findByPostId(postId);
        }
        else{
            likes = likeRepository.findAll();
        }
        return likes;
    }

    @Override
    public Like createLike(LikeDto likeDto) {
        User user = userService.getOneUser(likeDto.getUserId());
        Post post = postService.getOnePost(likeDto.getPostId());

        if(user == null || post == null){
            return null;
        }

        // convert DTO to entity
        Like toSave = modelMapper.map(likeDto, Like.class);

        return likeRepository.save(toSave);
    }

    @Override
    public Like getOneLike(Long likeId) {
        return likeRepository.findById(likeId).orElse(null);
    }

    @Override
    public void deleteLike(Long likeId, LikeDto likeDto) {
        try{
            likeRepository.deleteById(likeId);
        }catch (EmptyResultDataAccessException e){
            System.out.println("Like" + likeId +" does not exist"); // if you want, make log.
        }
    }
}
