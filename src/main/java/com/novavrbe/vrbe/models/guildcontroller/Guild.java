package com.novavrbe.vrbe.models.guildcontroller;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.net.URL;

/**
 * Classe che modella le nostre Corporazioni
 */
@Data
public class Guild {


    private Integer id;

    private String name;

    private String description;

    private String guild_img;

    private Boolean isVisible;

    private String statute;

    private String announcement;

    private Integer bankAmount;

}
