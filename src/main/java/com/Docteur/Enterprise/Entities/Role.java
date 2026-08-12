package com.Docteur.Enterprise.Entities;

import com.Docteur.Enterprise.Enum.TypeRole;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "role",uniqueConstraints = @UniqueConstraint(columnNames = "libele"))
public class Role {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

 
  @Column(unique = true,nullable = false)
  @Enumerated(EnumType.STRING)
  private TypeRole libele;


  // ✅ Constructeur correct
    public Role(TypeRole libele) {
        this.libele = libele;
    }

}
