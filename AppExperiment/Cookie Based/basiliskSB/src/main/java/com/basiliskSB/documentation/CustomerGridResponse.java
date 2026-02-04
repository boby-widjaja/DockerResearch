package com.basiliskSB.documentation;

import com.basiliskSB.dto.customer.CustomerGridDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CustomerGridResponse {
    private List<CustomerGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
