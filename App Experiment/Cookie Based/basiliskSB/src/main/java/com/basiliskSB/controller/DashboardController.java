package com.basiliskSB.controller;
import com.basiliskSB.dto.dashboard.*;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.service.abstraction.DashboardService;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.viewModel.abstraction.DropdownViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService service;

    @GetMapping("/index")
    public String index(Model model){
        var orderYears = service.getOrderYears();
        var yearDropdown = new ArrayList<DropdownDTO>();
        for(var orderYear : orderYears){
            yearDropdown.add(new DropdownDTO(orderYear, orderYear.toString()));
        }
        model.addAttribute("salesmenDropdown",
            DropdownViewModel.getDropdownDTO(service.getSalesmanDropdown()));
        model.addAttribute("customerDropdown",
            DropdownViewModel.getDropdownDTO(service.getCustomerDropdown()));
        model.addAttribute("breadCrumbs", "Dashboard");
        model.addAttribute("yearDropdown", yearDropdown);
        return "dashboard/dashboard-index";
    }

    @GetMapping("/annualIncome/{year}")
    public ResponseEntity<Object> annualIncome(@PathVariable(required = true) Integer year){
        var data = service.getAnnualIncome(year);
        var totalIncome = service.getTotalIncome(data);
        var fluctuation = service.getFluctuation(data, year);
        var highestPeriod = service.getHighestPeriod(data);
        var lowestPeriod = service.getLowestPeriod(data);
        var dto = new AnnualIncomeDTO(data, year, totalIncome, fluctuation, highestPeriod, lowestPeriod);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/salesmenComparison/{year}")
    public ResponseEntity<Object> salesmenComparison(@PathVariable(required = true) Integer year){
        var data = service.getSalesmenComparison(year);
        var dto = new SalesmenComparisonDTO(data);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/salesmanPerformance")
    public ResponseEntity<Object> salesmanPerformance(@RequestParam(required = true) String employeeNumber, @RequestParam(required = true) Integer year){
        var data = service.getSalesmenPerformance(employeeNumber, year);
        var performances = new double[12];
        for(var datum : data){
            var periodIncome = MapperHelper.getBigDecimalField(datum, 2).doubleValue();
            var periodMonth = MapperHelper.getIntegerField(datum, 0);
            performances[periodMonth-1] = periodIncome;
        }
        return ResponseEntity.status(200).body(performances);
    }

    @GetMapping("/categoryPopularity/{year}")
    public ResponseEntity<Object> categoryPopularity(@PathVariable(required = true) Integer year){
        var data = service.getCategoryPopularity(year);
        var dto = new CategoryPopularityDTO(data);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/incomeByRegion/{year}")
    public ResponseEntity<Object> incomeByRegion(@PathVariable(required = true) Integer year){
        var data = service.getIncomeByRegion(year);
        var dto = new IncomeByRegionDTO(data);
        return ResponseEntity.status(200).body(dto);
    }

    @GetMapping("/customerActivity")
    public ResponseEntity<Object> customerActivity(@RequestParam(required = true) Long customerId, @RequestParam(required = true) Integer year){
        var data = service.getCustomerActivity(customerId, year);
        var incomes = new double[12];
        for(var datum : data){
            var periodIncome = MapperHelper.getBigDecimalField(datum, 2).doubleValue();
            var periodMonth = MapperHelper.getIntegerField(datum, 0);
            incomes[periodMonth-1] = periodIncome;
        }
        return ResponseEntity.status(200).body(incomes);
    }

    @GetMapping("/customerInterest")
    public ResponseEntity<Object> customerInterest(@RequestParam(required = true) Long customerId, @RequestParam(required = true) Integer year){
        var data = service.getCustomerInterest(customerId, year);
        var dto = new CustomerInterestDTO(data);
        return ResponseEntity.status(200).body(dto);
    }
}
