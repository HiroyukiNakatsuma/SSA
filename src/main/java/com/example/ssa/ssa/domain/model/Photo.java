package com.example.ssa.ssa.domain.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Photo {
    private long photoId;
    private long roomId;
    private String imagePath;
    private long postedAccountId;
    private LocalDateTime createdAt;
}
