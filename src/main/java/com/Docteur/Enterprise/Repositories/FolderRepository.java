package com.Docteur.Enterprise.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.RepairFolder;

@Repository
public interface FolderRepository extends  JpaRepository<RepairFolder, Long> {

    
    

}
