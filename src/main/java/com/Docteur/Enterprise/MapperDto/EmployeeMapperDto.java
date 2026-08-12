package com.Docteur.Enterprise.MapperDto;

import java.util.function.Function;

import org.springframework.stereotype.Component;

import com.Docteur.Enterprise.Dto.ResponseAuthDto;
import com.Docteur.Enterprise.Entities.Employee;

@Component
public class EmployeeMapperDto implements Function<Employee, ResponseAuthDto>{

    @Override
    public ResponseAuthDto apply(Employee employee) {
        return new ResponseAuthDto(employee.getName(), employee.getSurname(), employee.getEmail(),employee.getPhone(),employee.getRole());
    }

}
