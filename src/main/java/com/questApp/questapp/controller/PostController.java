package com.questApp.questapp.controller;


import com.questApp.questapp.dto.PostDto;
import com.questApp.questapp.entity.Post;
import com.questApp.questapp.service.impl.PostServiceImpl;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.TypeToken;


import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private ModelMapper modelMapper;

    private PostServiceImpl postService;

    public PostController(ModelMapper modelMapper, PostServiceImpl postService) {
        this.modelMapper = modelMapper;
        this.postService = postService;
    }

    @GetMapping
    public List<PostDto> getAllPosts(@RequestParam Optional<Long> userId){
        List<Post> list = postService.getAllPosts(userId);
        return modelMapper.map(list, new TypeToken<List<PostDto>>(){}.getType());
    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto){
        Post post = postService.createPost(postDto);

        // convert entity to DTO
        PostDto postResponse = modelMapper.map(post, PostDto.class);

        return new ResponseEntity<PostDto>(postResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> getOnePost(@PathVariable Long postId){
        Post post = postService.getOnePost(postId);

        // convert entity to DTO
        PostDto postResponse = modelMapper.map(post, PostDto.class);

        return ResponseEntity.ok().body(postResponse);
    }

    @PutMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto){
        Post post = postService.updatePost(postId, postDto);
        // convert entity to DTO
        PostDto postResponse = modelMapper.map(post, PostDto.class);

        return ResponseEntity.ok().body(postResponse);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePost(postId);
    }

}
