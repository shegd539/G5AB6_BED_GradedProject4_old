package com.greatlearning.bed.ems.assesment.service;

import java.util.List;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.greatlearning.bed.ems.assesment.dto.UserRegistrationDto;
import com.greatlearning.bed.ems.assesment.model.NewEmployeeRegister;

public interface UserService extends UserDetailsService {

	NewEmployeeRegister save(UserRegistrationDto registrationDto);

	List<NewEmployeeRegister> getAll();
}
