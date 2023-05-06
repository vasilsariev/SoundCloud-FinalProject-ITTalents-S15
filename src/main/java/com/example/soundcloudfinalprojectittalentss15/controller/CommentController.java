package com.example.soundcloudfinalprojectittalentss15.controller;

import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CommentInfoDTO;
import com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs.CreationCommentDTO;
import com.example.soundcloudfinalprojectittalentss15.model.exceptions.BadRequestException;
import com.example.soundcloudfinalprojectittalentss15.services.CommentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController extends AbstractController{

    @Autowired
    private CommentService commentService;

    @GetMapping("/track/{trackId}/comments")
    public Page<CommentInfoDTO> getSongComments(@PathVariable int trackId, @RequestParam(name = "page", defaultValue = "0") int pageNumber) {
        return commentService.getAllByTrack(trackId, pageNumber);

    }

    @PostMapping("/track/{trackId}/comments")
    public CommentInfoDTO createComment(@PathVariable int trackId, @RequestBody CreationCommentDTO creationCommentDTO, HttpSession s) {
        int userId = getLoggedId(s);
        return commentService.createComment(trackId, creationCommentDTO.getContent(), userId);
    }

    @GetMapping("/users/{userId}/comments")
    public List<CommentInfoDTO> getAllCommentsByUser(@PathVariable int userId, HttpSession s) {
        getLoggedId(s);
        return commentService.getAllByUser(userId);
    }

    @PostMapping("/track/{trackId}/comments/{commentId}")
    public CommentInfoDTO replyToComment(@PathVariable int trackId, @PathVariable int commentId, @RequestBody CreationCommentDTO creationCommentDTO,  HttpSession s) {
        int userId = getLoggedId(s);
        return commentService.createReply(trackId, commentId, creationCommentDTO.getContent(), userId);
    }

    @DeleteMapping("/track/{trackId}/comments/{commentId}")
    public CommentInfoDTO deleteComment(@PathVariable int trackId, @PathVariable int commentId, HttpSession s) {
        int userId = getLoggedId(s);
        return commentService.deleteComment(trackId, commentId, userId);
    }


}
