package com.basiliskSB.rest;
import com.basiliskSB.documentation.RegionDetailGridResponse;
import com.basiliskSB.dto.region.AssignSalesmanDTO;
import com.basiliskSB.dto.region.AssignSalesmanResponseDTO;
import com.basiliskSB.dto.region.RegionDetailGridDTO;
import com.basiliskSB.dto.utility.DetailGridPageDTO;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.service.abstraction.RegionService;
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
@RequestMapping("/api/regionDetail")
public class RegionDetailRestController extends AbstractRestController{
    @Autowired
    private RegionService service;

    @Autowired
    private RegionService regionService;

    @Operation(
        summary = "Mendapatkan employee number dan full name dari salesman sebagai dropdown.",
        description = "Digunakan pada assignment salesman ke region form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("salesmanDropdown/{id}")
    public ResponseEntity<Object> getSalesmanDropdown(@PathVariable(required = true) Long id){
        var dropdown = regionService.getSalesmanDropdown(id);
        var dto = getDropdownDTO(dropdown, row -> new DropdownDTO(row));
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan data collection para sales yang bekerja di specific region beserta informasi regionnya.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = RegionDetailGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(
        @RequestParam(required = true) Long id, @RequestParam(defaultValue = "1") Integer page,
        @RequestParam(defaultValue="") String employeeNumber, @RequestParam(defaultValue="") String name,
        @RequestParam(defaultValue="") String employeeLevel, @RequestParam(defaultValue="") String superiorName){
        var header = service.getRegionHeader(id);
        var pageCollection = service.getSalesmanGridByRegion(id, page, employeeNumber, name, employeeLevel, superiorName);
        var grid = getGridDTO(pageCollection, RegionDetailGridDTO::new);
        var gridPage = new DetailGridPageDTO(grid, page, pageCollection.getTotalPages(), header);
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Menambahkan assignment/tugas salesman kepada sebuah region.",
        description = "Response akan berupa region bersama total salesman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = AssignSalesmanResponseDTO.class )
        )}
    )
    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody AssignSalesmanDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.assignSalesman(dto);
            var response = MapperHelper.mappingObject(feedback, AssignSalesmanResponseDTO.class);
            var salesmen = MapperHelper.getFieldValue(feedback, "salesmen");
            var salesmenSize = MapperHelper.getTotalSize(salesmen);
            response.setTotalSalesman(salesmenSize);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus assignment seorang employee terhadap region ini.",
        description = "Request ini tidak akan memberikan response khusus."
    )
    @ApiResponse(responseCode = "200")
    @DeleteMapping("regionId={regionId}/employeeNumber={employeeNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long regionId, @PathVariable(required = true) String employeeNumber){
        service.detachRegionSalesman(regionId, employeeNumber);
        return ResponseEntity.status(HttpStatus.OK).body("remove assignment success.");
    }
}