package com.greatlearning.bed.ems.assesment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.greatlearning.bed.ems.assesment.dto.UserRegistrationDto;
import com.greatlearning.bed.ems.assesment.model.NewEmployeeRegister;
import com.greatlearning.bed.ems.assesment.model.SearchOrderEmployee;
import com.greatlearning.bed.ems.assesment.service.UserServiceImpl;

@Controller
public class HomeController {

	
	@Autowired
	UserServiceImpl userServiceImplService;
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	@GetMapping("/")
	public String home(Model model) {
		
		model.addAttribute("listofAllEmployee", userServiceImplService.getAll());
		return "index";
	}
	
	
	 
	  @GetMapping("/deleteEmployee/{id}")
	  public String deleteEmployee(@PathVariable(value = "id") long id) {

	      // call delete employee method 
	      this.userServiceImplService.deleteEmployeeById(id);
	      return "redirect:/";
	  }
	 
	  
	  @GetMapping("/viewEmployee/{id}")
	  public String viewEmployee(@PathVariable(value = "id") long id, Model model) {

	      // get employee from the service
		  NewEmployeeRegister newEmployee = userServiceImplService.getNewEmployeeById(id);

	      // set employee as a model attribute to pre-populate the form
	      model.addAttribute("viewlogin", newEmployee);
	      return "view_employee";
	  }
	  
	  
	  @GetMapping("/employeeUpdateData/{id}")
	    public String employeeUpdateData(@PathVariable(value = "id") int id, Model model) {

	        // get employee from the service
		  NewEmployeeRegister newEmployee = userServiceImplService.getEmployeeById(id);

	        // set employee as a model attribute to pre-populate the form
	        model.addAttribute("updateEmployee", newEmployee);
	        return "update_employee";
	    }
	  @PostMapping("/updateEmployee")
	  public String updateEmployee(@ModelAttribute("updateEmployee") NewEmployeeRegister newEmployee) {
	     
	  	
	  	this.userServiceImplService.updateUmployeeById(newEmployee);
	      return "redirect:/";
	  }
	  
	  
	  @PostMapping("/viewlogin")
	  public String viewloginEmployee() {

	    
	      return "redirect:/";
	  }
	  
	  @GetMapping("/searchByorder")
	  public String searchByOrder() {
		
		  return "searchByorder_employee";
	  }
	   
	  @PostMapping("/searchEmployeeorder")
	  public String searchEmployeeorder(@ModelAttribute("orderBy") String searchByorder,Model model) {
		System.out.println("searchByorder "+searchByorder);
		
		model.addAttribute("listofAllOrderEmployee", userServiceImplService.orderByEmployeeSearch(searchByorder));
		  return "searchByorder_employee";
	  }
	   
	  
}
