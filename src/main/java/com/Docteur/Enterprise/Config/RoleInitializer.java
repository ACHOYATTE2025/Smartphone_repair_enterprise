package com.Docteur.Enterprise.Config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.Docteur.Enterprise.Entities.Role;
import com.Docteur.Enterprise.Enum.TypeRole;
import com.Docteur.Enterprise.Repositories.RoleRepository;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class RoleInitializer {

      private final RoleRepository roleRepository;

    @Bean
    CommandLineRunner initRoles() {
        return args -> {
            if (roleRepository.findByLibele(TypeRole.WELCOME).isEmpty()) {
                roleRepository.save(new Role( TypeRole.WELCOME));
            }
             if (roleRepository.findByLibele(TypeRole.TECHNICIAN).isEmpty()) {
                roleRepository.save(new Role( TypeRole.TECHNICIAN));
            }
            if (roleRepository.findByLibele(TypeRole.ADMIN).isEmpty()) {
                roleRepository.save(new Role( TypeRole.ADMIN));
            }

            
        };
    }

}
