package com.Docteur.Enterprise.Services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Docteur.Enterprise.Dto.NewPasswordDto;
import com.Docteur.Enterprise.Dto.ResponseAuthDto;


import com.Docteur.Enterprise.Dto.SignupRequestDto;
import com.Docteur.Enterprise.Entities.Employee;
import com.Docteur.Enterprise.Entities.Role;
import com.Docteur.Enterprise.Enum.TypeRole;
import com.Docteur.Enterprise.MapperDto.EmployeeMapperDto;
import com.Docteur.Enterprise.Repositories.EmployeeRepository;
import com.Docteur.Enterprise.Repositories.RoleRepository;

import jakarta.persistence.EntityNotFoundException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;



@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService  implements  UserDetailsService{
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final EmployeeMapperDto employeeMapperDto;
  
   

    public void RegisterUserService(SignupRequestDto request,TypeRole typeRole) {
        

        validateEmail(request.getEmail());
        checkUserAlreadyExists(request.getEmail());

        Employee employee = buildUser(request,typeRole);
       
        this.employeeRepository.save(employee);
        log.info("User successfully created with email: {}", employee.getEmail());

        
    }

    // ===================== PRIVATE METHODS =====================

    private void checkUserAlreadyExists(String email) {
        if (employeeRepository.existsByEmail(email)) {
            throw new RuntimeException("User exist already");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("EMAIL NOT VALID");
        }
    }

    

    private Employee buildUser(SignupRequestDto request, TypeRole typeRole) {
    
          // 🔥 Récupérer le rôle EXISTANT (ne jamais le créer ici)
        Role role = roleRepository.findByLibele(TypeRole.WELCOME)
                .or(()-> roleRepository.findByLibele(TypeRole.TECHNICIAN))
                .orElseThrow(() -> new RuntimeException("ROLE USER NOT FOUND"));
                

            return Employee.builder()
                    .name(request.getName())
                    .surname(request.getSurname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .phone(request.getPhone())
                    .role(role)
                    .active(true)
                    .build();
}

    
    @Override
    public UserDetails loadUserByUsername(String email) {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Employee doesn't found : " + email));
    }



    //nouveau mot de passe
    
        public ResponseEntity<?> newPassword(NewPasswordDto nouveauMotDePasse) {
            // 1. Vérification du DTO (pour éviter une recherche inutile si les mdp ne correspondent pas)
            if (!nouveauMotDePasse.getPassword1().equals(nouveauMotDePasse.getPassword2())) {
                throw new IllegalArgumentException("password doesn't match.");
            }

            // 2. Recherche de l'employé avec gestion propre de l'absence (Optional)
            Employee subscriber = this.employeeRepository.findByEmail(nouveauMotDePasse.getEmail())
                    .orElseThrow(() -> new EntityNotFoundException(nouveauMotDePasse.getEmail()+ " doesn't found " ));

            
            // 3. Encodage et sauvegarde
            String mdp = passwordEncoder.encode(nouveauMotDePasse.getPassword1());
            subscriber.setPassword(mdp);
            
            Employee savedEmployee =  this.employeeRepository.save(subscriber);
            log.info("Password updated : {}", subscriber.getEmail());

            return ResponseEntity.ok(savedEmployee);

        }



        //read an employee

        public Optional<ResponseAuthDto> ReadEmployeeById(Long id) {
            Optional<Employee> employeeFound = Optional.of(this.employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + id)));
                

                Optional<ResponseAuthDto>   autho =  employeeFound.map(employeeMapperDto);  
                log.info("Employee readed " + autho.get().getEmail());
    
                return autho;
            
            }



           
            

    }



