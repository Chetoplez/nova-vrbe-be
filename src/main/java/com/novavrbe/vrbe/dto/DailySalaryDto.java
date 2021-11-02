package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@Entity
@Table(name = "DailySalary")

public class DailySalaryDto {
    @Id
    @Column
    private Integer characterId;

    @Column
    private Date lastSalary;

    @Column
    private Date blockedUntil;
}
