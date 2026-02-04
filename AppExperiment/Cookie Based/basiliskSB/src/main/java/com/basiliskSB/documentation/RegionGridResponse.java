package com.basiliskSB.documentation;
import com.basiliskSB.dto.region.RegionGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RegionGridResponse {
    private List<RegionGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
