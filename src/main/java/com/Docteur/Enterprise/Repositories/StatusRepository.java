package com.Docteur.Enterprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.StatusFolder;


@Repository
public interface StatusRepository extends  JpaRepository<StatusFolder, Long>{

}
