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

import java.util.*;
import java.util.stream.Collectors;



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
    @Autowired
    private GuildMemberListRepository characterGuildRepository;
    @Autowired
    private MissiveRepositoryService missiveRepository;
    @Autowired
    private PrestavoltoRepository prestavoltoRepository;

    public CharacterDto retrieveCharacterFromId(Integer characterId){
        CharacterDto characterDto = null;
        if(characterId != null){
            Optional<CharacterDto> dto = characterRepository.findById(characterId);
            characterDto = dto.isPresent() ? dto.get() : null;
        }

        return characterDto;
    }

    public ArrayList<CharacterDto> getUnEmployedCharacters(){
        return  characterRepository.getUnemolyedCharacters();
    }

    public CharacterHistoryDto retrieveCharacterHistory(Integer historyId){
        CharacterHistoryDto historyDto = null;
        if(historyId != null){
            Optional<CharacterHistoryDto> dto = characterHistoryRepository.findById(historyId);
            historyDto = dto.isPresent() ? dto.get() : null;
        }
        return historyDto;
    }

    public CharacterDescriptionDto retrieveCharacterDescription(Integer descriptionId){
        CharacterDescriptionDto descriptionDto = null;
        if(descriptionId != null){
            Optional<CharacterDescriptionDto> dto = characterDescriptionRepository.findById(descriptionId);
            descriptionDto = dto.isPresent() ? dto.get() : null;
        }

        return descriptionDto;
    }

    public CharacterStatisticsDto retrieveCharacterStatistics(Integer characterId){
        CharacterStatisticsDto characterStatisticsDto = null;
        if(characterId != null){
            Optional<CharacterStatisticsDto> dto = characterStatisticRepository.findById(characterId);
            characterStatisticsDto = dto.isPresent() ? dto.get() : null;
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
            objectDto = dto != null && dto.isPresent() ? dto.get() : null;
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
            inventoryDto = inventory != null && inventory.isPresent() ? inventory.get() : null;
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
                characterDto.setCharacterId(userId);
                character.setCharacterId(userId.toString());

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

                InventoryDto inventoryDto = CharacterUtils.buildInventoryForDto(characterDto.getCharacterId(), 0);
                if(inventoryDto != null){
                    inventoryRepository.save(inventoryDto);
                }

                MissivaDto welcomeMissiva = new MissivaDto();
                welcomeMissiva.setChTo(characterDto.getCharacterId().toString());
                welcomeMissiva.setChFrom(1);
                welcomeMissiva.setRead(false);
                welcomeMissiva.setDeletedFrom(false);
                welcomeMissiva.setDeletedTo(false);
                welcomeMissiva.setType("OFF");
                welcomeMissiva.setSentAt(new Date().getTime());
                welcomeMissiva.setReceivedAt(new Date().getTime());
                welcomeMissiva.setSubject("Benvenut*");
                welcomeMissiva.setBody("Benvenut* in Land! \n" +
                        "\n" +
                        "Sono molto contento che tu ti sia iscritt* nonostante la land sia ancora in forte fase Alpha Test. \n" +
                        "E' con l'aiuto e i feedback di gente come te che sto provando a mettere su questo progetto. \n" +
                        "\n" +
                        "Ci sono ancora alcune cose che non funzionano, quindi ti chiedo di avere pazienza e segnalarmele così che io possa risolvere e stabilizzare le funzioni che ci sono attualmente.\n" +
                        "Puoi scrivermi a gestione@fervm.it, usare la linguetta gialla a fianco della pagina o mandare una missiva privata.\n" +
                        "Le bacheche sono in redazione, pensavo di avere altre 72 ore prima della pubblicazione ufficiale del gioco su GDR-Online ed invece hanno fatto in fretta. \n" +
                        "Ti ringrazio in anticipo\n" +
                        "Il Gestore");
                missiveRepository.sendMissiva(welcomeMissiva);

            }
        }

        return saved;
    }

    public boolean updateInventory(@NotNull Inventory inventory){
        boolean saved = true;

        InventoryDto inventoryDto = CharacterUtils.buildInventoryForDto(inventory.getInventoryId(), inventory.getGold());
        inventoryRepository.save(inventoryDto);

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
        //Modifichiamo pure le stat associate a quell'oggetto se serve
        ArrayList<InventoryObjectEffectDto> effects = (ArrayList<InventoryObjectEffectDto>) inventoryObjectEffectRepository.findEffectsForObject(characterInventoryObjectDto.getIdInventoryObject());
        if(!characterInventoryObjectDto.getInUse()){
            //se lo sto togliendo devo sottrarre i modificatori delle stat
            for (InventoryObjectEffectDto effect: effects) {
               Optional<CharacterStatisticsDto> statDto = characterStatisticRepository.findById(characterInventoryObjectDto.getCharacterId());
               CharacterStatisticsDto dto = statDto.get();
               switch (effect.getStat()){
                   case "FORZA":
                       dto.setForzaModifier(dto.getForzaModifier()-effect.getModifier());
                       break;
                   case "COSTITUZIONE":
                       dto.setCostituzioneModifier(dto.getCostituzioneModifier()-effect.getModifier());
                       break;
                   case "DESTREZZA":
                       dto.setDestrezzaModifier(dto.getDestrezzaModifier()-effect.getModifier());
                       break;
                   case "INTELLIGENZA":
                       dto.setIntelligenzaModifier(dto.getIntelligenzaModifier()-effect.getModifier());
                       break;
                   case "SAGGEZZA":
                       dto.setSaggezzaModifier(dto.getSaggezzaModifier()-effect.getModifier());
                       break;
               }
               characterStatisticRepository.save(dto);
            }
        }else {
            //se lo sto equipaggiando devo aggiungere i modificatori delle stat
            for (InventoryObjectEffectDto effect: effects) {
                Optional<CharacterStatisticsDto> statDto = characterStatisticRepository.findById(characterInventoryObjectDto.getCharacterId());
                CharacterStatisticsDto dto = statDto.get();
                switch (effect.getStat()){
                    case "FORZA":
                        dto.setForzaModifier(dto.getForzaModifier()+effect.getModifier());
                        break;
                    case "COSTITUZIONE":
                        dto.setCostituzioneModifier(dto.getCostituzioneModifier()+effect.getModifier());
                        break;
                    case "DESTREZZA":
                        dto.setDestrezzaModifier(dto.getDestrezzaModifier()+effect.getModifier());
                        break;
                    case "INTELLIGENZA":
                        dto.setIntelligenzaModifier(dto.getIntelligenzaModifier()+effect.getModifier());
                        break;
                    case "SAGGEZZA":
                        dto.setSaggezzaModifier(dto.getSaggezzaModifier()+effect.getModifier());
                        break;
                }
                characterStatisticRepository.save(dto);
            }
        }


    }

    public void lendItemToAnotherCharacter(@NotNull Integer toCharacterId, @NotNull InventoryObjectAssociation association){
        Integer newQuantity = association.getCharacterInventoryObjectDto().getQuantity() - 1;

        //TODO Ma qui, il caso non dovrebbe essere al contrario?
        if(newQuantity > 0){
            characterInventoryObjectRepository.delete(association.getCharacterInventoryObjectDto());
        }else{
            association.getCharacterInventoryObjectDto().setQuantity(newQuantity);
            characterInventoryObjectRepository.save(association.getCharacterInventoryObjectDto());
        }

        association.getCharacterInventoryObjectDto().setCharacterId(toCharacterId);
        association.getCharacterInventoryObjectDto().setQuantity(1);
        characterInventoryObjectRepository.save(association.getCharacterInventoryObjectDto());
    }

    public InventoryObjectDto retrieveInventoryItem(@NotNull Integer objectId){
        InventoryObjectDto objectDto = null;

        Optional<InventoryObjectDto> item = inventoryObjectRepository.findById(objectId);
        objectDto = item != null && item.isPresent() ? item.get() : null;

        return objectDto;
    }

    public boolean addItemToPlayerInventory(@NotNull Integer characterId, @NotNull InventoryObjectDto item, @NotNull Integer quantity){
        boolean added = false;

        List<CharacterInventoryObjectDto> characterItems = characterInventoryObjectRepository.findCharacterObjects(characterId);
        if(!CollectionUtils.isEmpty(characterItems)){
            List<CharacterInventoryObjectDto> items = characterItems.stream()
                    .filter( citem -> {
                        return Objects.equals(citem.getIdInventoryObject(), item.getId());
                    })
                    .collect(Collectors.toList());
            CharacterInventoryObjectDto citem = items.get(0);
            citem.setQuantity(citem.getQuantity() + quantity);
            characterInventoryObjectRepository.save(citem);
        }else{
            CharacterInventoryObjectDto newItem = CharacterUtils.mapInventoryObjectToCharacterInventoryObject(item, characterId);
            characterInventoryObjectRepository.save(newItem);
        }

        return added;
    }

    /**
     * Torna il mestiere del pg con i suoi parametri
     * @param cID characterID di cui si vuole il mestiere
     * @return le informazioni del mestiere svolto , null altrimenti
     */
    public V_GuildMembers retriveCharacterJob(Integer cID) {
        V_GuildMembers cJob = null;
        Optional<V_GuildMembers> dto = characterGuildRepository.findByCharacterId(cID);
        cJob = dto.orElse(null);
        return cJob;
    }


    public Boolean deposit(int characterId, int amount) {
        Optional<InventoryDto> inventoryDto = inventoryRepository.findById(characterId);
        if(inventoryDto.isPresent()){
            inventoryDto.get().setGold(inventoryDto.get().getGold() + amount);
            inventoryRepository.save(inventoryDto.get());
            return true;
        }
        return false;
    }

    public Boolean withdraw(int characterId, int amount) {
        Optional<InventoryDto> inventoryDto = inventoryRepository.findById(characterId);
        if(inventoryDto.isPresent() && inventoryDto.get().getGold() > amount){
            inventoryDto.get().setGold(inventoryDto.get().getGold() - amount);
            inventoryRepository.save(inventoryDto.get());
            return true;
        }
        return false;
    }

    public boolean UpdateCharacterDescription(Integer chId, String newtext) {
        Optional<CharacterDescriptionDto> opt;
        opt = characterDescriptionRepository.findById(chId);
        if(opt.isPresent()){
            CharacterDescriptionDto temp = opt.get();
            temp.setDescription(newtext);
            characterDescriptionRepository.save(temp);
            return true;
        }
        return false;
    }

    public boolean UpdateCharacterHystory(Integer chId, String newtext) {
        Optional<CharacterHistoryDto> opt;
        opt = characterHistoryRepository.findById(chId);
        if(opt.isPresent()){
            CharacterHistoryDto temp = opt.get();
            temp.setHistory(newtext);
            characterHistoryRepository.save(temp);
            return true;
        }
        return false;
    }

    public boolean updatePersonalImage(Integer chId, String urlImg) {
        Optional<CharacterDto> opt;
        opt = characterRepository.findById(chId);
        if(opt.isPresent()){
            CharacterDto temp = opt.get();
            temp.setCharacterImg(urlImg);
            characterRepository.save((temp));
            return true;
        }
        return false;
    }

    public ArrayList<CharacterDto> getAllCharacters() {
        return (ArrayList<CharacterDto>) characterRepository.findAll();
    }

    public boolean checkCharacterNome(String nome, String cognome) {
        CharacterDto dto = characterRepository.findByCharacterNameAndCharacterSurname(nome,cognome);
        return  dto == null;
    }

    public List<PrestavoltoDto> getPrestavoltoList() {
        return (List<PrestavoltoDto>) prestavoltoRepository.findAll();
    }

    public void UpdatePrestavolto(Integer chId, String name) {
        PrestavoltoDto dto = new PrestavoltoDto();
        dto.setName(name);
        dto.setChId(chId);
        prestavoltoRepository.save(dto);
    }
}
