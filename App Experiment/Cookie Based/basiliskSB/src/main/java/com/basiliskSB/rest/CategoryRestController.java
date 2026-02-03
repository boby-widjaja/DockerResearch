package com.basiliskSB.rest;
import com.basiliskSB.documentation.CategoryGridResponse;
import com.basiliskSB.dto.category.CategoryFilterDTO;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.dto.utility.GridPageDTO;
import com.basiliskSB.service.abstraction.CategoryService;
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
import com.basiliskSB.dto.category.CategoryGridDTO;
import jakarta.validation.Valid;

@CrossOrigin(
    origins = "http://localhost:8080",
    allowCredentials = "true"
)
@RestController
@RequestMapping("/api/category")
public class CategoryRestController extends AbstractRestController{

	@Qualifier("categoryService")
	@Autowired
	private CrudService service;

	@Autowired
	private CategoryService categoryService;

	@Operation(
		summary = "Mendapatkan data collection category.",
		description = "Data akan di respond dalam jumlah 10 per halaman."
	)
	@ApiResponse(
		responseCode = "200",
		content = { @Content(
			mediaType = "application/json",
			schema = @Schema( implementation = CategoryGridResponse.class )
		)}
	)
	@GetMapping
	public ResponseEntity<Object> get(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name){
		var pageCollection = service.getGrid(page, new CategoryFilterDTO(name));
		var grid = getGridDTO(pageCollection, CategoryGridDTO::new);
		var gridPage = new GridPageDTO(grid, page, pageCollection.getTotalPages());
		return ResponseEntity.status(HttpStatus.OK).body(gridPage);
	}

	@Operation(
		summary = "Mendapatkan 1 data category yang dipilih berdasarkan Id-nya.",
		description = "Data ini dimaksud kan untuk muncul di form update."
	)
	@ApiResponse(
		responseCode = "200",
		content = { @Content(
			mediaType = "application/json",
			schema = @Schema( implementation = UpsertCategoryDTO.class )
		)}
	)
	@GetMapping("/{id}")
	public ResponseEntity<Object> getUpdate(@PathVariable(required = true) Long id){
		var entity = service.getUpdate(id);
		var dto = new UpsertCategoryDTO(entity);
		return ResponseEntity.status(HttpStatus.OK).body(dto);
	}

	@Operation(
		summary = "Menambahkan data baru category dengan auto increment Id.",
		description = "Id tidak perlu di set pada json di request body, id tidak akan digunakan pada post method ini."
	)
	@ApiResponse(
		responseCode = "200",
		content = { @Content(
			mediaType = "application/json",
			schema = @Schema( implementation = UpsertCategoryDTO.class )
		)}
	)
	@PostMapping
	public ResponseEntity<Object> post(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult) throws Exception{
		if(!bindingResult.hasErrors()){
			dto.setId(0l);
			var feedback = service.save(dto);
			var response = MapperHelper.mappingObject(feedback, UpsertCategoryDTO.class);
			return ResponseEntity.status(HttpStatus.CREATED).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
		}
	}

	@Operation(
		summary = "Update data keseluruhan column pada entity category.",
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
	public ResponseEntity<Object> put(@Valid @RequestBody UpsertCategoryDTO dto, BindingResult bindingResult) throws Exception{
		if(!bindingResult.hasErrors()){
			var feedback = service.save(dto);
			var response = MapperHelper.mappingObject(feedback, UpsertCategoryDTO.class);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} else {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(getErrors(bindingResult.getAllErrors()));
		}
	}

	@Operation(
		summary = "Menghapus satu category.",
		description = "Delete di sini adalah hard delete, product memiliki ketergantungan dengan category."
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
		var dependentProducts = categoryService.dependentProducts(id);
		if(dependentProducts > 0) {
			return ResponseEntity.status(409).body(String.format("There are %s dependent products.", dependentProducts));
		}
		service.delete(id);
		return ResponseEntity.status(HttpStatus.OK).body(id);
	}
}
