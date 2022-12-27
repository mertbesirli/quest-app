package com.questApp.questapp.service;

import com.questApp.questapp.dto.CommentDto;
import com.questApp.questapp.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    List<Comment> getAllComments(Optional<Long> userId, Optional<Long> postId);

    Comment createComment(CommentDto commentRequest);

    Comment getOneComment(Long commentId);

    Comment updateComment(Long commentId, CommentDto commentDto);

    void deleteComment(Long commentId);
}
