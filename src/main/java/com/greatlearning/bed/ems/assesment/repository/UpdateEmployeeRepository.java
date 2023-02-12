package com.greatlearning.bed.ems.assesment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.greatlearning.bed.ems.assesment.model.NewEmployeeRegister;


@Repository
public interface UpdateEmployeeRepository extends JpaRepository<NewEmployeeRegister, Long>{
	@Transactional
	@Modifying
	@Query(value="update user u set u.first_name = :first_name,u.last_name = :last_name, u.password = :password, u.email = :email where u.id = :id", nativeQuery = true)
	void updateEmployeeRecord(@Param("first_name")String first_name,
			@Param("last_name")String last_name,
			@Param("password")String password,
			@Param("email")String email,
			@Param("id")long id);

}
