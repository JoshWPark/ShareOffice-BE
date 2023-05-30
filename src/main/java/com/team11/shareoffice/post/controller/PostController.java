package com.team11.shareoffice.post.controller;

import com.team11.shareoffice.global.dto.ResponseDto;
import com.team11.shareoffice.global.security.UserDetailsImpl;
import com.team11.shareoffice.post.dto.PostRequestDto;
import com.team11.shareoffice.post.dto.PostResponseDto;
import com.team11.shareoffice.post.dto.PostUpdateRequestDto;
import com.team11.shareoffice.post.entity.Post;
import com.team11.shareoffice.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

    private final PostService postService;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE, MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public ResponseDto<Long> createPost(@RequestPart PostRequestDto postRequestDto,
                                        @RequestPart(value = "imageFile", required = false) MultipartFile image,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException {
        return postService.createPost(postRequestDto, image, userDetails.getMember());
    }

    @PutMapping("/{id}")
    public ResponseDto<Long> updatePost(@PathVariable Long id,
                                        @RequestPart(value = "postRequestDto", required = false) PostUpdateRequestDto postRequestDto,
                                        @RequestPart(value = "imageFile", required = false) MultipartFile image,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) throws IOException{
        return postService.updatePost(id, postRequestDto, image, userDetails.getMember());
    }

    @DeleteMapping("/{id}")
    public ResponseDto<Long> deletePost(@PathVariable Long id,@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return postService.deletePost(id, userDetails.getMember());
    }


    //상세 게시글 조회
    @GetMapping("/{id}")
    public ResponseDto<PostResponseDto> GetPost(@PathVariable Long id) {
        if (isAuthenticated()) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return postService.getPost(id, userDetails.getMember());
        }
        return postService.getPost(id,null);
    }

    //로그인 여부 확인
    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }
}
