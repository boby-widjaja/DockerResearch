package com.basiliskSB.dto.dashboard;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
public class SalesmenComparisonDTO {
    private List<String> salesmenNames = new LinkedList<>();
    private List<Double> salesmenPerformances = new LinkedList<>();

    public SalesmenComparisonDTO(List<Object> data) {
        setSalesmenNames(data);
        setSalesmanPerformances(data);
    }

    private void setSalesmenNames(List<Object> data){
        for(var datum : data){
            var salesmanName = MapperHelper.getStringField(datum, 0);
            salesmenNames.add(salesmanName);
        }
    }

    private void setSalesmanPerformances(List<Object> data){
        for(var datum : data){
            var salesmanPerformance = MapperHelper.getBigDecimalField(datum, 1).doubleValue();
            salesmenPerformances.add(salesmanPerformance);
        }
    }
}
