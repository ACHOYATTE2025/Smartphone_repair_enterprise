package com.Docteur.Enterprise.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.Employee;
import com.Docteur.Enterprise.Entities.Validation;

@Repository
public interface ValidationRepository extends  JpaRepository<Validation, Long>{

    Optional<Validation> findByCode(String code);
    Optional<Validation> deleteByCode(String code);
    Optional<Validation> findById(long id);
    Optional<Validation> findByEmployee(Employee employee);

}
