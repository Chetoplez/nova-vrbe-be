package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.GuildBankDTO;
import com.novavrbe.vrbe.dto.GuildDTO;
import com.novavrbe.vrbe.dto.V_GuildMembers;
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

            guildRole.setGuild_id(role.getGuildId());
            guildRole.setRole_id(role.getRoleId());
            guildRole.setName(role.getName());
            guildRole.setSalary(role.getSalary());
            guildRole.setIsManager(role.getIsManager());
            guildRole.setRole_img(role.getRole_img());
            guildRole.setDescription(role.getDescription());
            guildRole.setGuild_level(role.getGuildLevel());

            _tmp.add(guildRole);
        }

        return _tmp;
    }

    public static GuildMember getMemberfromDTO(@NotNull V_GuildMembers tmp) {
        GuildMember newMember = new GuildMember();
        newMember.setCHARACTER_ID(tmp.getCharacterId());
        newMember.setROLE_ID(tmp.getRoleId());
        newMember.setGUILD_ID(tmp.getGuildId());
        newMember.setGUILD_LEVEL(tmp.getGuildLevel());
        newMember.setCHARACTER_NAME(tmp.getCharacterName());
        newMember.setROLE_IMG(tmp.getRoleImg());
        newMember.setROLE_NAME(tmp.getRoleName());
        newMember.setROLESALARY(tmp.getRoleSalary());

        return newMember;
    }

    public static List<Guild> prepareGuildList(Iterable<GuildDTO> allGuilds) {
        ArrayList<Guild> guildList = new ArrayList<>();
        for (GuildDTO guildItem: allGuilds) {
            if(guildItem.getIsVisible()) {
                Guild temp = new Guild();
                temp.setId(guildItem.getId());
                temp.setName(guildItem.getName());
                temp.setGuild_img(guildItem.getGuild_img());
                temp.setDescription(guildItem.getDescription());
                temp.setStatute(guildItem.getStatute());
                temp.setIsVisible(guildItem.getIsVisible());
                temp.setAnnouncement(guildItem.getAnnouncement());
                temp.setBankAmount(0);
                guildList.add(temp);
            }
        }
        return guildList;
    }
}
