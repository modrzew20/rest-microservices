package com.example.smartcode.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PaginationParam {

    private int page = 0;
    private int size = 100;
}
