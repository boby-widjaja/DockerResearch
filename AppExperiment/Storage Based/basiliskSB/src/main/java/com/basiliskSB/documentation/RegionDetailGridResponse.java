package com.basiliskSB.documentation;

import com.basiliskSB.dto.region.RegionDetailGridDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class RegionDetailGridResponse {
    private RegionHeaderResponse header;
    private List<RegionDetailGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
