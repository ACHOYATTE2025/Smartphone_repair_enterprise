package com.Docteur.Enterprise.MapperDto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.Docteur.Enterprise.Dto.ResponseClientDto;
import com.Docteur.Enterprise.Entities.Client;

@Component
public class ClientMapperDto implements Function<Client, ResponseClientDto>{

     @Override
    public ResponseClientDto apply(Client client) {
        return new ResponseClientDto(client.getNumberClient(),client.getFullName(),client.getEmail(),
                client.getPhoneNumber());
    }

}
