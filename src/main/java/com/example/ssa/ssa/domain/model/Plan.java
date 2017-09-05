package com.example.ssa.ssa.domain.model;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class Plan {
    private Long planId;
    private Long roomId;
    private String title;
    private LocalDate startDate;
    private LocalTime startTime;
    private LocalDate endDate;
    private LocalTime endTime;
    private String memo;
}
