package com.example.ssa.ssa.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

@Data
public class PlanCreateForm {
    @NotNull
    private Long roomId;
    @NotEmpty
    @Length(max = 40)
    private String title;
    @NotNull
    private String startDate;
    @NotNull
    private String startTime;
    @NotNull
    private String endDate;
    @NotNull
    private String endTime;
    @Length(max = 100)
    private String memo;
}
