package com.basiliskSB.rest;
import com.basiliskSB.documentation.OrderDetailGridResponse;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.dto.order.OrderDetailGridDTO;
import com.basiliskSB.dto.order.UpsertOrderDetailDTO;
import com.basiliskSB.dto.utility.DetailGridPageDTO;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.service.abstraction.CrudDetailService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/orderDetail")
public class OrderDetailRestController extends AbstractRestController{
    @Qualifier("orderService")
    @Autowired
    private CrudDetailService service;

    @Autowired
    private OrderService orderService;

    @Operation(
        summary = "Mendapatkan id dan name dari product sebagai dropdown.",
        description = "Digunakan pada order detail insert dan update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("productDropdown/{invoiceNumber}")
    public ResponseEntity<Object> getProductDropdown(@PathVariable(required = true) String invoiceNumber){
        var dropdown = orderService.getProductDropdown(invoiceNumber);
        var dto = getDropdownDTO(dropdown, DropdownDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan data collection detail belanja beserta informasi headernya.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = OrderDetailGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(required = true) String invoiceNumber, @RequestParam(defaultValue = "1") Integer page){
        var header = service.getHeader(invoiceNumber);
        var pageCollection = service.getGridDetail(invoiceNumber, page, null);
        var grid = getGridDTO(pageCollection, (row) -> {
            Double unitPrice = MapperHelper.getDoubleField(row, 3);
            Integer quantity = MapperHelper.getIntegerField(row, 4);
            Double discount = MapperHelper.getDoubleField(row, 5);
            Double totalPrice = OrderService.calculateTotalPrice(unitPrice, quantity, discount);
            return new OrderDetailGridDTO(row, totalPrice);
        });
        var gridPage = new DetailGridPageDTO(grid, 1, 1, header);
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Mendapatkan 1 detail dari order yang dipilih berdasarkan Id-nya.",
        description = "Data ini dimaksud kan untuk muncul di form update."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertOrderDetailDTO.class )
        )}
    )
    @GetMapping("/{invoiceNumber}/{productId}")
    public ResponseEntity<Object> getUpdateDetail(@PathVariable(required = true) String invoiceNumber, @PathVariable(required = true) Long productId){
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        class CompositeId{
            private String invoiceNumber;
            private Long productId;
        }
        var entity = service.getUpdateDetail(new CompositeId(invoiceNumber, productId));
        var dto = new UpsertOrderDetailDTO(entity);
        var productName = orderService.getProductName(productId);
        dto.setProductName(productName);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Menambahkan data baru order detail dengan auto increment Id.",
        description = "Id tidak perlu di set pada json di request body, id tidak akan digunakan pada post method ini."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertOrderDetailDTO.class )
        )}
    )
    @PostMapping
    public ResponseEntity<Object> postDetail(@Valid @RequestBody UpsertOrderDetailDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.saveDetail(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertOrderDetailDTO.class);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Update data keseluruhan column pada entity order detail.",
        description = "Id di set pada request body."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertCategoryDTO.class )
        )}
    )
    @PutMapping
    public ResponseEntity<Object> putDetail(@Valid @RequestBody UpsertOrderDetailDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.saveDetail(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertOrderDetailDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus satu order detail.",
        description = "Delete di sini adalah hard delete."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = Long.class )
        )}
    )
    @DeleteMapping("/{invoiceNumber}/{productId}")
    public ResponseEntity<Object> deleteDetail(@PathVariable(required = true) String invoiceNumber, @PathVariable(required = true) Long productId){
        @Getter
        @Setter
        @NoArgsConstructor
        @AllArgsConstructor
        class CompositeId{
            private String invoiceNumber;
            private Long productId;
        }
        var id = new CompositeId(invoiceNumber, productId);
        service.deleteDetail(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

}
