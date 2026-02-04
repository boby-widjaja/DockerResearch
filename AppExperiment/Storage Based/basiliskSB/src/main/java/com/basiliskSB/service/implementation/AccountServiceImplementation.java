package com.basiliskSB.service.implementation;

import com.basiliskSB.dto.account.ChangePasswordDTO;
import com.basiliskSB.service.abstraction.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.basiliskSB.utility.ApplicationUserDetails;
import com.basiliskSB.dao.AccountRepository;
import com.basiliskSB.dto.account.RegisterDTO;
import com.basiliskSB.entity.Account;

@Service
public class AccountServiceImplementation implements AccountService, UserDetailsService{

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void registerAccount(RegisterDTO dto) {
		var hashPassword = passwordEncoder.encode(dto.getPassword());
		var account = new Account(
				dto.getUsername(),
				hashPassword,
				dto.getRole());
		accountRepository.save(account);
	}

	@Override
	public String getAccountRole(String username) {
		var account = accountRepository.findById(username).get();
		return account.getRole();
	}

	@Override
	public Boolean checkExistingAccount(String username) {
		var totalUser = accountRepository.count(username);
		return (totalUser > 0) ? true : false;
	}

	@Override
	public void changePassword(ChangePasswordDTO dto) {
		var account = accountRepository.findById(dto.getUsername()).get();
		var hashPassword = passwordEncoder.encode(dto.getNewPassword());
		account.setPassword(hashPassword);
		accountRepository.save(account);
	}

	@Override
	public Boolean checkPassword(String username, String password) {
		var account = accountRepository.findById(username);
		var authenticated = false;
		if(!account.isEmpty()){
			var entity = account.get();
			authenticated = passwordEncoder.matches(password, entity.getPassword());
		}
		return authenticated;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		var account = accountRepository.findById(username).get();
		var user = new ApplicationUserDetails(account);
		if(user == null){
			throw new UsernameNotFoundException("User not found");
		}
		return user;
	}
}
