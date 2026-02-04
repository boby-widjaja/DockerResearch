package com.basiliskSB.documentation;
import com.basiliskSB.dto.order.OrderGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderGridResponse {
    private List<OrderGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
