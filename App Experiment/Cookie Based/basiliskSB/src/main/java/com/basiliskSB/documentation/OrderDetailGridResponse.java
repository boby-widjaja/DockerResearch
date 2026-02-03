package com.basiliskSB.documentation;
import com.basiliskSB.dto.order.OrderDetailGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDetailGridResponse {
    private OrderHeaderResponse header;
    private List<OrderDetailGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
