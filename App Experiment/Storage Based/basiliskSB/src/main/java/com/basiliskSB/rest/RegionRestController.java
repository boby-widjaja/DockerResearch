package com.basiliskSB.rest;
import com.basiliskSB.documentation.RegionGridResponse;
import com.basiliskSB.dto.product.UpsertProductDTO;
import com.basiliskSB.dto.region.RegionFilterDTO;
import com.basiliskSB.dto.region.RegionGridDTO;
import com.basiliskSB.dto.region.UpsertRegionDTO;
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
@RequestMapping("/api/region")
public class RegionRestController extends AbstractRestController {

    @Qualifier("regionService")
    @Autowired
    private CrudService service;


    @Operation(
        summary = "Mendapatkan data collection region.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = RegionGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String city){
        var pageCollection = service.getGrid(page, new RegionFilterDTO(city));
        var grid = getGridDTO(pageCollection, RegionGridDTO::new);
        var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Mendapatkan 1 data region yang dipilih berdasarkan Id-nya.",
        description = "Data ini dimaksud kan untuk muncul di form update."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertRegionDTO.class )
        )}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        var entity = service.getUpdate(id);
        var dto = new UpsertRegionDTO(entity);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Menambahkan data baru region dengan auto increment Id.",
        description = "Id tidak perlu di set pada json di request body, id tidak akan digunakan pada post method ini."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertRegionDTO.class )
        )}
    )
    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertRegionDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            dto.setId(0l);
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertRegionDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Update data keseluruhan column pada entity region.",
        description = "Id di set pada request body."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertRegionDTO.class )
        )}
    )
    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertRegionDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertRegionDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus satu region.",
        description = "Mendelete region akan langsung men-delete assignment region ini dengan salesman, tetapi salesman tetap akan ada pada database."
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
