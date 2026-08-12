package com.Docteur.Enterprise.Entities;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="repairfolder")
public class RepairFolder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String repairNumber;// unique number of repair folder

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name= "productId")
    private Product product;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    private LocalDateTime receptionDate;

    private String detailsClientIssue;// issue of smartphone from clients

    private String diagnosticTetch;// issue diagnostic form technician

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "status_id")
    private StatusFolder  status;//diffents kind of product status

    @Column(precision=10,scale=2)//ten numbers and 2 after comma
    private BigDecimal price;// repair price

    private LocalDateTime diagnosticDateTime;

    private LocalDateTime paymentDate;// date of payment

    private LocalDate dropDatePrevision;// drop date scheluded

    private LocalDate dropDateLimit;// limit date of dropping

    private Instant repairEnd;//date of issue fixed

    private LocalDate archiveDate;// date of archived

    private String notes;

}
