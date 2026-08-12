package com.Docteur.Enterprise.Controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.Docteur.Enterprise.Dto.ErroResponseDto;
import com.Docteur.Enterprise.Dto.LoginRequestDto;
import com.Docteur.Enterprise.Dto.NewPasswordDto;
import com.Docteur.Enterprise.Dto.RefreshTokenDto;
import com.Docteur.Enterprise.Dto.ResponseAuthDto;
import com.Docteur.Enterprise.Dto.ResponseDto;
import com.Docteur.Enterprise.Dto.SignupRequestDto;
import com.Docteur.Enterprise.Dto.SignupResponseDto;
import com.Docteur.Enterprise.Entities.Employee;
import com.Docteur.Enterprise.Enum.TypeRole;
import com.Docteur.Enterprise.MapperDto.EmployeeMapperDto;
import com.Docteur.Enterprise.Repositories.EmployeeRepository;
import com.Docteur.Enterprise.Services.AuthService;
import com.Docteur.Enterprise.Services.JwtService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(
  name = "Authentification",
  description="AUTHENTIFICATION REST Api in Smartphone Repair Enterprise management APP to CREATE  details"
)
@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController {
    private  final AuthService authService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapperDto employeeMapperDto;
    
  


    //User registration
  
//create
  @Operation(
    summary="REST API to create new User in Smartphone Repair Enterprise management",
    description = "REST API to create new Account in Smartphone Repair Enterprise management "
  )

  @ApiResponse(
    responseCode="201",
    description = "USER CREATED SUCCESSFULLY"
  )
  @PreAuthorize("hasRole('ADMIN')")
  @PostMapping(value="/registeremployee",
              produces = MediaType.APPLICATION_JSON_VALUE
            )
  public ResponseEntity<ResponseDto> registerEmployee( @RequestBody @Valid SignupRequestDto request, TypeRole role)throws Exception {
         this.authService.RegisterUserService(request,role);
         log.info("Employee saved: " + request.getEmail());
    
         return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ResponseDto(201,"USER CREATED SUCCESSFULLY","OK"));
       
  }


  //login

  @Operation(
    summary="REST API to login  Employee",
    description = "REST API for Employee to login"
  )

  @ApiResponses({
    @ApiResponse(
        responseCode="200",
        description = "HTTP Status DONE",
        content = @Content(
            schema = @Schema(implementation = ResponseDto.class)) ),
    
    @ApiResponse(   

        description = "Login  failed!!!",
        content = @Content(
            schema = @Schema(implementation = ErroResponseDto.class)
        )
    )
    }
  )
@PostMapping("/login")
 public ResponseEntity<?> login( @RequestBody LoginRequestDto request) {
    log.info("Email reçu: " + request.getEmail());
    
         try {
              Authentication authentication = authenticationManager.authenticate(
                  new UsernamePasswordAuthenticationToken(
                      request.getEmail(),
                      request.getPassword()
                  )
        );

              Employee user = (Employee) authentication.getPrincipal();
              SignupResponseDto tokens = jwtService.generateAndSaveToken(user);

              return ResponseEntity
                  .ok()
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(tokens);

    } catch (AuthenticationException ex) {

        return ResponseEntity
            .status(HttpStatus.UNAUTHORIZED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ResponseDto(401, "LOGIN ERROR", "BAD CREDENTIALS"));
    }
                
    }




    

//refresh Token
@Operation(
    summary="REST API to make refreshtoken ",
    description = "REST API to make refreshtoken  "
  )
//@PreAuthorize("hasAnyRole('WELCOME,'TECHNICIAN',ADMIN')")
  @PostMapping("/refreshtoken")
  public  SignupResponseDto refreshToken(@RequestBody RefreshTokenDto refreshTokenRequest) {
       return this.jwtService.refreshtoken(refreshTokenRequest);
       }



  
 //nouveau mot de passe
 @Operation(
    summary="REST API to make new password ",
    description = "REST API to make new  "
  )
  @ResponseStatus(value = HttpStatus.CREATED)
  @PostMapping(path = "/renewpassword")
    public ResponseEntity<?> newPassword(@RequestBody NewPasswordDto NouveauMotDePasse) throws Throwable {
        this.authService.newPassword(NouveauMotDePasse);
        log.info("Password updated : " + NouveauMotDePasse.getEmail());
    
        return ResponseEntity
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ResponseDto(201, "OK", "PASSWORD UPDATED  "));
    }
         
/*delete employee */

@Operation(
    summary="REST API to delete an employee ",
    description = "REST API  to delete an employee  "
  )
@PreAuthorize("hasRole('ADMIN')")
@ResponseStatus(value = HttpStatus.GONE)
@PostMapping(path="/deleteemployee")
public void deleteemployee( @Valid @RequestParam(required = true)Long id) {
      this.employeeRepository.deleteById(id);
    
    
}





//Read Employeeby Id
 @Operation(
    summary="REST API to get Employee by Id",
    description = "REST API to get Employee By Id"
  )
@PreAuthorize("hasRole('ADMIN')")
@GetMapping(path="/employeesearchbyid/{id}")
Optional<ResponseAuthDto> employeesearchbyId(@Valid @RequestParam(required = true)Long id ){
  Optional<ResponseAuthDto> bix = this.authService.ReadEmployeeById(id);
  log.info("client fetch by id N° "+ bix.get().getEmail());
  return bix;
}

     //read all employee


// Lire TOUS les employés
@Operation(
    summary="REST API to get all Employee",
    description = "REST API to get all Employee"
  )
@PreAuthorize("hasRole('ADMIN')")
@GetMapping(path="/employeesearchall")
public List<ResponseAuthDto> readAllEmployees() {
            return employeeRepository.findAll().stream()
                .map(employeeMapperDto)
                .collect(Collectors.toList());
        }

}
