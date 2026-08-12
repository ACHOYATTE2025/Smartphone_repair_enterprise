package com.Docteur.Enterprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

}
