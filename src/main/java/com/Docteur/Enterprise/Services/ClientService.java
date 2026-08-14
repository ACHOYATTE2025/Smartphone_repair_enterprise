package com.Docteur.Enterprise.Services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.logging.log4j.util.Strings;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.Docteur.Enterprise.Dto.ResponseClientDto;
import com.Docteur.Enterprise.Dto.SignupClientDto;
import com.Docteur.Enterprise.Entities.Client;
import com.Docteur.Enterprise.Entities.Employee;
import com.Docteur.Enterprise.MapperDto.ClientMapperDto;
import com.Docteur.Enterprise.Repositories.ClientRepository;
import com.Docteur.Enterprise.Repositories.EmployeeRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final EmployeeRepository employeeRepository;
    private final ClientMapperDto clientMapperDto;




    @Transactional
    public void RegisterClientService(SignupClientDto request) {
    
        
        // 1. Récupérer l'employé connecté depuis la session Spring Security
        Employee currentEmployee = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        validateEmail(request.getEmail());
        checkClientAlreadyExists(request.getEmail());

        Client client = buildClient(request);
        Client savedClient = this.clientRepository.save(client);
        log.info("Client successfully created with email: {}", client.getEmail());

        // 3. Associer le client à l'employé (C'est cette étape qui enregistre la relation en BD !)
        currentEmployee.setClient(savedClient);

        // 4. Sauvegarder l'employé pour mettre à jour la clé étrangère `client_id`
            this.employeeRepository.save(currentEmployee);
           

        
    }

    // ===================== PRIVATE METHODS =====================

    private void checkClientAlreadyExists(String email) {
        if (clientRepository.existsByEmail(email)) {
            throw new RuntimeException("User exist already");
        }
    }

    private void validateEmail(String email) {
        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("EMAIL NOT VALID");
        }
    }

    

    private Client buildClient(SignupClientDto request) {
    

                   

            return Client.builder()
                    .numberClient("Client-"+UUID.randomUUID().toString())
                    .email(request.getEmail())
                    .phoneNumber(request.getPhoneNumber())
                    .fullName(request.getFullName())
                    .clientCreated(LocalDateTime.now())
                    .build();
}




//mise à jour du client
     @Transactional
    public void updateClientService(Long clientId, SignupClientDto request) {
        // 1. Recherche du client existant
        Client client = this.clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client non trouvé avec l'ID : " + clientId));

        // 2. Vérifier si le nouvel email est déjà utilisé par un AUTRE client
        if (!client.getEmail().equals(request.getEmail()) 
                && this.clientRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Un autre client utilise déjà cet email : " + request.getEmail());
        }

        // 3. Mise à jour des informations
        client.setFullName(request.getFullName());
        client.setPhoneNumber(request.getPhoneNumber());
        client.setEmail(request.getEmail());

        log.info("Client updated successfully (ID: {})", clientId);

        // 4. Sauvegarde
          this.clientRepository.save(client);
    }




        /*client search */

     public Stream<ResponseClientDto> ReadClient(String num) {
            boolean notEmpty = Strings.isNotEmpty(num);
            Employee usex = (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                   
            if(notEmpty)
                {Optional<Client>  client = this.clientRepository.findByNumberClient(num);
                    if(client==null){throw new RuntimeException("Client doesn't exist");}
                
                log.info("Client Found successfully )"+ client.get().getEmail());
                return this.clientRepository.findByNumberClient(num)
                    .stream()
                    .map(clientMapperDto);
                }
            else{
                    List<Client> clients = (List<Client>) this.clientRepository.findAll();
                    if(clients.isEmpty()){throw new RuntimeException( "Any client founded") ; }
                    log.info("Clients Found successfully )"+ clients.stream().map(clientMapperDto));
                    return  clients.stream().map(clientMapperDto);
                }

           
          
  }

  /*client searched by Id */

     public Optional<ResponseClientDto> ReadClientById(Long id) {
       
            if(id==null){throw new RuntimeException("ID not found");}
            Optional<Client> clientes = this.clientRepository.findById(id);
            if(clientes.isEmpty()){throw new RuntimeException("Client doesn't exist!!!");}

            Optional<ResponseClientDto> alex=  clientes.map(clientMapperDto);
             log.info("Client Found successfully )"+ alex.get().getEmail());
            return alex;
     }
     

}




     


