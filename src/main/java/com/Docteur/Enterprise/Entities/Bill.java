package com.Docteur.Enterprise.Entities;

import java.math.BigDecimal;
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
@Table(name="bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    
    private LocalDateTime factureCreated;

    @Column(precision=10,scale=2)//ten numbers and 2 after comma
    private BigDecimal totalAmount;// repair amount

    @Column(precision=10,scale=2)//ten numbers and 2 after comma
    private BigDecimal amountPayed;// amount payed

    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "bill_id")
    private StatusBill statusBill;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "repairfolder")
    private RepairFolder repairFolder;


}
