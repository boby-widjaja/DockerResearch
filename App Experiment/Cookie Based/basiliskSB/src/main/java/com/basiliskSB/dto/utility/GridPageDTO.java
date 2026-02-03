package com.basiliskSB.dto.utility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GridPageDTO<T> {
    private List<T> grid;
    private Integer page;
    private Integer totalPages;
}
