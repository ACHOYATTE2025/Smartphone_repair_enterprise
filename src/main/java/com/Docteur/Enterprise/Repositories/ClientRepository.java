package com.Docteur.Enterprise.Repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.Client;
import com.Docteur.Enterprise.Entities.Employee;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    boolean existsByEmail(String email);

    Client findByNumberClient(String num);

    Optional<Client>  findByNumberClientAndEmployees(String num, Employee employee);

}
