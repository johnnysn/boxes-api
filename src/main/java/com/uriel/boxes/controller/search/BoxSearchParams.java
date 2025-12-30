package com.uriel.boxes.controller.search;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BoxSearchParams {
    private String name;
    private String description;
    private Boolean or;
    private Long userId;
}
