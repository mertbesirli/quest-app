package com.questApp.questapp.controller;


import com.questApp.questapp.dto.CommentDto;
import com.questApp.questapp.dto.PostDto;
import com.questApp.questapp.entity.Comment;
import com.questApp.questapp.service.impl.CommentServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
public class CommentController {

    private final ModelMapper modelMapper;
    private final CommentServiceImpl commentService;

    public CommentController(CommentServiceImpl commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<CommentDto> getAllComments(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        List<Comment> list = commentService.getAllComments(userId, postId);

        return modelMapper.map(list, new TypeToken<List<PostDto>>(){}.getType());
    }

    @PostMapping
    public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto){
        Comment comment = commentService.createComment(commentDto);

        // convert entity to DTO
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);

        return new ResponseEntity<>(commentResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{commentId}")
    public ResponseEntity<CommentDto> getOneComment(@PathVariable Long commentId){
        Comment comment = commentService.getOneComment(commentId);

        // convert entity to DTO
        CommentDto commentResponse = modelMapper.map(comment, CommentDto.class);

        return ResponseEntity.ok().body(commentResponse);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto){
        Comment comment = commentService.updateComment(commentId, commentDto);

        // convert entity to DTO
        CommentDto commentRequest = modelMapper.map(comment, CommentDto.class);

        return ResponseEntity.ok().body(commentRequest);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable Long commentId){
        commentService.deleteComment(commentId);
    }

}
