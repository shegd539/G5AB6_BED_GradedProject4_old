package com.greatlearning.bed.ems.assesment.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.greatlearning.bed.ems.assesment.dto.UserRegistrationDto;
import com.greatlearning.bed.ems.assesment.model.Role;
import com.greatlearning.bed.ems.assesment.model.NewEmployeeRegister;
import com.greatlearning.bed.ems.assesment.repository.OrderByDESCEmployeeRepository;
import com.greatlearning.bed.ems.assesment.repository.UpdateEmployeeRepository;
import com.greatlearning.bed.ems.assesment.repository.UserRepository;
import com.greatlearning.bed.ems.assesment.repository.OrderByASCEmployeeRepository;;

@Service
public class UserServiceImpl implements UserService {

	private UserRepository userRepository;
	
	
	
	@Autowired
	UpdateEmployeeRepository updateEmployeeRepository;
	
	@Autowired
	OrderByDESCEmployeeRepository orderByDESCEmployeeRepository;
	
	@Autowired
	OrderByASCEmployeeRepository orderByASCEmployeeRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public UserServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public NewEmployeeRegister save(UserRegistrationDto registrationDto) {

		NewEmployeeRegister user = new NewEmployeeRegister(registrationDto.getFirstName(), registrationDto.getLastName(), registrationDto.getEmail(),
				passwordEncoder.encode(registrationDto.getPassword()), 
				Arrays.asList(new Role("ROLE_USER")),registrationDto.getRole());

		return userRepository.save(user);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		NewEmployeeRegister user = userRepository.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {

		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
	}

	//@Override
	public List<NewEmployeeRegister> getAll() {

		return userRepository.findAll();
	}
	
	
	public void deleteEmployeeById(long id) {
	    this.userRepository.deleteById(id);
	}
	
	
	
	public NewEmployeeRegister getNewEmployeeById(long id) {
	    Optional < NewEmployeeRegister > optional = userRepository.findById(id);
	    NewEmployeeRegister employee = null;
	    if (optional.isPresent()) {
	        employee = optional.get();
	        
	        System.out.println("Records updated ");
	    } else {
	        throw new RuntimeException(" Employee not found for id :: " + id);
	    }
	    return employee;
	}
	
	public NewEmployeeRegister getEmployeeById(long id) {
	    Optional < NewEmployeeRegister > optional = userRepository.findById(id);
	    NewEmployeeRegister employee = null;
	    if (optional.isPresent()) {
	        employee = optional.get();
	        System.out.println("employee first name "+employee.getFirstName());
	       updateEmployeeRepository.updateEmployeeRecord(optional.get().getFirstName(),optional.get().getLastName(), optional.get().getPassword(), optional.get().getEmail(), id);
	        System.out.println("Records updated ");
	    } else {
	        throw new RuntimeException(" Employee not found for id :: " + id);
	    }
	    return employee;
	}

	public void updateUmployeeById(NewEmployeeRegister newEmployee) {
		System.out.println("newEmployee "+newEmployee.getFirstName());
		updateEmployeeRepository.updateEmployeeRecord(newEmployee.getFirstName(),newEmployee.getLastName(), newEmployee.getPassword(), newEmployee.getEmail(), newEmployee.getId());;
	}
	
	
	public List<NewEmployeeRegister> orderByEmployeeSearch(String orderBy) {
		
		List<NewEmployeeRegister> employeSearchResult=new ArrayList<NewEmployeeRegister>();
		if(orderBy.equalsIgnoreCase("asc")) {
		
		employeSearchResult=orderByASCEmployeeRepository.orderByEmployeeAsc();
		}
		if(orderBy.equalsIgnoreCase("desc")) {
			
			employeSearchResult=orderByDESCEmployeeRepository.orderByEmployeedesc();
		}
		
		
		return employeSearchResult;
	}
	

}
