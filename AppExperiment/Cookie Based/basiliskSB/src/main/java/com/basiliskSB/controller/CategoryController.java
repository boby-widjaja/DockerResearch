package com.basiliskSB.controller;
import com.basiliskSB.dto.category.UpsertCategoryDTO;
import com.basiliskSB.factory.abstraction.DeleteFactory;
import com.basiliskSB.factory.abstraction.IndexFactory;
import com.basiliskSB.factory.abstraction.UpsertFactory;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.ui.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.*;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Qualifier("categoryFactory")
	@Autowired
	private IndexFactory indexFactory;

	@GetMapping("/index")
	public String index(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue="") String name, Model model){
		indexFactory.setPage(page);
		indexFactory.setUrlParameters("name", name);
		indexFactory.processIndex(model);
		return "category/category-index";
	}

	@Qualifier("categoryFactory")
	@Autowired
	private UpsertFactory upsertFactory;

	@GetMapping("/upsertForm")
	public String upsertForm(@RequestParam(required = false) Long id, Model model) {
		upsertFactory.setId(id);
		upsertFactory.processUpsertForm(model);
		return "category/category-form";
	}
	
	@PostMapping("/upsert")
	public String upsert(@Valid @ModelAttribute("dto") UpsertCategoryDTO dto, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			upsertFactory.processUpsertForm(model, dto);
			return "category/category-form";
		} else {
			upsertFactory.save(dto);
			return "redirect:/category/index";
		}
	}

	@Qualifier("categoryFactory")
	@Autowired
	private DeleteFactory deleteFactory;
	
	@RequestMapping(value = "/delete", method = {RequestMethod.GET, RequestMethod.POST})
	public String delete(@RequestParam(required = true) Long id, Model model) {
		deleteFactory.setId(id);
		var success = deleteFactory.processDelete(model);
		if (success){
			return "redirect:/category/index";
		}
		return "category/category-delete";
	}
}
