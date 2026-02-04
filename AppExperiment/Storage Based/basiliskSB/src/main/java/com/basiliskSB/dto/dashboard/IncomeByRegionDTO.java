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
public class IncomeByRegionDTO {
    private List<String> cities = new LinkedList<>();
    private List<Double> incomes = new LinkedList<>();
    public IncomeByRegionDTO(List<Object> data){
        setCities(data);
        setIncomes(data);
    }

    private void setCities(List<Object> data){
        for(var datum : data){
            var city = MapperHelper.getStringField(datum, 0);
            cities.add(city);
        }
    }

    private void setIncomes(List<Object> data){
        for(var datum : data){
            var income = MapperHelper.getBigDecimalField(datum, 1).doubleValue();
            incomes.add(income);
        }
    }
}
