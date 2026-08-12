package com.Docteur.Enterprise.Repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.RefreshToken;

@Repository
public interface RefreshTokenRepository extends  CrudRepository<RefreshToken, Long>{
     Optional <RefreshToken> findByValeur(String valeur);


}
