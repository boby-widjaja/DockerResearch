package com.basiliskSB.rest;
import com.basiliskSB.documentation.SalesmanDetailGridResponse;
import com.basiliskSB.dto.region.AssignSalesmanResponseDTO;
import com.basiliskSB.dto.salesman.AssignRegionDTO;
import com.basiliskSB.dto.salesman.AssignRegionResponseDTO;
import com.basiliskSB.dto.salesman.SalesmanDetailGridDTO;
import com.basiliskSB.dto.utility.DetailGridPageDTO;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.service.abstraction.SalesmanService;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/salesmanDetail")
public class SalesmanDetailRestController extends AbstractRestController {
    @Autowired
    private SalesmanService service;

    @Operation(
        summary = "Mendapatkan id dan city dari region sebagai dropdown.",
        description = "Digunakan pada assignment region ke salesman form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("regionDropdown/{employeeNumber}")
    public ResponseEntity<Object> getRegionDropdown(@PathVariable(required = true) String employeeNumber){
        var dropdown = service.getRegionDropdown(employeeNumber);
        var dto = getDropdownDTO(dropdown, row -> new DropdownDTO(row));
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    private String getSalesmanFullName(Object header){
        String firstName = MapperHelper.getStringField(header, "firstName");
        String lastName = MapperHelper.getStringField(header, "lastName");
        String fullName = firstName + ((lastName != null) ? String.format(" %s", lastName) : "");
        return fullName;
    }

    @Operation(
        summary = "Mendapatkan data collection region-region yang di assign ke specific salesman beserta informasi salesmannya.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = SalesmanDetailGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = true) String employeeNumber, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String city){
        var salesman = service.getSalesmanHeader(employeeNumber);
        class Header{
            private String employeeNumber;
            private String fullName;
            public String getEmployeeNumber() {
                return employeeNumber;
            }
            public String getFullName() {
                return fullName;
            }
            public Header(String employeeNumber, String fullName) {
                this.employeeNumber = employeeNumber;
                this.fullName = fullName;
            }
        }
        String fullName = getSalesmanFullName(salesman);
        var header = new Header(employeeNumber, fullName);
        var pageCollection = service.getRegionGridBySalesman(employeeNumber, page, city);
        var grid = getGridDTO(pageCollection, SalesmanDetailGridDTO::new);
        var gridPage = new DetailGridPageDTO(grid, page, pageCollection.getTotalPages(), header);
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Menambahkan assignment/tugas region kepada sebuah salesman.",
        description = "Response akan berupa salesman bersama total regionnya."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = AssignRegionResponseDTO.class )
        )}
    )
    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody AssignRegionDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.assignRegion(dto);
            var response = MapperHelper.mappingObject(feedback, AssignRegionResponseDTO.class);
            var regions = MapperHelper.getFieldValue(feedback, "regions");
            var regionSize = MapperHelper.getTotalSize(regions);
            response.setTotalRegion(regionSize);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus assignment sebuah region terhadap employee ini.",
        description = "Request ini tidak akan memberikan response khusus."
    )
    @ApiResponse(responseCode = "200")
    @DeleteMapping("regionId={regionId}/employeeNumber={employeeNumber}")
    public ResponseEntity<Object> deleteDetail(@PathVariable(required = true) Long regionId, @PathVariable(required = true) String employeeNumber){
        service.detachRegionSalesman(regionId, employeeNumber);
        return ResponseEntity.status(HttpStatus.OK).body("remove assignment success.");
    }
}
