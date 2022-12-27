package com.questApp.questapp.controller;


import com.questApp.questapp.dto.LikeDto;
import com.questApp.questapp.entity.Like;
import com.questApp.questapp.service.impl.LikeServiceImpl;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
public class LikeController {

    private final ModelMapper modelMapper;

    private final LikeServiceImpl likeService;

    public LikeController(ModelMapper modelMapper, LikeServiceImpl likeService) {
        this.modelMapper = modelMapper;
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeDto> getAllLikes(@RequestParam Optional<Long> userId, @RequestParam Optional<Long> postId){
        List<Like> list = likeService.getAllLikes(userId, postId);

        return modelMapper.map(list, new TypeToken<List<LikeDto>>(){}.getType());
    }

    @PostMapping
    public ResponseEntity<LikeDto> createLike(@RequestBody LikeDto likeDto){
        Like like = likeService.createLike(likeDto);

        // convert entity to DTO
        LikeDto likeResponse = modelMapper.map(like, LikeDto.class);

        return new ResponseEntity<>(likeResponse, HttpStatus.CREATED);
    }

    @GetMapping("/{likeId}")
    public ResponseEntity<LikeDto> getOneLike(@PathVariable Long likeId){
        Like like = likeService.getOneLike(likeId);

        // convert entity to DTO
        LikeDto likeResponse = modelMapper.map(like, LikeDto.class);

        return ResponseEntity.ok().body(likeResponse);
    }

    @DeleteMapping("/{likeId}")
    public void deleteLike(@PathVariable Long likeId, @RequestBody LikeDto likeDto){
        likeService.deleteLike(likeId, likeDto);
    }


}
