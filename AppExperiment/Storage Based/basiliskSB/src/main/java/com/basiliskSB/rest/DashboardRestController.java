package com.basiliskSB.rest;
import com.basiliskSB.documentation.CategoryGridResponse;
import com.basiliskSB.dto.dashboard.*;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.service.abstraction.DashboardService;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@CrossOrigin
@RestController
@RequestMapping("/api/dashboard")
public class DashboardRestController extends AbstractRestController{

    @Autowired
    private DashboardService service;

    @Operation(
        summary = "Mendapatkan data 1 tahun untuk diagram fluktuasi pendapatan dari bulan ke bulan.",
        description = "Incomes collection akan secara sequence berurutan dari January ke December."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = AnnualIncomeDTO.class )
        )}
    )
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

    @Operation(
        summary = "Mendapatkan hasil penjualan setiap salesman dalam 1 tahun.",
        description = "Hasilnya digunakan untuk pie chart, hasil akan dibagi menjadi 2 collection, nama salesman dan hasil penjualan dalam sequence yang sama."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = SalesmenComparisonDTO.class )
        )}
    )
    @GetMapping("/salesmenComparison/{year}")
    public ResponseEntity<Object> salesmenComparison(@PathVariable(required = true) Integer year){
        var data = service.getSalesmenComparison(year);
        var dto = new SalesmenComparisonDTO(data);
        return ResponseEntity.status(200).body(dto);
    }

    @Operation(
        summary = "Mendapatkan hasil penjualan 1 orang salesman dalam 1 tahun.",
        description = "Hasil di tampilkan untuk line atau bar chart, respectively dalam sequence January ke December."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = Double[].class )
        )}
    )
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

    @Operation(
        summary = "Mendapatkan hasil penjualan quantity product untuk setiap categorynya dalam satu tahun.",
        description = "Mendapatkan deret nama category beserta total quantitynya respectively."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = CategoryPopularityDTO.class )
        )}
    )
    @GetMapping("/categoryPopularity/{year}")
    public ResponseEntity<Object> categoryPopularity(@PathVariable(required = true) Integer year){
        var data = service.getCategoryPopularity(year);
        var dto = new CategoryPopularityDTO(data);
        return ResponseEntity.status(200).body(dto);
    }

    @Operation(
        summary = "Mendapatkan total income dalam satu tahun yang dibagi dalam setiap region.",
        description = "Mendapatkan collection kota dan incomes respectively dalam sequence."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = IncomeByRegionDTO.class )
        )}
    )
    @GetMapping("/incomeByRegion/{year}")
    public ResponseEntity<Object> incomeByRegion(@PathVariable(required = true) Integer year){
        var data = service.getIncomeByRegion(year);
        var dto = new IncomeByRegionDTO(data);
        return ResponseEntity.status(200).body(dto);
    }


    @Operation(
        summary = "Mendapatkan total pendapatan untuk 1 cumstomer dan pada 1 tahun tertentu.",
        description = "Mendapatkan collection pendapatan pada setiap bulannya sequentially."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = Double[].class )
        )}
    )
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

    @Operation(
        summary = "Mendapatkan kecenderungan belanja seorang customer di dalam tahun tertentu terhadap category-category product.",
        description = "Mendapatkan collection category dan total quantity barang yang dibeli respectively dalam sequence."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = CustomerInterestDTO.class )
        )}
    )
    @GetMapping("/customerInterest")
    public ResponseEntity<Object> customerInterest(@RequestParam(required = true) Long customerId, @RequestParam(required = true) Integer year){
        var data = service.getCustomerInterest(customerId, year);
        var dto = new CustomerInterestDTO(data);
        return ResponseEntity.status(200).body(dto);
    }

    @Operation(
        summary = "Mendapatkan tahun dalam value dan text sebagai dropdown.",
        description = "Year yang akan menjadi drop down adalah existing year yang ada penjualannya."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("/orderYearDropdown")
    public ResponseEntity<Object> getOrderYearDropdown(){
        var orderYears = service.getOrderYears();
        var dropdown = new ArrayList<DropdownDTO>();
        for(var orderYear : orderYears){
            dropdown.add(new DropdownDTO(orderYear, orderYear.toString()));
        }
        return ResponseEntity.status(HttpStatus.OK).body(dropdown);
    }

    @Operation(
        summary = "Mendapatkan employee number dan full name dari salesman sebagai dropdown.",
        description = "Digunakan pada drop down di dashboard."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("/salesmanDropdown")
    public ResponseEntity<Object> getSalesmanDropdown(){
        var dropdown = service.getSalesmanDropdown();
        var dto = getDropdownDTO(dropdown, DropdownDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan id dan company name dari customer sebagai dropdown.",
        description = "Digunakan pada drop down di dashboard."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("/customerDropdown")
    public ResponseEntity<Object> getCustomerDropdown(){
        var dropdown = service.getCustomerDropdown();
        var dto = getDropdownDTO(dropdown, DropdownDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }
}
