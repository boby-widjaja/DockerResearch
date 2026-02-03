package com.basiliskSB.service.implementation;
import com.basiliskSB.dao.CustomerRepository;
import com.basiliskSB.dao.OrderRepository;
import com.basiliskSB.dao.ProductRepository;
import com.basiliskSB.dao.SalesmanRepository;
import com.basiliskSB.service.abstraction.DashboardService;
import com.basiliskSB.utility.MapperHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.relational.core.sql.In;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Scope("singleton")
@Service("dashboardService")
public class DashboardServiceImplementation implements DashboardService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private SalesmanRepository salesmanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Object> getAnnualIncome(Integer year) {
         return orderRepository.executeAnnualIncome(year);
    }

    @Override
    public Double getTotalIncome(List<Object> data) {
        var totalIncome = new BigDecimal(0.0);
        for(var datum : data){
            var periodIncome = MapperHelper.getBigDecimalField(datum, 2);
            totalIncome = totalIncome.add(periodIncome);
        }
        return totalIncome.doubleValue();
    }

    @Override
    public Double getFluctuation(List<Object> data, Integer year) {
        var totalIncome = getTotalIncome(data);
        var lastYearTotalIncome = getTotalIncome(getAnnualIncome(year-1));
        return totalIncome - lastYearTotalIncome;
    }

    @Override
    public String getHighestPeriod(List<Object> data) {
        var highestPeriod = "No Record";
        var highestIncome = 0.0;
        for(var datum : data){
            var periodIncome = MapperHelper.getBigDecimalField(datum, 2).doubleValue();
            if(periodIncome > highestIncome){
                highestIncome = periodIncome;
                highestPeriod = MapperHelper.getStringField(datum, 1);
            }
        }
        return highestPeriod;
    }

    @Override
    public String getLowestPeriod(List<Object> data) {
        var lowestPeriod = MapperHelper.getStringField(data.get(0), 1);;
        var lowestIncome = MapperHelper.getBigDecimalField(data.get(0), 2).doubleValue();
        for(var datum : data){
            var periodIncome = MapperHelper.getBigDecimalField(datum, 2).doubleValue();
            if(periodIncome < lowestIncome){
                lowestIncome = periodIncome;
                lowestPeriod = MapperHelper.getStringField(datum, 1);
            }
        }
        return lowestPeriod;
    }

    @Override
    public List<Object> getSalesmenComparison(Integer year) {
        return orderRepository.executeSalesmenComparison(year);
    }

    @Override
    public List<Object> getSalesmenPerformance(String employeeNumber, Integer year) {
        return orderRepository.executeSalesmanPerformance(employeeNumber, year);
    }

    @Override
    public List<Object> getCustomerActivity(Long customerId, Integer year) {
        return orderRepository.executeCustomerActivity(customerId, year);
    }

    @Override
    public List<Object> getCategoryPopularity(Integer year) {
        return productRepository.executeCategoryPopularity(year);
    }

    @Override
    public List<Object> getIncomeByRegion(Integer year) {
        return orderRepository.executeIncomeByRegion(year);
    }

    @Override
    public List<Object> getCustomerInterest(Long customerId, Integer year) {
        return orderRepository.executeCustomerInterest(customerId, year);
    }

    @Override
    public List<Integer> getOrderYears() {
        return orderRepository.getOrderYears();
    }

    @Override
    public List<Object> getSalesmanDropdown() {
        return salesmanRepository.findAllOrderByFirstName();
    }

    @Override
    public List<Object> getCustomerDropdown() {
        return customerRepository.findAllOrderByCompanyName();
    }
}
