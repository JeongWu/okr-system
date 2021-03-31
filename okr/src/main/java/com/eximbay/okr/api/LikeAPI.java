package com.eximbay.okr.api;

import com.eximbay.okr.dto.like.LikeRequestDto;
import com.eximbay.okr.service.Interface.ILikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/likes")
public class LikeAPI {

    private final ILikeService likeService;

    @PostMapping
    public ResponseEntity<Void> handleLikeRequest(@RequestBody LikeRequestDto likeRequest){
        likeService.handleLikeRequest(likeRequest);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }
}
