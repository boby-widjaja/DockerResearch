package com.basiliskSB.rest;
import com.basiliskSB.documentation.DeliveryGridResponse;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.dto.customer.UpsertCustomerDTO;
import com.basiliskSB.dto.delivery.DeliveryFilterDTO;
import com.basiliskSB.dto.delivery.DeliveryGridDTO;
import com.basiliskSB.dto.delivery.UpsertDeliveryDTO;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.DeliveryService;
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
@RequestMapping("/api/delivery")
public class DeliveryRestController extends AbstractRestController{

    @Qualifier("deliveryService")
    @Autowired
    private CrudService service;

    @Autowired
    private DeliveryService deliveryService;

    @Operation(
        summary = "Mendapatkan data collection delivery.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DeliveryGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String company){
        var pageCollection = service.getGrid(page, new DeliveryFilterDTO(company));
        var grid = getGridDTO(pageCollection, DeliveryGridDTO::new);
        var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Mendapatkan 1 data delivery yang dipilih berdasarkan Id-nya.",
        description = "Data ini dimaksud kan untuk muncul di form update."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertDeliveryDTO.class )
        )}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        var entity = service.getUpdate(id);
        var dto = new UpsertDeliveryDTO(entity);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Menambahkan data baru delivery dengan auto increment Id.",
        description = "Id tidak perlu di set pada json di request body, id tidak akan digunakan pada post method ini."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertDeliveryDTO.class )
        )}
    )
    @PostMapping
    public ResponseEntity<Object> post(@Valid @RequestBody UpsertDeliveryDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            dto.setId(0l);
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertDeliveryDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Update data keseluruhan column pada entity delivery.",
        description = "Id di set pada request body."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertDeliveryDTO.class )
        )}
    )
    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertDeliveryDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertDeliveryDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus satu delivery.",
        description = "Delete di sini adalah hard delete, order memiliki ketergantungan dengan delivery."
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
        long dependentOrders = deliveryService.dependentOrders(id);
        if(dependentOrders > 0) {
            ResponseEntity.status(409).body(String.format("There are %s orders dependent on this delivery", dependentOrders));
        }
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
