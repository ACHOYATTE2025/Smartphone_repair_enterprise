package com.Docteur.Enterprise.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.Employee;


@Repository
public interface EmployeeRepository extends  JpaRepository<Employee, Long>{

    boolean existsByEmail(String email);

     Optional<Employee> findByEmail(String email);

     void deleteById( Long Id);
   
}
