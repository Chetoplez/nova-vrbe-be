package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.GuildBankDTO;
import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.dto.GuildMemberListDTO;
import com.novavrbe.vrbe.dto.GuildRoleDTO;
import com.novavrbe.vrbe.models.guildcontroller.Guild;
import com.novavrbe.vrbe.models.guildcontroller.GuildMember;
import com.novavrbe.vrbe.models.guildcontroller.GuildRole;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

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

    public static List<GuildRole> prepareGuildRoles(@NotNull List<GuildRoleDTO> roleDTO) {
        List<GuildRole> _tmp = new ArrayList<>();
        for (GuildRoleDTO role: roleDTO) {
            GuildRole guildRole = new GuildRole();

            guildRole.setGuild_id(role.getGuild_id());
            guildRole.setRole_id(role.getRole_id());
            guildRole.setName(role.getName());
            guildRole.setSalary(role.getSalary());
            guildRole.setIsManager(role.getIsManager());
            guildRole.setRole_img(role.getRole_img());
            guildRole.setDescription(role.getDescription());
            guildRole.setGuild_level(role.getGuild_level());

            _tmp.add(guildRole);
        }

        return _tmp;
    }

    public static GuildMember getMemberfromDTO(@NotNull GuildMemberListDTO tmp) {
        GuildMember newMember = new GuildMember();
        newMember.setCHARACTER_ID(tmp.getCHARACTER_ID());
        newMember.setROLE_ID(tmp.getROLE_ID());
        newMember.setGUILD_ID(tmp.getGUILD_ID());
        newMember.setGUILD_LEVEL(tmp.getGUILD_LEVEL());
        newMember.setCHARACTER_NAME(tmp.getCHARACTER_NAME());
        newMember.setROLE_IMG(tmp.getROLE_IMG());
        newMember.setROLE_NAME(tmp.getROLE_NAME());

        return newMember;
    }
}
