package com.example.soundcloudfinalprojectittalentss15.model.DTOs.commentDTOs;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyInfoDTO {
    private int ownerId;
    private String content;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime postedAt;
    private int parentCommentId;
}
