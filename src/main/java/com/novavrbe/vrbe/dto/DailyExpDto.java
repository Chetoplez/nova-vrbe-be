package com.novavrbe.vrbe.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity

public class DailyExpDto
{
    @Id
    @Column
    private Integer characterId;
    @Column
    private Integer dailyExp;
    @Column
    private Date expDate;

}
