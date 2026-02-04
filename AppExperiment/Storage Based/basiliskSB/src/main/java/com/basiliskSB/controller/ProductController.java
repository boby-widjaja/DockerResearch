package com.basiliskSB.controller;
import com.basiliskSB.dto.product.UpsertProductDTO;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/product")
public class ProductController {

	@Qualifier("productFactory")
	@Autowired
	private IndexFactory indexFactory;

	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name, @RequestParam(required = false) Long categoryId, @RequestParam(required = false) Long supplierId, Model model){
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("name", name);
		indexFactory.setUrlParameters("categoryId", categoryId);
		indexFactory.setUrlParameters("supplierId", supplierId);
		indexFactory.processIndex(model);
		return "product/product-index";
	}

	@Qualifier("productFactory")
	@Autowired
	private UpsertFactory upsertFactory;

	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		upsertFactory.setId(id);
		upsertFactory.processUpsertForm(model);
		return "product/product-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertProductDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "product/product-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/product/index";
		}
	}

	@Qualifier("productFactory")
	@Autowired
	private DeleteFactory deleteFactory;

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) Long id, Model model) {
		deleteFactory.setId(id);
		var success = deleteFactory.processDelete(model);
		if(success) {
			return "redirect:/product/index";
		}
		return "product/product-delete";
	}
}