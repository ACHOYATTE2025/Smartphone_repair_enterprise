package com.Docteur.Enterprise.Entities;

import com.Docteur.Enterprise.Enum.StatutsPaymentEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@NoArgsConstructor
@Table(name = "statusbill",uniqueConstraints = @UniqueConstraint(columnNames = "valuebill"))
public class StatusBill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  
  @Column(unique = true,nullable = false)
  @Enumerated(EnumType.STRING)
  private StatutsPaymentEnum  valuebill;
}
