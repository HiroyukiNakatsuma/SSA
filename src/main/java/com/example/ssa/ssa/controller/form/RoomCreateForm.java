package com.example.ssa.ssa.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class RoomCreateForm {
    @NotEmpty
    private String roomName;
}
