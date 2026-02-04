package com.basiliskSB.rest;
import com.basiliskSB.documentation.ProductGridResponse;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.dto.product.ProductFilterDTO;
import com.basiliskSB.dto.product.ProductGridDTO;
import com.basiliskSB.dto.product.UpsertProductDTO;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.dto.utility.DropdownDTO;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CrudService;
import com.basiliskSB.service.abstraction.ProductService;
import com.basiliskSB.utility.FileHelper;
import com.basiliskSB.utility.MapperHelper;
import com.basiliskSB.utility.ProductImageHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.Valid;

@CrossOrigin
@RestController
@RequestMapping("/api/product")
public class ProductRestController extends AbstractRestController {
    @Qualifier("productService")
    @Autowired
    private CrudService service;

    @Autowired
    private ProductService productService;

    @Operation(
        summary = "Mendapatkan id dan name dari category sebagai dropdown.",
        description = "Digunakan pada product search filter dan insert update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("/categoryDropdown")
    public ResponseEntity<Object> getCategoryDropdown(){
        var dropdown = productService.getCategoryDropdown();
        var dto = getDropdownDTO(dropdown, DropdownDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan id dan company name dari suplier sebagai dropdown.",
        description = "Digunakan pada product search filter dan insert update form."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = DropdownDTO.class )
        )}
    )
    @GetMapping("/supplierDropdown")
    public ResponseEntity<Object> getSupplierDropdown(){
        var dropdown = productService.getSupplierDropdown();
        var dto = getDropdownDTO(dropdown, DropdownDTO::new);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Mendapatkan data collection product.",
        description = "Data akan di respond dalam jumlah 10 per halaman."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = ProductGridResponse.class )
        )}
    )
    @GetMapping
    public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name, @RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long supplierId){
        var pageCollection = service.getGrid(page, new ProductFilterDTO(name, categoryId, supplierId));
        var grid = getGridDTO(pageCollection, ProductGridDTO::new);
        var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
        return ResponseEntity.status(HttpStatus.OK).body(gridPage);
    }

    @Operation(
        summary = "Mendapatkan 1 data product yang dipilih berdasarkan Id-nya.",
        description = "Data ini dimaksud kan untuk muncul di form update."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertProductDTO.class )
        )}
    )
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
        var entity = service.getUpdate(id);
        var dto = new UpsertProductDTO(entity);
        return ResponseEntity.status(HttpStatus.OK).body(dto);
    }

    @Operation(
        summary = "Menambahkan product baru dengan menggunakan multipart form data. Product menggunakan auto increment Id.",
        description = "Request ini membutuhkan 2 input key pada form-data, dto (application/json), image (image/jpeg) (image/png)."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertProductDTO.class )
        )}
    )
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> post(@RequestPart MultipartFile image, @RequestPart @Valid UpsertProductDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            dto.setId(0l);
            try{
                dto.setImage(image);
                ProductImageHandler.imageFieldHandler(dto);
                var feedback = service.save(dto);
                var response = MapperHelper.mappingObject(feedback, UpsertProductDTO.class);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (Exception exception){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Ini adalah alternate request untuk menambah product. Di sini request body sepenuhnya menggunakan multipart form.",
        description = "Di sini setiap field dari dto di submit dengan key masing-masing di dalam multipart form, image menjadi salah satu field di dto."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertProductDTO.class )
        )}
    )
    @PostMapping(value="/alternate", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> postAlternate(@Valid UpsertProductDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            dto.setId(0l);
            try{
                ProductImageHandler.imageFieldHandler(dto);
                var feedback = service.save(dto);
                var response = MapperHelper.mappingObject(feedback, UpsertProductDTO.class);
                return ResponseEntity.status(HttpStatus.CREATED).body(response);
            } catch (Exception exception){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Mengupdate data form sepenuhnya, put pada request ini memisahkan dto dengan image untuk diupload.",
        description = "Request ini membutuhkan 2 input key pada form-data, dto (application/json), image (image/jpeg) (image/png)."
    )
    @ApiResponse(
        responseCode = "200",
        content = { @Content(
            mediaType = "application/json",
            schema = @Schema( implementation = UpsertProductDTO.class )
        )}
    )
    @PutMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> put(@RequestPart MultipartFile image, @Valid @RequestPart UpsertProductDTO dto, BindingResult bindingResult){
        if(!bindingResult.hasErrors()){
            try{
                dto.setImage(image);
                ProductImageHandler.imageFieldHandler(dto);
                var feedback = service.save(dto);
                var response = MapperHelper.mappingObject(feedback, UpsertProductDTO.class);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } catch (Exception exception){
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("There is a run-time error on the server.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
        }
    }

    @Operation(
        summary = "Menghapus satu product.",
        description = "Delete di sini adalah hard delete, order detail memiliki kebergantungan dengan product."
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
        long dependentOrderDetails = productService.dependentOrderDetails(id);
        if(dependentOrderDetails > 0) {
            return ResponseEntity.status(409).body(String.format("There are %s order details dependent on this product", dependentOrderDetails));
        }
        String imagePath = productService.getImagePath(id);
        FileHelper.deleteProductPhoto(imagePath);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}
