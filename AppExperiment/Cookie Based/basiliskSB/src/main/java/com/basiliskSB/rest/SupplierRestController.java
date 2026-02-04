package com.basiliskSB.rest;
import com.basiliskSB.documentation.SupplierGridResponse;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.dto.supplier.SupplierFilterDTO;
import com.basiliskSB.dto.supplier.SupplierGridDTO;
import com.basiliskSB.dto.supplier.UpsertSupplierDTO;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
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
@RequestMapping("/api/supplier")
public class SupplierRestController extends AbstractRestController {

    @Qualifier("supplierService")
    @Autowired
    private CrudService service;

    @Operation(
        summary = "Mendapatkan data collection supplier.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = SupplierGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page,
                                      @RequestParam(defaultValue="") String company,
                                      @RequestParam(defaultValue="") String contact,
                                      @RequestParam(defaultValue="") String jobTitle){
        var pageCollection = service.getGrid(page, new SupplierFilterDTO(company, contact, jobTitle));
        var grid = getGridDTO(pageCollection, SupplierGridDTO::new);
        var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Mendapatkan 1 data supplier yang dipilih berdasarkan Id-nya.",
        description = "Data ini dimaksud kan untuk muncul di form update."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertSupplierDTO.class )
        )}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        var entity = service.getUpdate(id);
        var dto = new UpsertSupplierDTO(entity);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Menambahkan data baru supplier dengan auto increment Id.",
        description = "Id tidak perlu di set pada json di request body, id tidak akan digunakan pada post method ini."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertSupplierDTO.class )
        )}
    )
    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            dto.setId(0l);
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertSupplierDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Update data keseluruhan column pada entity supplier.",
        description = "Id di set pada request body."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertSupplierDTO.class )
        )}
    )
    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertSupplierDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertSupplierDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus satu supplier.",
        description = "Delete di sini adalah soft delete, supplier akan di flag pada column deleteDate."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = Long.class )
        )}
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) Long id){
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
