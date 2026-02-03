package com.basiliskSB.controller;
import com.basiliskSB.dto.customer.UpsertCustomerDTO;
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
@RequestMapping("/customer")
public class CustomerController {

	@Qualifier("customerFactory")
	@Autowired
	private IndexFactory indexFactory;

	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String company, @RequestParam(defaultValue="") String contact, Model model){
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("company", company);
		indexFactory.setUrlParameters("contact", contact);
		indexFactory.processIndex(model);
		return "customer/customer-index";
	}

	@Qualifier("customerFactory")
	@Autowired
	private UpsertFactory upsertFactory;
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		upsertFactory.setId(id);
		upsertFactory.processUpsertForm(model);
		return "customer/customer-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertCustomerDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "customer/customer-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/customer/index";
		}
	}

	@Qualifier("customerFactory")
	@Autowired
	private DeleteFactory deleteFactory;

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) Long id, Model model) {
		deleteFactory.setId(id);
		deleteFactory.processDelete(model);
		return "redirect:/customer/index";
	}
	
}
