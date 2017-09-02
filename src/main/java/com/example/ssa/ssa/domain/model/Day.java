package com.example.ssa.ssa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Day {
    private LocalDate date;
    private boolean todayFlag;
    private List<Plan> plans;
}
