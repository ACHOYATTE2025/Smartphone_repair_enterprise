package com.Docteur.Enterprise.Repositories;

import java.util.Optional;
import java.util.function.Supplier;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Docteur.Enterprise.Entities.Role;
import com.Docteur.Enterprise.Enum.TypeRole;


@Repository
public interface RoleRepository extends  JpaRepository<Role, Long>{

      Optional< Role> findByLibele(TypeRole libele);

}
