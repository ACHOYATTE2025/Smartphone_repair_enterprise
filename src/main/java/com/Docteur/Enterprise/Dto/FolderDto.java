package com.Docteur.Enterprise.Dto;

import java.math.BigDecimal;

import com.Docteur.Enterprise.Entities.Product;
import com.Docteur.Enterprise.Entities.StatusFolder;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FolderDto {
    
    private String detailsClientIssue;// issue of smartphone from clients

    private String diagnosticTetch;// issue diagnostic form technician

    private StatusFolder  status;//diffents kind of product status

    @Column(precision=10,scale=2)//ten numbers and 2 after comma
    private BigDecimal price;// repair price

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name= "productId")
    private Product product;



}
