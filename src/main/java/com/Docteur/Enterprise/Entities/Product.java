package com.Docteur.Enterprise.Entities;

import java.util.Set;

import com.Docteur.Enterprise.Enum.ProductType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;

    private String serie;

    @Column(unique = true, nullable = false)
    private String imei;

    @Enumerated(EnumType.STRING)
    private ProductType producType;

    @OneToOne(fetch=FetchType.EAGER)
    private Image image;

    
    // ✅ PLUSIEURS réparations pour ce produit (RepairFolder existe déjà)
    @OneToMany(fetch = FetchType.LAZY)
    private Set<RepairFolder> folders;

   


}
