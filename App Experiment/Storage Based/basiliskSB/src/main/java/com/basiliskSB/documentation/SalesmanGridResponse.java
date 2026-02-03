package com.basiliskSB.documentation;
import com.basiliskSB.dto.salesman.SalesmanGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SalesmanGridResponse {
    private List<SalesmanGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
