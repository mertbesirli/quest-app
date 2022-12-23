package com.questApp.questapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.questApp.questapp.entity.Post;


public interface PostRepository extends JpaRepository<Post, Long> {

}
