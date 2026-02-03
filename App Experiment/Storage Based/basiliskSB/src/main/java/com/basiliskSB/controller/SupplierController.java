package com.basiliskSB.controller;
import com.basiliskSB.dto.supplier.UpsertSupplierDTO;
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

@Qualifier("supplierMenu")
@Controller
@RequestMapping("/supplier")
public class SupplierController {

	@Qualifier("supplierFactory")
	@Autowired
	private IndexFactory indexFactory;

	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, 
			@RequestParam(defaultValue="") String company,
			@RequestParam(defaultValue="") String contact,
			@RequestParam(defaultValue="") String jobTitle,
			Model model){
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("company", company);
		indexFactory.setUrlParameters("contact", contact);
		indexFactory.setUrlParameters("jobTitle", jobTitle);
		indexFactory.processIndex(model);
		return "supplier/supplier-index";
	}

	@Qualifier("supplierFactory")
	@Autowired
	private UpsertFactory upsertFactory;
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		upsertFactory.setId(id);
		upsertFactory.processUpsertForm(model);
		return "supplier/supplier-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertSupplierDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "supplier/supplier-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/supplier/index";
		}
	}

	@Qualifier("supplierFactory")
	@Autowired
	private DeleteFactory deleteFactory;

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) Long id, Model model) {
		deleteFactory.setId(id);
		deleteFactory.processDelete(model);
		return "redirect:/supplier/index";
	}
}
