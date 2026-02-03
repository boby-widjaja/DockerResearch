package com.basiliskSB.dto.dashboard;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
public class CategoryPopularityDTO {
    private List<String> categoryNames = new LinkedList<>();
    private List<Integer> totalQuantity = new LinkedList<>();
    public CategoryPopularityDTO(List<Object> data){
        setCategoryNames(data);
        setTotalQuantity(data);
    }

    private void setCategoryNames(List<Object> data){
        for(var datum : data){
            var categoryName = MapperHelper.getStringField(datum, 1);
            categoryNames.add(categoryName);
        }
    }

    private void setTotalQuantity(List<Object> data){
        for(var datum : data){
            var quantity = MapperHelper.getIntegerField(datum, 2);
            totalQuantity.add(quantity);
        }
    }
}
