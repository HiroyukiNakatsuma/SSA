package com.example.ssa.ssa.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Month;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CalendarWithPlan {
    private Month month;
    private List<Day> days;
}
