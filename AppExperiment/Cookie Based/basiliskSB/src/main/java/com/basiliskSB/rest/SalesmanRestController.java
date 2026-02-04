package com.basiliskSB.rest;
import com.basiliskSB.documentation.SalesmanGridResponse;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.dto.salesman.PutSalesmanDTO;
import com.basiliskSB.dto.salesman.SalesmanFilterDTO;
import com.basiliskSB.dto.salesman.SalesmanGridDTO;
import com.basiliskSB.dto.salesman.UpdateSalesmanDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.SalesmanService;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/salesman")
public class SalesmanRestController extends AbstractRestController {
    @Qualifier("salesmanService")
    @Autowired
    private CrudService service;

    @Autowired
    private SalesmanService salesmanService;

    @Operation(
        summary = "Mendapatkan enumerasi employee level sebagai dropdown.",
        description = "Digunakan pada employee search filter dan insert update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("employeeLevelDropdown")
    public ResponseEntity<Object> getEmployeeLevelDropdown(){
        var employeeLevelDropdown = Dropdown.getEmployeeLevelDropdown();
        return ResponseEntity.status(HttpStatus.OK).body(employeeLevelDropdown);
    }

    @Operation(
        summary = "Mendapatkan employee number dan salesman full name dari salesman sebagai dropdown untuk superior.",
        description = "Digunakan pada product search filter dan insert update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("superiorDropdown")
    public ResponseEntity<Object> getSuperiorDropdown(@RequestParam(required = false) String employeeNumber){
        var dropdown = salesmanService.getSuperiorDropdown(employeeNumber);
        var dto = getDropdownDTO(dropdown, DropdownDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan data collection salesman.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = SalesmanGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(
        @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String employeeNumber,
        @RequestParam(defaultValue="") String name, @RequestParam(defaultValue="") String employeeLevel,
        @RequestParam(defaultValue="") String superiorName){
        var pageCollection = service.getGrid(page, new SalesmanFilterDTO(employeeNumber, name, employeeLevel, superiorName));
        var grid = getGridDTO(pageCollection, SalesmanGridDTO::new);
        var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Mendapatkan 1 data salesman yang dipilih berdasarkan employee number-nya.",
        description = "Data ini dimaksud kan untuk muncul di form update."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpdateSalesmanDTO.class )
        )}
    )
    @GetMapping("/{employeeNumber}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) String employeeNumber){
        var entity = service.getUpdate(employeeNumber);
        var dto = new UpdateSalesmanDTO(entity);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Digunakan untuk menambah atau pun mengupdate data salesman.",
        description = "Salesman tidak memiliki auto increment, employee number yang dikenali akan menjadi update, yang tidak dikenali akan menjadi insert."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = PutSalesmanDTO.class )
        )}
    )
    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody PutSalesmanDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, PutSalesmanDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus satu salesman.",
        description = "Delete di sini adalah hard delete, order dan subordinate karyawan lain memiliki ketergantungan dengan sales ini."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = String.class )
        )}
    )
    @DeleteMapping("/{employeeNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String employeeNumber){
        long dependentOrders = salesmanService.dependentOrders(employeeNumber);
        long dependentSubordinates = salesmanService.dependentSubordinates(employeeNumber);
        if(dependentOrders > 0 || dependentSubordinates > 0) {
            return ResponseEntity.status(409).body(String.format("There are %s dependent orders and %s dependent subordinates on this employee", dependentOrders, dependentSubordinates));
        }
        service.delete(employeeNumber);
        return ResponseEntity.status(HttpStatus.OK).body(employeeNumber);
    }
}
