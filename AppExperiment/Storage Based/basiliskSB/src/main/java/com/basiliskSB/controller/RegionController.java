package com.basiliskSB.controller;
import com.basiliskSB.dto.region.*;
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
@RequestMapping("/region")
public class RegionController {
	@Qualifier("regionFactory")
	@Autowired
	private IndexFactory indexFactory;

	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String city, Model model){
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("city", city);
		indexFactory.processIndex(model);
		return "region/region-index";
	}

	@Qualifier("regionFactory")
	@Autowired
	private UpsertFactory upsertFactory;
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		upsertFactory.setId(id);
		upsertFactory.processUpsertForm(model);
		return "region/region-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertRegionDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "region/region-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/region/index";
		}
	}

	@Qualifier("regionFactory")
	@Autowired
	private DeleteFactory deleteFactory;

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) Long id, Model model) {
		deleteFactory.setId(id);
		deleteFactory.processDelete(model);
		return "redirect:/region/index";
	}

	@Qualifier("regionDetailFactory")
	@Autowired
	private IndexDetailFactory indexDetailFactory;

	@GetMapping("/detail")
	public String detail(@RequestParam(required = true) Long id, @RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String employeeNumber, @RequestParam(defaultValue="") String name, @RequestParam(defaultValue="") String employeeLevel, @RequestParam(defaultValue="") String superiorName, Model model) {
		indexDetailFactory.setParentId(id);
		indexDetailFactory.setPage(page);
		indexDetailFactory.setUrlParameters("employeeNumber", employeeNumber);
		indexDetailFactory.setUrlParameters("name", name);
		indexDetailFactory.setUrlParameters("employeeLevel", employeeLevel);
		indexDetailFactory.setUrlParameters("superiorName", superiorName);
		indexDetailFactory.processIndex(model);
		return "region/region-detail";
	}

	@Qualifier("regionDetailFactory")
	@Autowired
	private UpsertFactory upsertDetailFactory;

	@GetMapping("/assignDetailForm")
	public String assignDetailForm(@RequestParam(required = true) Long id, Model model) {
		upsertDetailFactory.setId(id);
		upsertDetailFactory.processUpsertForm(model);
		return "region/region-detail-form";
	}
	
	@PostMapping("/assignDetail")
	public String assignDetail(@Valid @ModelAttribute("dto") AssignSalesmanDTO dto, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
		if(bindingResult.hasErrors()) {
			upsertDetailFactory.processUpsertForm(model, dto);
			return "region/region-detail-form";
		} else {
			upsertDetailFactory.save(dto);
			redirectAttributes.addAttribute("id", dto.getRegionId());
			return "redirect:/region/detail";
		}
	}

	@Qualifier("regionDetailFactory")
	@Autowired
	private DeleteAssociationFactory deleteAssociationFactory;

	@RequestMapping(value = "/deleteDetail", method = {RequestMethod.GET, RequestMethod.POST})
	public String deleteDetail(@RequestParam(required = true) Long regionId, @RequestParam(required = true) String employeeNumber, Model model, RedirectAttributes redirectAttributes) {
		deleteAssociationFactory.setId(regionId);
		deleteAssociationFactory.setPairId(employeeNumber);
		deleteAssociationFactory.processDelete(model);
		redirectAttributes.addAttribute("id", regionId);
		return "redirect:/region/detail";
	}
}