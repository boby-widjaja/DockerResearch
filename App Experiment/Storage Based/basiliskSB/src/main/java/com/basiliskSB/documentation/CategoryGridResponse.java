package com.basiliskSB.documentation;

import com.basiliskSB.dto.category.CategoryGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CategoryGridResponse {
    private List<CategoryGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
