package com.novavrbe.vrbe.utils;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.models.charactermodels.*;
import com.novavrbe.vrbe.models.enumerations.*;
import com.sun.istack.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CharacterUtils {

    static Logger logger = LoggerFactory.getLogger(CharacterUtils.class);
    static final Integer MIN_LEVEL = 1;
    static final Integer DEFAULT_HEALTH = 500;
    static final Integer DEFAULT_STAT = 5;

    public static void fillCharacterFieldsFromDto(Character character, CharacterDto dto){
        if(character != null && dto != null){
            character.setCharacterId(dto.toString());
            character.setCharacterName(dto.getCharacterName());
            try {
                URL characterIcon = new URL(dto.getCharacterIcon());
                character.setCharacterIcon(characterIcon);
            } catch (MalformedURLException e) {
                logger.error(e.toString());
            }
            character.setGender(Gender.valueOf(dto.getGender()));
            character.setStatus(Status.valueOf(dto.getStatus()));

            CharacterLevel characterLevel = new CharacterLevel();
            characterLevel.setLevel(dto.getLevel());
            characterLevel.setExperience(dto.getExperience());
            characterLevel.setTotalExperience(dto.getTotalExperience());
            character.setLevel(characterLevel);

            character.setHealth(dto.getHealth());
            character.setHealthStatus(HealthStatus.valueOf(dto.getHealthStatus()));

            ArrayList<Roles> roles = new ArrayList<>();
            Roles role = Roles.valueOf(dto.getRole());
            roles.add(role);
            character.setRoles(roles);
        }
    }

    public static void fillCharacterHistoryFromDto(Character character, CharacterHistoryDto historyDto){
        if(character != null && historyDto != null){
            CharacterHistory history = new CharacterHistory();
            history.setHistory(historyDto.getHistory());
            history.setHistoryId(historyDto.getHistoryId());

            character.setHistory(history);
        }
    }

    public static void fillCharacterDescriptionFromDto(Character character, CharacterDescriptionDto descriptionDto){
        if(character != null && descriptionDto != null){
            CharacterDescription description = new CharacterDescription();
            description.setDescriptionId(descriptionDto.getDescriptionId());
            description.setDescription(descriptionDto.getDescription());

            character.setDescription(description);
        }
    }

    public static void fillCharacterStatisticsFromDto(Character character, CharacterStatisticsDto characterStatisticsDto){
        if(character != null && characterStatisticsDto != null){
            ArrayList<CharacterStatistic> stats = new ArrayList<>();

            CharacterStatistic strenght = buildCharacterStat(Stat.FORZA, characterStatisticsDto.getForza(), characterStatisticsDto.getForzaModifier());
            stats.add(strenght);
            CharacterStatistic intelligence = buildCharacterStat(Stat.INTELLIGENZA, characterStatisticsDto.getIntelligenza(), characterStatisticsDto.getIntelligenzaModifier());
            stats.add(intelligence);
            CharacterStatistic wisdom = buildCharacterStat(Stat.SAGGEZZA, characterStatisticsDto.getSaggezza(), characterStatisticsDto.getSaggezzaModifier());
            stats.add(wisdom);
            CharacterStatistic dexterity = buildCharacterStat(Stat.DESTREZZA, characterStatisticsDto.getDestrezza(), characterStatisticsDto.getDestrezzaModifier());
            stats.add(dexterity);
            CharacterStatistic constitution = buildCharacterStat(Stat.COSTITUZIONE, characterStatisticsDto.getCostituzione(), characterStatisticsDto.getCostituzioneModifier());
            stats.add(constitution);

            character.setStats(stats);
        }
    }

    public static CharacterStatistic buildCharacterStat(Stat stat, Integer base, BigDecimal modifier){
        CharacterStatistic statistic = new CharacterStatistic();
        statistic.setStatName(stat);
        statistic.setBaseStat(base);
        statistic.setModified(modifier);

        return statistic;
    }

    public static void fillCharacterTemporaryEffectsFromDto(Character character, List<CharacterTemporaryEffectDto> effects){
        if(character != null && !CollectionUtils.isEmpty(effects)){
            ArrayList<CharacterTemporaryEffect> characterTemporaryEffects = new ArrayList<>();
            effects.stream()
                    .forEach(effect -> {
                        characterTemporaryEffects.add(buildCharacterTemporaryEffect(effect));
                    });

            character.setEffects(characterTemporaryEffects);
        }
    }

    public static CharacterTemporaryEffect buildCharacterTemporaryEffect(CharacterTemporaryEffectDto dto){
        CharacterTemporaryEffect effect = new CharacterTemporaryEffect();

        effect.setId(dto.getId());
        effect.setModifier(dto.getModifier());
        effect.setStat(Stat.valueOf(dto.getStat()));

        return effect;
    }

    public static CharacterDto buildCharacterDtoFromCharacter(Integer characterId, Character character){
        CharacterDto characterDto = null;
        if(character != null){
            characterDto = new CharacterDto();
            characterDto.setCharacterId(characterId);
            characterDto.setCharacterName(character.getCharacterName());
            characterDto.setCharacterIcon(character.getCharacterIcon() != null ? character.getCharacterIcon().toString() : "");
            characterDto.setGender(character.getGender().name());
            characterDto.setStatus(character.getStatus() != null ? character.getStatus().name() : Status.PLEBEO.name());
            characterDto.setHealth(character.getHealth() != null ? character.getHealth() : DEFAULT_HEALTH);
            characterDto.setHealthStatus(character.getHealthStatus() != null ? character.getHealthStatus().name() : HealthStatus.SAZIO.name());
            characterDto.setRole(!CollectionUtils.isEmpty(character.getRoles()) ? character.getRoles().get(0).name() : Roles.USER.name());

            buildCharacterLevelForDto(character, characterDto);
        }
        return characterDto;
    }

    public static void buildCharacterLevelForDto(Character character, CharacterDto characterDto){
        if(character != null && characterDto != null){
            if(character.getLevel() != null){
                characterDto.setLevel(character.getLevel().getLevel());
                characterDto.setExperience(character.getLevel().getExperience());
                characterDto.setTotalExperience(character.getLevel().getTotalExperience());
            }else{
                characterDto.setLevel(MIN_LEVEL);
                characterDto.setExperience(BigDecimal.ZERO);
                characterDto.setTotalExperience(BigDecimal.ZERO);
            }
        }
    }

    public static CharacterHistoryDto buildCharacterHistoryForDto(Integer id, Character character){
        CharacterHistoryDto historyDto = null;

        if(character != null){
            historyDto = new CharacterHistoryDto();
            historyDto.setHistoryId(id);
            if(character.getHistory() != null){
                historyDto.setHistory(character.getHistory().getHistory());
            }
        }

        return historyDto;
    }

    public static CharacterDescriptionDto buildCharacterDescriptionForDto(Integer id, Character character){
        CharacterDescriptionDto historyDto = null;

        if(character != null){
            historyDto = new CharacterDescriptionDto();
            historyDto.setDescriptionId(id);
            if(character.getDescription() != null){
                historyDto.setDescription(character.getDescription().getDescription());
            }
        }

        return historyDto;
    }

    public static CharacterStatisticsDto buildCharacterStatisticForDto(Integer id, Character character){
        CharacterStatisticsDto statisticsDto = null;

        if(character != null){
            statisticsDto = new CharacterStatisticsDto();
            statisticsDto.setCharacterId(id);
            if(!CollectionUtils.isEmpty(character.getStats())){
                mapStatListsToStatisticDto(character.getStats(), statisticsDto);
            }else{
                buildDefaultStatisticDto(statisticsDto);
            }
        }

        return statisticsDto;
    }

    public static InventoryDto buildInventoryForDto(Integer characterId, BigDecimal gold){
        InventoryDto inventoryDto = null;

        inventoryDto.setCharacterId(characterId);
        inventoryDto.setGold(gold);

        return inventoryDto;
    }

    public static void mapStatListsToStatisticDto(ArrayList<CharacterStatistic> stats, @NotNull CharacterStatisticsDto statisticsDto){
        stats.stream().forEach(stat -> {
            switch (stat.getStatName()){
                case FORZA:
                    statisticsDto.setForza(stat.getBaseStat());
                    statisticsDto.setForzaModifier(stat.getModified());
                    break;
                case SAGGEZZA:
                    statisticsDto.setSaggezza(stat.getBaseStat());
                    statisticsDto.setSaggezzaModifier(stat.getModified());
                    break;
                case DESTREZZA:
                    statisticsDto.setDestrezza(stat.getBaseStat());
                    statisticsDto.setDestrezzaModifier(stat.getModified());
                    break;
                case COSTITUZIONE:
                    statisticsDto.setCostituzione(stat.getBaseStat());
                    statisticsDto.setCostituzioneModifier(stat.getModified());
                case INTELLIGENZA:
                    statisticsDto.setIntelligenza(stat.getBaseStat());
                    statisticsDto.setIntelligenzaModifier(stat.getModified());
                default: break;
            }
        });
    }

    public static void buildDefaultStatisticDto(CharacterStatisticsDto statisticsDto){
        if(statisticsDto != null){
            statisticsDto.setCostituzione(DEFAULT_STAT);
            statisticsDto.setCostituzioneModifier(BigDecimal.ZERO);
            statisticsDto.setDestrezza(DEFAULT_STAT);
            statisticsDto.setDestrezzaModifier(BigDecimal.ZERO);
            statisticsDto.setForza(DEFAULT_STAT);
            statisticsDto.setForzaModifier(BigDecimal.ZERO);
            statisticsDto.setSaggezza(DEFAULT_STAT);
            statisticsDto.setSaggezzaModifier(BigDecimal.ZERO);
            statisticsDto.setIntelligenza(DEFAULT_STAT);
            statisticsDto.setIntelligenzaModifier(BigDecimal.ZERO);
        }
    }

    public static void mapInventoryDtoToInventory(Inventory inventory, InventoryDto inventoryDto){
        if(inventory != null && inventoryDto != null){
            inventory.setGold(inventoryDto.getGold() != null ? inventoryDto.getGold().intValue() : 0);
            inventory.setInventoryId(inventoryDto.getCharacterId());
        }
    }

    public static void fillInventoryWithObjects(Inventory inventory, List<InventoryObjectAssociation> associations){
        if(inventory != null && !CollectionUtils.isEmpty(associations)){
            inventory.setItems(new ArrayList<>());
            associations.stream().forEach(
                    association -> {
                        inventory.getItems().add(createInventoryObjectFromDtos(association.getInventoryObjectDto(), association.getCharacterInventoryObjectDto()));
                    }
            );
        }
    }

    public static void fillInventoryObjectsWithEffects(@NotNull Inventory inventory, List<InventoryObjectEffectAssociation> effects){
        if(!CollectionUtils.isEmpty(effects) && !CollectionUtils.isEmpty(inventory.getItems())){

            inventory.getItems().stream().forEach(
                    item -> {
                        List<InventoryObjectEffectAssociation> objectEffects = effects.stream()
                                .filter( effect -> {
                                    return effect.getObjectId() == item.getId();
                                })
                                .collect(Collectors.toList());
                        if(!CollectionUtils.isEmpty(objectEffects)){
                            InventoryObjectEffectAssociation association = objectEffects.get(0);
                            item.setModifiers(new ArrayList<>());
                            association.getEffects().stream().forEach( effect -> {
                                item.getModifiers().add(createEffectFromDto(effect));
                            });
                        }
                    }
            );
        }
    }

    public static InventoryObjectEffect createEffectFromDto(@NotNull InventoryObjectEffectDto effectDto){
        InventoryObjectEffect effect = new InventoryObjectEffect();

        effect.setTemporary(effectDto.getIsTemporary());
        effect.setDuration(effectDto.getDuration());
        effect.setHealing(effectDto.getHealing());
        effect.setHealthStatus(StringUtils.hasText(effectDto.getHealthStatus()) ? HealthStatus.valueOf(effectDto.getHealthStatus()) : null);
        effect.setOneShot(effectDto.getIsOneShot());
        effect.setStat(StringUtils.hasText(effectDto.getStat()) ? Stat.valueOf(effectDto.getStat()) : null);

        return effect;
    }

    public static InventoryObject createInventoryObjectFromDtos(InventoryObjectDto inventoryObjectDto, CharacterInventoryObjectDto characterInventoryObjectDto){
        InventoryObject object = new InventoryObject();

        object.setId(characterInventoryObjectDto.getIdInventoryObject());
        object.setName(inventoryObjectDto.getName());
        object.setDescription(inventoryObjectDto.getDescription());
        object.setQuantity(characterInventoryObjectDto.getQuantity());
        object.setEquipment(inventoryObjectDto.isEquipment());
        object.setInUse(characterInventoryObjectDto.getInUse());
        object.setRare(inventoryObjectDto.isRare());
        object.setCategory(ObjectCategory.valueOf(inventoryObjectDto.getCategory()));
        object.setBodyPart(BodyPart.valueOf(inventoryObjectDto.getBodyPart()));
        object.setAcquiringDate(characterInventoryObjectDto.getAcquiringDate());
        object.setDuration(characterInventoryObjectDto.getDuration());


        return object;
    }

}
