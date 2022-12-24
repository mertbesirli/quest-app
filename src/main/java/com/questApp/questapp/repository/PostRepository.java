package com.questApp.questapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.questApp.questapp.entity.Post;

import java.util.Collection;
import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findByUserId(Long userId);
}
