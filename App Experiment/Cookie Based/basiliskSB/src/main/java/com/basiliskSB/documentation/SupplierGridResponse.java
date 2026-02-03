package com.basiliskSB.documentation;
import com.basiliskSB.dto.supplier.SupplierGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SupplierGridResponse {
    private List<SupplierGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
