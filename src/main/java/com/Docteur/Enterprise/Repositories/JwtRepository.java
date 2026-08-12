package com.Docteur.Enterprise.Repositories;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.Employee;
import com.Docteur.Enterprise.Entities.Jwt;

@Repository
public interface JwtRepository extends  CrudRepository<Jwt, Long>{

     Optional<Jwt> findByValeur(String valeur);

    void deleteByExpirationAndDesactive(Boolean expiration,Boolean desactive);

    void deleteByValeur(String valeur);

    @Query("FROM Jwt j WHERE  j.employee.email=:email and j.desactive= :expire and j.expiration=: expire" )
    Optional <Jwt> findBytoken(String email, Boolean desactive,Boolean expire);

    @Query("FROM Jwt j WHERE  j.employee.email=:email")
    Stream <Jwt>  findByEmployee(String email);

    @Query("FROM Jwt j WHERE  j.refreshToken.valeur=:valeur")
    Optional <Jwt> findByRefreshToken(String valeur);


    void deleteAllByValeur(String valeur);

    Optional<Jwt>  findByEmployee(Employee employe);

    Optional<Jwt> findByEmployeeAndExpirationFalseAndDesactiveFalse(Employee employee);


}
