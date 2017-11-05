package com.example.ssa.ssa.controller.form;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

@Data
public class RoomCreateForm {
    @NotEmpty(message = "{room.roomName.notEmpty}")
    @Length(max = 40, message = "{room.roomName.max}")
    private String roomName;
}
