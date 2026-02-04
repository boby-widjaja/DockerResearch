package com.basiliskSB.controller;

import com.basiliskSB.dto.account.ChangePasswordDTO;
import com.basiliskSB.dto.account.RegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.basiliskSB.dto.utility.Dropdown;
import com.basiliskSB.service.abstraction.AccountService;

@Controller
@RequestMapping("/account")
public class AccountController {

	@Autowired
	private AccountService service;
	
	@GetMapping("/loginForm")
	public String loginForm(Model model) {
		return "account/login-form";
	}

	@RequestMapping(value = "/accessDenied", method = {RequestMethod.GET, RequestMethod.POST})
	public String accessDenied(Model model) {
		return "account/access-denied";
	}
	
	@GetMapping("/registerForm")
	public String registerForm(Model model) {
		var dto = new RegisterDTO();
		var roleDropdown = Dropdown.getRoleDropdown();
		model.addAttribute("roleDropdown", roleDropdown);
		model.addAttribute("dto", dto);
		return "account/register-form";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("dto") RegisterDTO dto,
			BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			var roleDropdown = Dropdown.getRoleDropdown();
			model.addAttribute("roleDropdown", roleDropdown);
			return "account/register-form";
		}
		service.registerAccount(dto);
		return "redirect:/account/loginForm";
	}

	@GetMapping("/changePasswordForm")
	public String changePasswordForm(@RequestParam(required = true) String username, Model model){
		var dto = new ChangePasswordDTO();
		dto.setUsername(username);
		model.addAttribute("dto", dto);
		model.addAttribute("breadCrumbs", "Change Password");
		return "account/change-password-form";
	}

	@PostMapping("/changePassword")
	public String changePassword(@Valid @ModelAttribute("dto") ChangePasswordDTO dto, BindingResult bindingResult, Model model){
		if(bindingResult.hasErrors()) {
			return "account/change-password-form";
		}
		service.changePassword(dto);
		return "redirect:/";
	}

	@GetMapping("/failLogin")
	public String failLogin(Model model){
		return "account/fail-login";
	}
	
}
