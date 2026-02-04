package com.basiliskSB.controller;
import com.basiliskSB.dto.salesman.*;
import com.basiliskSB.factory.abstraction.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/salesman")
public class SalesmanController {
	@Qualifier("salesmanFactory")
	@Autowired
	private IndexFactory indexFactory;
	
	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String employeeNumber, @RequestParam(defaultValue="") String name, @RequestParam(defaultValue="") String employeeLevel, @RequestParam(defaultValue="") String superiorName, Model model){
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("employeeNumber", employeeNumber);
		indexFactory.setUrlParameters("name", name);
		indexFactory.setUrlParameters("employeeLevel", employeeLevel);
		indexFactory.setUrlParameters("superiorName", superiorName);
		indexFactory.processIndex(model);
		return "salesman/salesman-index";
	}

	@Qualifier("salesmanFactory")
	@Autowired
	private UpsertFactory upsertFactory;
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) String employeeNumber, Model model) {
		upsertFactory.setId(employeeNumber);
		upsertFactory.processUpsertForm(model);
		return "salesman/salesman-form";
	}
	
	@PostMapping("/insert")
	public String insert(@Valid @ModelAttribute("dto") InsertSalesmanDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "salesman/salesman-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/salesman/index";
		}
	}
	
	@PostMapping("/update")
	public String update(@Valid @ModelAttribute("dto") UpdateSalesmanDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "salesman/salesman-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/salesman/index";
		}
	}

	@Qualifier("salesmanFactory")
	@Autowired
	private DeleteFactory deleteFactory;

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) String employeeNumber, Model model) {
		deleteFactory.setId(employeeNumber);
		var success = deleteFactory.processDelete(model);
		if(!success) {
			return "salesman/salesman-delete";
		}
		return "redirect:/salesman/index";
	}

	@Qualifier("salesmanDetailFactory")
	@Autowired
	private IndexDetailFactory indexDetailFactory;

	@GetMapping("/detail")
	public String detail(@RequestParam(required = true) String employeeNumber, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String city, Model model) {
		indexDetailFactory.setParentId(employeeNumber);
		indexDetailFactory.setPage(page);
		indexDetailFactory.setUrlParameters("city", city);
		indexDetailFactory.processIndex(model);
		return "salesman/salesman-detail";
	}

	@Qualifier("salesmanDetailFactory")
	@Autowired
	private UpsertFactory upsertDetailFactory;
	
	@GetMapping("/assignDetailForm")
	public String assignDetailForm(@RequestParam(required = true) String employeeNumber, Model model) {
		upsertDetailFactory.setId(employeeNumber);
		upsertDetailFactory.processUpsertForm(model);
		return "salesman/salesman-detail-form";
	}
	
	@PostMapping("/assignDetail")
	public String assignDetail(@Valid @ModelAttribute("dto") AssignRegionDTO dto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			upsertDetailFactory.processUpsertForm(model, dto);
			return "salesman/salesman-detail-form";
		} else {
			upsertDetailFactory.save(dto);
			redirectAttributes.addAttribute("employeeNumber", dto.getSalesmanEmployeeNumber());
			return "redirect:/salesman/detail";
		}
	}

	@Qualifier("salesmanDetailFactory")
	@Autowired
	private DeleteAssociationFactory deleteAssociationFactory;

	@RequestMapping(value = "/deleteDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteDetail(@RequestParam(required = true) Long regionId, @RequestParam(required = true) String employeeNumber, Model model, RedirectAttributes redirectAttributes) {
		deleteAssociationFactory.setId(employeeNumber);
		deleteAssociationFactory.setPairId(regionId);
		deleteAssociationFactory.processDelete(model);
		redirectAttributes.addAttribute("employeeNumber", employeeNumber);
		return "redirect:/salesman/detail";
	}
}
