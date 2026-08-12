package com.Docteur.Enterprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.Image;

@Repository
public interface ImageRepository extends  JpaRepository<Image, Long>{

}
