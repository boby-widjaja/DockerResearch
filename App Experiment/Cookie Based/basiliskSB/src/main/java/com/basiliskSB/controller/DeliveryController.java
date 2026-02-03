package com.basiliskSB.controller;
import com.basiliskSB.dto.delivery.UpsertDeliveryDTO;
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
@RequestMapping("/delivery")
public class DeliveryController {
	@Qualifier("deliveryFactory")
	@Autowired
	private IndexFactory indexFactory;

	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String company, Model model){
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("company", company);
		indexFactory.processIndex(model);
		return "delivery/delivery-index";
	}

	@Qualifier("deliveryFactory")
	@Autowired
	private UpsertFactory upsertFactory;
	
	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		upsertFactory.setId(id);
		upsertFactory.processUpsertForm(model);
		return "delivery/delivery-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertDeliveryDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "delivery/delivery-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/delivery/index";
		}
	}

	@Qualifier("deliveryFactory")
	@Autowired
	private DeleteFactory deleteFactory;

	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) Long id, Model model) {
		deleteFactory.setId(id);
		var success = deleteFactory.processDelete(model);
		if(!success) {
			return "delivery/delivery-delete";
		}
		return "redirect:/delivery/index";
	}
}
