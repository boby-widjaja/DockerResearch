package com.basiliskSB.service.abstraction;

import java.util.List;

public interface DashboardService {
    public List<Object> getAnnualIncome(Integer year);
    public Double getTotalIncome(List<Object> data);
    public Double getFluctuation(List<Object> data, Integer year);
    public String getHighestPeriod(List<Object> data);
    public String getLowestPeriod(List<Object> data);
    public List<Object> getSalesmenComparison(Integer year);
    public List<Object> getSalesmenPerformance(String employeeNumber, Integer year);
    public List<Object> getCustomerActivity(Long customerId, Integer year);
    public List<Object> getCategoryPopularity(Integer year);
    public List<Object> getIncomeByRegion(Integer year);
    public List<Object> getCustomerInterest(Long customerId, Integer year);
    public List<Integer> getOrderYears();
    public List<Object> getSalesmanDropdown();
    public List<Object> getCustomerDropdown();
}
