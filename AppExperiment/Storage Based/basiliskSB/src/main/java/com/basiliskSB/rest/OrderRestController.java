package com.basiliskSB.rest;
import com.basiliskSB.documentation.OrderGridResponse;
import com.basiliskSB.dto.order.OrderFilterDTO;
import com.basiliskSB.dto.order.OrderGridDTO;
import com.basiliskSB.dto.order.UpsertOrderDTO;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.factory.abstraction.PDFFactory;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.OrderService;
import com.basiliskSB.utility.MapperHelper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
public class OrderRestController extends AbstractRestController {

    @Qualifier("orderService")
    @Autowired
    private CrudService service;

    @Autowired
    private OrderService orderService;

    @Operation(
        summary = "Mendapatkan id dan name dari customer sebagai dropdown.",
        description = "Digunakan pada order search filter dan insert update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("customerDropdown")
    public ResponseEntity<Object> getCustomerDropdown(){
        var dropdown = orderService.getCustomerDropdown();
        var dto = getDropdownDTO(dropdown, row -> new DropdownDTO(row));
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan employee number dan full name dari salesman sebagai dropdown.",
        description = "Digunakan pada order search filter dan insert update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("salesmanDropdown")
    public ResponseEntity<Object> getSalesmanDropdown(){
        var dropdown = orderService.getSalesmanDropdown();
        var dto = getDropdownDTO(dropdown, row -> new DropdownDTO(row));
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan id dan name dari delivery sebagai dropdown.",
        description = "Digunakan pada order search filter dan insert update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("deliveryDropdown")
    public ResponseEntity<Object> getDeliveryDropdown(){
        var dropdown = orderService.getDeliveryDropdown();
        var dto = getDropdownDTO(dropdown, row -> new DropdownDTO(row));
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan data collection order.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = OrderGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String invoiceNumber, @RequestParam(required=false) Long customerId,
        @RequestParam(required=false) String employeeNumber, @RequestParam(required=false) Long deliveryId, @RequestParam(required=false) String orderDate){
        LocalDate formattedDate = null;
        if(orderDate != null && orderDate != "") {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDate = LocalDate.parse(orderDate, formatter);
        }
        var pageCollection = service.getGrid(page, new OrderFilterDTO(invoiceNumber, customerId, employeeNumber, deliveryId, formattedDate));
        var grid = getGridDTO(pageCollection, OrderGridDTO::new);
        var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Mendapatkan 1 data order yang dipilih berdasarkan invoice number-nya.",
        description = "Data ini dimaksud kan untuk muncul di form update."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertOrderDTO.class )
        )}
    )
    @GetMapping("/{invoiceNumber}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) String invoiceNumber){
        var entity = service.getUpdate(invoiceNumber);
        var dto = new UpsertOrderDTO(entity);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Digunakan untuk menambah atau pun mengupdate data order.",
        description = "Order tidak memiliki auto increment, invoice number yang dikenali akan menjadi update, yang tidak dikenali akan menjadi insert dan digenerate otomatis."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertOrderDTO.class )
        )}
    )
    @PutMapping
    public ResponseEntity<Object> put(@Valid @RequestBody UpsertOrderDTO dto, BindingResult bindingResult) throws Exception{
        if(!bindingResult.hasErrors()){
            var feedback = service.save(dto);
            var response = MapperHelper.mappingObject(feedback, UpsertOrderDTO.class);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus satu order beserta order detailnya.",
        description = "Delete di sini adalah hard delete dan mengakibatkan cascading delete ke order detailnya."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = String.class )
        )}
    )
    @DeleteMapping("/{invoiceNumber}")
    public ResponseEntity<Object> delete(@PathVariable(required = true) String invoiceNumber){
        service.delete(invoiceNumber);
        return ResponseEntity.status(HttpStatus.OK).body(invoiceNumber);
    }

    @Qualifier("orderFactory")
    @Autowired
    private PDFFactory pdfFactory;

    @Operation(
        summary = "Mendownload invoice dari specific invoice number.",
        description = "Request ini akan meresponse dalam pdf file."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/pdf"
        )}
    )
    @GetMapping("/generateInvoice/{invoiceNumber}")
    public void generateInvoice(@PathVariable(required = true) String invoiceNumber, HttpServletResponse response) throws Exception {
        pdfFactory.setId(invoiceNumber);
        var pdfBytes = pdfFactory.getPdfByte();
        pdfFactory.setFileName(String.format("%s-invoice", invoiceNumber));
        var fileName = pdfFactory.getFileNameDisposition();
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", fileName);
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }
}
