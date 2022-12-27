package com.questApp.questapp.service.impl;

import com.questApp.questapp.dto.CommentDto;
import com.questApp.questapp.entity.Comment;
import com.questApp.questapp.entity.Post;
import com.questApp.questapp.entity.User;
import com.questApp.questapp.repository.CommentRepository;
import com.questApp.questapp.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserServiceImpl userService;
    private final PostServiceImpl postService;
    private final ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepository, UserServiceImpl userService, PostServiceImpl postService, ModelMapper modelMapper) {
        this.commentRepository = commentRepository;
        this.userService = userService;
        this.postService = postService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId) {
        List<Comment> comments;

        if(userId.isPresent() && postId.isPresent()){
            comments = commentRepository.findByUserIdAndPostId(userId.get(), postId.get());
        }
        else if(userId.isPresent()){
            comments = commentRepository.findByUserId((userId.get()));
        }
        else if(postId.isPresent()){
            comments = commentRepository.findByPostId(postId.get());
        }
        else{
            comments = commentRepository.findAll();
        }
        return comments;
    }

    @Override
    public Comment createComment(CommentDto commentRequest) {
        User user = userService.getOneUser(commentRequest.getUserId());
        Post post = postService.getOnePost(commentRequest.getPostId());

        if(user == null || post == null){
            return null;
        }

        // convert DTO to entity
        Comment toSave = modelMapper.map(commentRequest, Comment.class);

        return commentRepository.save(toSave);
    }

    @Override
    public Comment getOneComment(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public Comment updateComment(Long commentId, CommentDto commentDto) {
        Optional<Comment> comment = commentRepository.findById(commentId);

        if(comment.isEmpty()){
            return null;
        }
        Comment toUpdate = comment.get();
        toUpdate.setText(commentDto.getText());
        commentRepository.save(toUpdate);
        return toUpdate;
    }

    @Override
    public void deleteComment(Long commentId) {
        try {
            commentRepository.deleteById(commentId);
        }catch (EmptyResultDataAccessException e){
            System.out.println("Comment" + commentId +" does not exist"); // if you want, make log.
        }
    }


}
