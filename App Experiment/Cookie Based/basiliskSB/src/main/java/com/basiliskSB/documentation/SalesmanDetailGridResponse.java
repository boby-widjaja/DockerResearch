package com.basiliskSB.documentation;
import com.basiliskSB.dto.salesman.SalesmanDetailGridDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class SalesmanDetailGridResponse {
    private SalesmanHeaderResponse header;
    private List<SalesmanDetailGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
