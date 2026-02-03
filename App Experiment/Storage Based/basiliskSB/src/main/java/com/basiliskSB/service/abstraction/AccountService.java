package com.basiliskSB.service.abstraction;
import com.basiliskSB.dto.account.ChangePasswordDTO;
import com.basiliskSB.dto.account.RegisterDTO;

public interface AccountService {
	public void registerAccount(RegisterDTO dto);
	public String getAccountRole(String username);
	public Boolean checkExistingAccount(String username);
	public void changePassword(ChangePasswordDTO dto);
	public Boolean checkPassword(String username, String password);
}
