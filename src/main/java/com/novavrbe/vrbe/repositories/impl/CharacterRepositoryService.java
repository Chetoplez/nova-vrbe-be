package com.novavrbe.vrbe.repositories.impl;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.models.charactermodels.Inventory;
import com.novavrbe.vrbe.models.charactermodels.InventoryObjectAssociation;
import com.novavrbe.vrbe.models.charactermodels.InventoryObjectEffectAssociation;
import com.novavrbe.vrbe.repositories.*;
import com.novavrbe.vrbe.utils.CharacterUtils;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CharacterRepositoryService {

    @Autowired
    private CharacterRepository characterRepository;
    @Autowired
    private CharacterHistoryRepository characterHistoryRepository;
    @Autowired
    private CharacterDescriptionRepository characterDescriptionRepository;
    @Autowired
    private CharacterStatisticRepository characterStatisticRepository;
    @Autowired
    private CharacterTemporaryEffectsRepository characterTemporaryEffectsRepository;
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryObjectEffectRepository inventoryObjectEffectRepository;
    @Autowired
    private InventoryObjectRepository inventoryObjectRepository;
    @Autowired
    private CharacterInventoryObjectRepository characterInventoryObjectRepository;

    public CharacterDto retrieveCharacterFromId(Integer characterId){
        CharacterDto characterDto = null;
        if(characterId != null){
            Optional<CharacterDto> dto = characterRepository.findById(characterId);
            characterDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return characterDto;
    }

    public CharacterHistoryDto retrieveCharacterHistory(Integer historyId){
        CharacterHistoryDto historyDto = null;
        if(historyId != null){
            Optional<CharacterHistoryDto> dto = characterHistoryRepository.findById(historyId);
            historyDto = dto != null && dto.get() != null ? dto.get() : null;
        }
        return historyDto;
    }

    public CharacterDescriptionDto retrieveCharacterDescription(Integer descriptionId){
        CharacterDescriptionDto descriptionDto = null;
        if(descriptionId != null){
            Optional<CharacterDescriptionDto> dto = characterDescriptionRepository.findById(descriptionId);
            descriptionDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return descriptionDto;
    }

    public CharacterStatisticsDto retrieveCharacterStatistics(Integer characterId){
        CharacterStatisticsDto characterStatisticsDto = null;
        if(characterId != null){
            Optional<CharacterStatisticsDto> dto = characterStatisticRepository.findById(characterId);
            characterStatisticsDto = dto != null && dto.get() != null ? dto.get() : null;
        }

        return characterStatisticsDto;
    }

    public List<InventoryObjectAssociation> retrieveCharacterObjects(Integer characterId){
        final List<InventoryObjectAssociation> objects = new ArrayList<>();

        if(characterId != null){
            List<CharacterInventoryObjectDto> allCharacterObjects = characterInventoryObjectRepository.findCharacterObjects(characterId);
            if(!CollectionUtils.isEmpty(allCharacterObjects)){
                allCharacterObjects.stream()
                        .forEach(obj -> {
                            InventoryObjectAssociation association = new InventoryObjectAssociation();
                            association.setCharacterInventoryObjectDto(obj);
                            InventoryObjectDto inventoryObjectDto = retrieveInventoryObject(obj.getIdInventoryObject());
                            if(inventoryObjectDto != null){
                                association.setInventoryObjectDto(inventoryObjectDto);
                                objects.add(association);
                            }
                        });
            }
        }

        return objects;
    }

    public InventoryObjectDto retrieveInventoryObject(Integer objectId){
        InventoryObjectDto objectDto = null;
        if(objectId != null){
            Optional<InventoryObjectDto> dto = inventoryObjectRepository.findById(objectId);
            objectDto = dto != null && dto.get() != null ? dto.get() : null;
        }
        return objectDto;
    }

    public List<CharacterTemporaryEffectDto> retrieveCharacterTemporaryEffects(Integer characterId){
        List<CharacterTemporaryEffectDto> effects = new ArrayList<>();

        if(characterId != null){
            effects = characterTemporaryEffectsRepository.findTemporaryEffectForUser(characterId);
        }

        return effects;
    }

    public InventoryDto retrieveCharacterInventory(Integer characterId){
        InventoryDto inventoryDto = null;

        if(characterId != null){
            Optional<InventoryDto> inventory = inventoryRepository.findById(characterId);
            inventoryDto = inventory != null && inventory.get() != null ? inventory.get() : null;
        }

        return inventoryDto;
    }

    public List<InventoryObjectEffectAssociation> retrieveInventoryObjectEffects(Inventory inventory){
        final List<InventoryObjectEffectAssociation> associations = new ArrayList<>();

        if(!CollectionUtils.isEmpty(inventory.getItems())){
            inventory.getItems().stream()
                    .forEach( item -> {
                        InventoryObjectEffectAssociation association = new InventoryObjectEffectAssociation();

                        List<InventoryObjectEffectDto> effects = retrieveInventoryObjectEffectsDto(item.getId());
                        if(!CollectionUtils.isEmpty(effects)){
                            association.setObjectId(item.getId());
                            association.setEffects(effects);
                            associations.add(association);
                        }
                    });
        }

        return associations;
    }

    public List<InventoryObjectEffectDto> retrieveInventoryObjectEffectsDto(Integer objectId){
        List<InventoryObjectEffectDto> effects = new ArrayList<>();

        effects = inventoryObjectEffectRepository.findEffectsForObject(objectId);

        return effects;
    }

    public boolean saveNewCharacter(Integer userId, Character character){
        boolean saved = true;

        CharacterDto characterDto = CharacterUtils.buildCharacterDtoFromCharacter(userId, character);

        if(characterDto != null){
            saved = characterRepository.save(characterDto) != null;
            if(saved){
                CharacterHistoryDto historyDto = CharacterUtils.buildCharacterHistoryForDto(userId, character);
                if(historyDto != null){
                    characterHistoryRepository.save(historyDto);
                }

                CharacterDescriptionDto characterDescriptionDto = CharacterUtils.buildCharacterDescriptionForDto(userId, character);
                if(characterDescriptionDto != null){
                    characterDescriptionRepository.save(characterDescriptionDto);
                }

                CharacterStatisticsDto characterStatisticsDto = CharacterUtils.buildCharacterStatisticForDto(userId, character);
                if(characterStatisticsDto != null){
                    characterStatisticRepository.save(characterStatisticsDto);
                }

                InventoryDto inventoryDto = CharacterUtils.buildInventoryForDto(characterDto.getCharacterId(), BigDecimal.ZERO);
                if(inventoryDto != null){
                    inventoryRepository.save(inventoryDto);
                }

            }
        }

        return saved;
    }

    public boolean updateInventory(@NotNull Inventory inventory){
        boolean saved = true;

        //TODO

        return saved;
    }

    public void applyEffect(@NotNull Integer characterId, @NotNull InventoryObjectEffectDto effectDto){
        if(effectDto.getIsOneShot()){
            CharacterDto character = retrieveCharacterFromId(characterId);
            if(StringUtils.hasText(effectDto.getHealthStatus())){
                character.setHealthStatus(effectDto.getHealthStatus());
            }
            if(effectDto.getHealing() != null){
                character.setHealth(character.getHealth() + effectDto.getHealing());
            }
            characterRepository.save(character);
        }

        if(effectDto.getIsTemporary()){
            CharacterTemporaryEffectDto newEffect = new CharacterTemporaryEffectDto();
            newEffect.setCharacterId(characterId);
            newEffect.setStat(effectDto.getStat());
            newEffect.setModifier(effectDto.getModifier());
            characterTemporaryEffectsRepository.save(newEffect);
        }
    }

    public void decreaseQuantityOrRemoveObject(@NotNull Integer characterId, @NotNull InventoryObjectAssociation association){
        Integer newQuantity = association.getCharacterInventoryObjectDto().getQuantity() - 1;
        if(newQuantity > 0){
            association.getCharacterInventoryObjectDto().setQuantity(newQuantity);
            characterInventoryObjectRepository.save(association.getCharacterInventoryObjectDto());
        }else{
            characterInventoryObjectRepository.delete(association.getCharacterInventoryObjectDto());
        }
    }

    public void equipItem(@NotNull CharacterInventoryObjectDto characterInventoryObjectDto){
        characterInventoryObjectRepository.save(characterInventoryObjectDto);
    }

}
