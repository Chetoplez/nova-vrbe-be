package com.novavrbe.vrbe.models;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Table
@Entity
@Data
public class Guild {

    @Id
    private BigDecimal id;
    @Column
    private String name;


}
