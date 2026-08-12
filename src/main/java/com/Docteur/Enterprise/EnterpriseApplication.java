package com.Docteur.Enterprise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.Docteur.Enterprise.Entities.Employee;
import com.Docteur.Enterprise.Entities.Role;
import com.Docteur.Enterprise.Enum.TypeRole;
import com.Docteur.Enterprise.Repositories.EmployeeRepository;
import com.Docteur.Enterprise.Repositories.RoleRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@EnableScheduling
@AllArgsConstructor
@SpringBootApplication
public class EnterpriseApplication implements CommandLineRunner {

    private final PasswordEncoder passwordEncoder;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(EnterpriseApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        // 1️⃣ Récupérer le rôle ADMIN ou le créer S'IL N'EXISTE PAS ENCORE en BD
        Role adminRole = this.roleRepository.findByLibele(TypeRole.ADMIN)
                .orElseGet(() -> {
                    log.info("Création du rôle ADMIN...");
                    return this.roleRepository.save(
                        Role.builder()
                            .libele(TypeRole.ADMIN)
                            .build()
                    );
                });

        // 2️⃣ Créer l'utilisateur ADMIN s'il n'existe pas
        this.employeeRepository.findByEmail("acho.quebec@gmail.com")
                .ifPresentOrElse(
                    employee -> log.info("L'utilisateur ADMIN existe déjà en base de données."),
                    () -> {
                        Employee newAdmin = Employee.builder()
                                .name("ACHO")
                                .surname("YATTE DEIVY CONSTANT")
                                .email("acho.quebec@gmail.com")
                                .password(passwordEncoder.encode("dreamcast"))
                                .role(adminRole) // On passe l'objet récupéré/créé en toute sécurité
                                .active(true)
                                .build();
                        
                        this.employeeRepository.save(newAdmin);
                        log.info("Utilisateur ADMIN créé avec succès !");
                    }
                );
    }
}