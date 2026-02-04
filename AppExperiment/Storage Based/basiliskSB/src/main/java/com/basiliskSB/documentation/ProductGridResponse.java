package com.basiliskSB.documentation;
import com.basiliskSB.dto.product.ProductGridDTO;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class ProductGridResponse {
    private List<ProductGridDTO> grid;
    private Integer page;
    private Integer totalPages;
}
