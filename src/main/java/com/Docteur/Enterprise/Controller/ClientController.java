package com.Docteur.Enterprise.Controller;

import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Docteur.Enterprise.Dto.ResponseClientDto;
import com.Docteur.Enterprise.Dto.ResponseDto;
import com.Docteur.Enterprise.Dto.SignupClientDto;
import com.Docteur.Enterprise.Services.ClientService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;







@RestController
@RequiredArgsConstructor
@Slf4j

@Tag(
  name = "Client Controller",
  description="Controller REST Api for  details"
)
public class ClientController {

    private final ClientService clientService;
   

/********************************************************************************************************************
*
*                                                     CLIENT BLOCK
*
*
***********************************************************************************************************************/



    /*CLIENT SAVING */
@Operation(
    summary="REST API to register Employee ",
    description = "REST API to register Employee "
  )
    @PostMapping(value="/registerclient",
              produces = MediaType.APPLICATION_JSON_VALUE
            )
  public ResponseEntity<ResponseDto> registerClient( @RequestBody @Valid SignupClientDto request)throws Exception {
        this.clientService.RegisterClientService(request);
        log.info("Client saved: " + request.getEmail()); 
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .contentType(MediaType.APPLICATION_JSON)
            .body(new ResponseDto(201,"USER CREATED SUCCESSFULLY","OK"));
       
  }




    /*CLIENT UPDATED */
    @Operation(
      summary="REST API to update Client informations",
      description = "REST API to updated informations"
    )
   @PutMapping(
        value = "/updateclient/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE,
        consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ResponseDto> updateclient(@PathVariable("id") Long id,@RequestBody @Valid SignupClientDto request
    ) {
        this.clientService.updateClientService(id, request);
        log.info("Client updated: " + request.getEmail());
        return ResponseEntity.ok(
            new ResponseDto(200, "CLIENT UPDATED SUCCESSFULLY", "OK")
        );
    }



    /* CLIENT SEARCH */

//Read one or all clients by number
 @Operation(
    summary="REST API to look for Client by number",
    description = "REST API to look for client by number  "
  )
@GetMapping(path="/clientsearch")
public Stream<ResponseClientDto> searchClient(@RequestParam(required = false)  String num){

     Stream<ResponseClientDto> clientFounded  = this.clientService.ReadClient(num);
     log.info("client fetch N° "+ clientFounded);
     return clientFounded;
}




//Read client by Id
 @Operation(
    summary="REST API to getclient by Id",
    description = "REST API to get Client By Id"
  )
@GetMapping(path="/clientsearchbyid/{id}")
Optional<ResponseClientDto> searchClientbyId(@Valid @PathVariable(required = true)Long id ){
  Optional<ResponseClientDto> bix = this.clientService.ReadClientById(id);
  log.info("client fetch by id N° "+ bix.get().getPhoneNumber());
  return bix;
}











} 
