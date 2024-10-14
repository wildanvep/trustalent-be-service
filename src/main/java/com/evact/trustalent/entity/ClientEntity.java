package com.evact.trustalent.entity;


import com.evact.trustalent.common.CommonEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Getter
@Setter
@Table(name = "TB_M_CLIENT")
@AllArgsConstructor
@NoArgsConstructor
public class ClientEntity extends CommonEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "client_name")
    private String clientName;

    @Column(name = "is_active")
    private boolean active;
}
