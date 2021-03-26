package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.GuildBankDTO;
import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.models.guildcontroller.Guild;
import com.sun.istack.NotNull;

public class GuildUtils {

    public static Guild createGuildObject(@NotNull GuildDTO guildDTO,@NotNull GuildBankDTO bankDTO){
        Guild guild = new Guild();

        guild.setId(guildDTO.getId());
        guild.setName(guildDTO.getName());
        guild.setDescription(guildDTO.getDescription());
        guild.setGuild_img(guildDTO.getGuild_img());
        guild.setIsVisible(guildDTO.getIsVisible());
        guild.setAnnouncement(guildDTO.getAnnouncement());
        guild.setStatute(guildDTO.getStatute());
        guild.setBankAmount(bankDTO.getAmount());
        return guild;
    }
}
