package com.basiliskSB.dto.dashboard;
import com.basiliskSB.utility.MapperHelper;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class AnnualIncomeDTO {
    private double[] incomes = new double[12];
    private Integer year;
    private Double totalIncome;
    private Double fluctuation;
    private String highestPeriod;
    private String lowestPeriod;

    public AnnualIncomeDTO(List<Object> data, Integer year, Double totalIncome, Double fluctuation, String highestPeriod, String lowestPeriod) {
        setIncomes(data);
        this.year = year;
        this.totalIncome = totalIncome;
        this.fluctuation = fluctuation;
        this.highestPeriod = highestPeriod;
        this.lowestPeriod = lowestPeriod;
    }

    private void setIncomes(List<Object> data){
        for(var datum : data){
            var periodIncome = MapperHelper.getBigDecimalField(datum, 2).doubleValue();
            var periodMonth = MapperHelper.getIntegerField(datum, 0);
            incomes[periodMonth-1] = periodIncome;
        }
    }
}
