package com.greatlearning.bed.ems.assesment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.greatlearning.bed.ems.assesment.model.NewEmployeeRegister;

@Repository
public interface OrderByDESCEmployeeRepository extends JpaRepository<NewEmployeeRegister, Long>{


	@Query(value="select * from user ORDER BY first_name DESC", nativeQuery = true)
	public List<NewEmployeeRegister> orderByEmployeedesc();

}
