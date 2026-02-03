package com.basiliskSB.documentation;
import com.basiliskSB.dto.delivery.DeliveryGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DeliveryGridResponse {
    private List<DeliveryGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
