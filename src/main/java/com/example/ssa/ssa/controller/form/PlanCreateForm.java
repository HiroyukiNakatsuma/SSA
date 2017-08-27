package com.example.ssa.ssa.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class PlanCreateForm {
    @NotNull
    private Long roomId;
    @NotEmpty
    @Max(value = 40)
    private String title;
    @NotNull
    private LocalDateTime startDateTime;
    @NotNull
    private LocalDateTime endDateTime;
    @Max(value = 100)
    private String memo;
}
