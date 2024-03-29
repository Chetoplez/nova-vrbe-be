package com.novavrbe.vrbe.business;

import com.novavrbe.vrbe.dto.*;
import com.novavrbe.vrbe.models.charactercontroller.*;
import com.novavrbe.vrbe.models.charactermodels.*;
import com.novavrbe.vrbe.models.charactermodels.Character;
import com.novavrbe.vrbe.models.enumerations.BodyPart;
import com.novavrbe.vrbe.models.enumerations.MaritalStatus;
import com.novavrbe.vrbe.repositories.impl.CharacterRepositoryService;
import com.novavrbe.vrbe.utils.CharacterUtils;
import com.novavrbe.vrbe.utils.ValidateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CharacterBusiness {

    @Autowired
    private CharacterRepositoryService characterRepositoryService;

    public ResponseEntity<AddCharacterResponse> addCharacter(AddCharacterRequest addCharacterRequest){
        ResponseEntity<AddCharacterResponse> response;

        if(addCharacterRequest.getCharacter() == null || !ValidateUtils.validateCharacter(addCharacterRequest.getCharacter())){
            response = new ResponseEntity<>(new AddCharacterResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        AddCharacterResponse addCharacterResponse = new AddCharacterResponse();
        addCharacterResponse.setSuccess(characterRepositoryService.saveNewCharacter(addCharacterRequest.getUserId(), addCharacterRequest.getCharacter()));

        response = new ResponseEntity<>(addCharacterResponse, addCharacterResponse.isSuccess() ? HttpStatus.OK : HttpStatus.INTERNAL_SERVER_ERROR);

        return response;
    }

    public ResponseEntity<GetCharacterResponse> getCharacter(String characterId){
        ResponseEntity<GetCharacterResponse> response = null;

        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<>(new GetCharacterResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer cID = Integer.parseInt(characterId);

        CharacterDto characterDto = retrieveCharacterFromId(cID);
        if(characterDto != null){
            GetCharacterResponse getCharacterResponse = new GetCharacterResponse();

            Character character = new Character();
            //TODO modificare questa parte per lo stato matrimoniale
            CharacterMaritalStatus dummy = new CharacterMaritalStatus();
            dummy.setCharacterId("0");
            dummy.setCharacterName("none");
            dummy.setMaritalStatus(MaritalStatus.CELIBE);
            character.setMaritalStatus(dummy);
            //fin qui
            CharacterUtils.fillCharacterFieldsFromDto(character, characterDto);
            CharacterUtils.fillCharacterHistoryFromDto(character, characterRepositoryService.retrieveCharacterHistory(cID));
            CharacterUtils.fillCharacterDescriptionFromDto(character, characterRepositoryService.retrieveCharacterDescription(cID));
            CharacterUtils.fillCharacterTemporaryEffectsFromDto(character, characterRepositoryService.retrieveCharacterTemporaryEffects(cID));
            CharacterUtils.fillCharacterJobFromDto(character, characterRepositoryService.retriveCharacterJob(cID));

            CharacterUtils.fillCharacterStatisticsFromDto(character, characterRepositoryService.retrieveCharacterStatistics(cID));

            Inventory inventory = new Inventory();
            CharacterUtils.fillInventoryWithObjects(inventory, characterRepositoryService.retrieveCharacterObjects(cID));
            CharacterUtils.fillInventoryObjectsWithEffects(inventory, characterRepositoryService.retrieveInventoryObjectEffects(inventory));
            CharacterUtils.addModifiedStatsFromEffects(inventory, character);


            getCharacterResponse.setCharacter(character);
            getCharacterResponse.setNewpg(false);
            response = new ResponseEntity<GetCharacterResponse>(getCharacterResponse, HttpStatus.OK);
        }else{
            //Un utente è registrato ma non ha completato il processo di creazione del pg, quindi ha un UserId ma non un Character
            GetCharacterResponse characterResp = new GetCharacterResponse();
            characterResp.setNewpg(true);
            response = new ResponseEntity<GetCharacterResponse>(characterResp, HttpStatus.OK);
        }

        return response;
    }

    public ResponseEntity<GetInventoryResponse> getInventory(String characterId){
        ResponseEntity<GetInventoryResponse> response = null;

        if(!StringUtils.hasText(characterId)){
            response = new ResponseEntity<>(new GetInventoryResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer charactedId = Integer.parseInt(characterId);

        InventoryDto inventoryDto = characterRepositoryService.retrieveCharacterInventory(charactedId);
        if(inventoryDto == null){
            response = new ResponseEntity<>(new GetInventoryResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Inventory inventory = new Inventory(); //correzione: aggiunto costruttore che inizializza la ista items, altrimenti arriva null se il pg non ha oggetti
        CharacterUtils.mapInventoryDtoToInventory(inventory, inventoryDto);
        CharacterUtils.fillInventoryWithObjects(inventory, characterRepositoryService.retrieveCharacterObjects(charactedId));
        CharacterUtils.fillInventoryObjectsWithEffects(inventory, characterRepositoryService.retrieveInventoryObjectEffects(inventory));

        GetInventoryResponse inventoryResponse = new GetInventoryResponse();
        inventoryResponse.setInventory(inventory);
        response = new ResponseEntity<>(inventoryResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<UpdateInventoryResponse> updateInventory(UpdateInventoryRequest request){
        ResponseEntity<UpdateInventoryResponse> response = null;

        if(request == null || request.getInventory() == null || (request.getInventory() != null &&request.getInventory().getInventoryId() == null)){
            response = new ResponseEntity<>(new UpdateInventoryResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        UpdateInventoryResponse inventoryResponse = new UpdateInventoryResponse();
        inventoryResponse.setInventory(request.getInventory());

        response = new ResponseEntity<>(inventoryResponse, characterRepositoryService.updateInventory(request.getInventory()) ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

        return response;
    }

    public ResponseEntity<GetListUnemployedResponse> getUnEmployedCharacters(){
        ResponseEntity<GetListUnemployedResponse> response;
        ArrayList<CharacterDto> dtos = characterRepositoryService.getUnEmployedCharacters();
        ArrayList<SmallCharacter> unEmployed = new ArrayList<>();
        for (CharacterDto dto: dtos) {
            SmallCharacter temp = new SmallCharacter();
            temp.setCharacterId(dto.getCharacterId());
            temp.setCharacterName(dto.getCharacterName());
            temp.setCharacterSurname(dto.getCharacterSurname());
            temp.setCharacterImg(dto.getCharacterImg());
            unEmployed.add(temp);
        }
        GetListUnemployedResponse res = new GetListUnemployedResponse();
        res.setUnemployed(unEmployed);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    public ResponseEntity<EquipItemResponse> equipItem(EquipItemRequest request) {
        ResponseEntity<EquipItemResponse> response = null;

        if(request == null || request.getCharacterId() == null || request.getItemId() == null){
            response = new ResponseEntity<>(new EquipItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        CharacterDto character = retrieveCharacterFromId(request.getCharacterId());
        if(character == null){
            response = new ResponseEntity<>(new EquipItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        List<InventoryObjectAssociation> objects = characterRepositoryService.retrieveCharacterObjects(request.getCharacterId());
        EquipItemResponse equipItemResponse = new EquipItemResponse();

        if(!CollectionUtils.isEmpty(objects)){
            List<InventoryObjectAssociation> itemDto = objects.stream().filter(association -> {
                return association.getCharacterInventoryObjectDto().getIdInventoryObject() == request.getItemId();
            }).collect(Collectors.toList());
            if(!CollectionUtils.isEmpty(itemDto)){
                InventoryObjectAssociation item = itemDto.get(0);
                //Equipaggiamento
                if(item.getInventoryObjectDto().isEquipment()){

                    equipItemAndCheckCompability(item, objects, request.isRemove());
                }else{
                    applyOneEffectItem(request.getCharacterId(), item);
                }
                equipItemResponse.setEquipped(true);
            }
        }

        response = new ResponseEntity<>(equipItemResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<LendItemResponse> lendItem(LendItemRequest request) {
        ResponseEntity<LendItemResponse> response = null;

        if(request == null || request.getFromCharacterId() == null || request.getFromObjectId() == null || request.getToCharacterId() == null){
            response = new ResponseEntity<>(new LendItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        LendItemResponse lendItemResponse = new LendItemResponse();

        List<InventoryObjectAssociation> objects = characterRepositoryService.retrieveCharacterObjects(request.getFromCharacterId());
        if(!CollectionUtils.isEmpty(objects)){
            List<InventoryObjectAssociation> items = objects.stream().filter(association -> {
                return association.getCharacterInventoryObjectDto().getIdInventoryObject() == request.getFromObjectId();
            }).collect(Collectors.toList());

            if(!CollectionUtils.isEmpty(items)){
                InventoryObjectAssociation item = items.get(0);
                characterRepositoryService.lendItemToAnotherCharacter(request.getToCharacterId(), item);
                lendItemResponse.setLended(true);
            }
        }

        response = new ResponseEntity<>(lendItemResponse, HttpStatus.OK);

        return response;
    }

    public ResponseEntity<AddItemResponse> addItem(AddItemRequest request) {
        ResponseEntity<AddItemResponse> response = null;

        if(request == null || request.getCharacterId() == null || request.getItemId() == null || request.getQuantity() == null){
            response = new ResponseEntity<>(new AddItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        CharacterDto character = retrieveCharacterFromId(request.getCharacterId());
        InventoryObjectDto inventoryObjectDto = characterRepositoryService.retrieveInventoryItem(request.getItemId());

        if(character == null || inventoryObjectDto == null){
            response = new ResponseEntity<>(new AddItemResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        AddItemResponse addItemResponse = new AddItemResponse();

        addItemResponse.setSuccess(characterRepositoryService.addItemToPlayerInventory(character.getCharacterId(), inventoryObjectDto, request.getQuantity()));
        response = new ResponseEntity<>(addItemResponse, HttpStatus.OK);

        return response;
    }

    private CharacterDto retrieveCharacterFromId(Integer id){
        CharacterDto character = characterRepositoryService.retrieveCharacterFromId(id);
        return  character;
    }

    private void applyOneEffectItem(Integer characterId, InventoryObjectAssociation item){
        //Oggetto one shot
        List<InventoryObjectEffectDto> effectDtoList = characterRepositoryService.retrieveInventoryObjectEffectsDto(item.getInventoryObjectDto().getId());
        if(!CollectionUtils.isEmpty(effectDtoList)){
            effectDtoList.stream().forEach( effect -> {
                characterRepositoryService.applyEffect(characterId, effect);
            });
        }

        characterRepositoryService.decreaseQuantityOrRemoveObject(characterId, item);
    }

    private void equipItemAndCheckCompability(InventoryObjectAssociation item, List<InventoryObjectAssociation> objects, boolean remove){
        //Se si sceglie di rimuovere un equipaggiamento, va bene cosi

        item.getCharacterInventoryObjectDto().setInUse(!remove);
        characterRepositoryService.equipItem(item.getCharacterInventoryObjectDto());

        //Altrimenti se lo si sta equipaggiando, bisogna controllare la parte del corpo. Se esiste gia un equipaggiamento per la stessa parte del corpo, va rimosso
        if(!remove){
            objects.stream().forEach(
                    equipment -> {
                        if(item.getCharacterInventoryObjectDto().getIdInventoryObject() != equipment.getCharacterInventoryObjectDto().getIdInventoryObject() && Boolean.TRUE.equals(equipment.getCharacterInventoryObjectDto().getInUse())){
                            //Stessa parte del corpo
                            // Oppure caso speciale per armi a due mani
                            if(item.getInventoryObjectDto().getBodyPart().equalsIgnoreCase(equipment.getInventoryObjectDto().getBodyPart())
                                    || (
                                    BodyPart.DUAL_WIELD.name().equalsIgnoreCase(item.getInventoryObjectDto().getBodyPart())
                                            && (BodyPart.HAND.name().equalsIgnoreCase(equipment.getInventoryObjectDto().getBodyPart()))
                            )){
                                equipment.getCharacterInventoryObjectDto().setInUse(false);
                                characterRepositoryService.equipItem(equipment.getCharacterInventoryObjectDto());
                            }
                        }
                    });
        }
    }

    /**
     * Questo metodo permette al giocatore di aggiornare la proria descrizione.
     * @param incomingRequest contiene l'id del pg da aggiornare e il nuovo campo
     * @return true se è andato a buon fine, false altrimenti
     */
    public ResponseEntity<UpdateDescriptionResponse> updateDescription(UpdateDescriptionRequest incomingRequest) {
        ResponseEntity<UpdateDescriptionResponse> response;
        if(!StringUtils.hasText(incomingRequest.getCharacterId()) || !StringUtils.hasText(incomingRequest.getNewtext())){
            //Bad!!
            response = new ResponseEntity<>(new UpdateDescriptionResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer chId = Integer.parseInt(incomingRequest.getCharacterId());
        boolean changed = characterRepositoryService.UpdateCharacterDescription(chId, incomingRequest.getNewtext());
        UpdateDescriptionResponse res = new UpdateDescriptionResponse();
        res.setChanged(changed);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }


    public ResponseEntity<UpdateDescriptionResponse> updateHistory(UpdateDescriptionRequest incomingRequest) {
        ResponseEntity<UpdateDescriptionResponse> response;
        if(!StringUtils.hasText(incomingRequest.getCharacterId()) || !StringUtils.hasText(incomingRequest.getNewtext())){
            //Bad!!
            response = new ResponseEntity<>(new UpdateDescriptionResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }

        Integer chId = Integer.parseInt(incomingRequest.getCharacterId());
        boolean changed = characterRepositoryService.UpdateCharacterHystory(chId, incomingRequest.getNewtext());
        UpdateDescriptionResponse res = new UpdateDescriptionResponse();
        res.setChanged(changed);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;
    }

    public ResponseEntity<UpdateDescriptionResponse> updateImage(UpdateImageRequest incomingRequest) {
        //Sto volutamente riutilizzando questa response perché tanto è un update su un attributo del charachter
        ResponseEntity<UpdateDescriptionResponse> response;
        if(!StringUtils.hasText(incomingRequest.getCharachterId()) || !StringUtils.hasText(incomingRequest.getUrlImg())){
            //Bad!!
            response = new ResponseEntity<>(new UpdateDescriptionResponse(), HttpStatus.BAD_REQUEST);
            return response;
        }
        Integer chId = Integer.parseInt(incomingRequest.getCharachterId());
        boolean changed = characterRepositoryService.updatePersonalImage(chId, incomingRequest.getUrlImg());
        UpdateDescriptionResponse res = new UpdateDescriptionResponse();
        res.setChanged(changed);
        response = new ResponseEntity<>(res,HttpStatus.OK);
        return response;

    }

    /**
     * Torna la lista di tutti i pg della land.
     * @return
     */
    public ResponseEntity<GetAllCharacterResponse> getAllCharacters() {
        ResponseEntity<GetAllCharacterResponse> response;
        GetAllCharacterResponse characterResponse = new GetAllCharacterResponse();
        ArrayList<CharacterDto> dtos = characterRepositoryService.getAllCharacters();
        ArrayList<SmallCharacter> characters = new ArrayList<>();
        for (CharacterDto dto: dtos) {
            SmallCharacter temp = new SmallCharacter();
            temp.setCharacterId(dto.getCharacterId());
            temp.setCharacterName(dto.getCharacterName());
            temp.setCharacterSurname(dto.getCharacterSurname());
            temp.setCharacterImg(dto.getCharacterImg());
            characters.add(temp);
        }
        characterResponse.setCharacterList(characters);
        response = new ResponseEntity<>(characterResponse, HttpStatus.OK);
        return response;
    }

    /**
     * Verifica se esiste già un pg con quel nome
     * @param request
     * @return
     */
    public ResponseEntity<CheckCharacterNomeResponse> checkCharacterNomi (CheckCharacterNomeRequest request) {
        ResponseEntity<CheckCharacterNomeResponse> response;
        CheckCharacterNomeResponse nomeResponse = new CheckCharacterNomeResponse();
        nomeResponse.setValid(false);
        if(!StringUtils.hasText(request.getNome()) || !StringUtils.hasText(request.getCognome())){
            response = new ResponseEntity<>(nomeResponse, HttpStatus.BAD_REQUEST);
            return response;
        }
        boolean valid = characterRepositoryService.checkCharacterNome(request.getNome(), request.getCognome());
        nomeResponse.setValid(valid);
        response = new ResponseEntity<>(nomeResponse, HttpStatus.OK);
        return response;
    }

    /**
     * Torna la lista dei prestavolto
     * @return
     */
    public ResponseEntity<GetPrestavoltoResponse> getPrestavoltoList () {
        ResponseEntity<GetPrestavoltoResponse> response;
        GetPrestavoltoResponse prestavoltoResponse = new GetPrestavoltoResponse();
        ArrayList<Prestavolto> lista = new ArrayList<>();

        List<PrestavoltoDto> dtos = characterRepositoryService.getPrestavoltoList();
        if(dtos != null) {
            lista = CharacterUtils.preparePrestavoltoList(dtos, characterRepositoryService);
        }
        prestavoltoResponse.setPrestavoltoList(lista);
        response = new ResponseEntity<>(prestavoltoResponse, HttpStatus.OK);
        return response;
    }

    public ResponseEntity<UpdatePrestavoltoResponse> updatePrestavolto(UpdatePrestavoltoRequest request){
        ResponseEntity<UpdatePrestavoltoResponse> response;
        UpdatePrestavoltoResponse updateResponse = new UpdatePrestavoltoResponse();
        if(request.getChId() == null || !StringUtils.hasText(request.getName())){
            updateResponse.setUpdated(false);
            updateResponse.setMessage("Nome non aggiornato");
            response = new ResponseEntity<>(updateResponse,HttpStatus.BAD_REQUEST);
            return response;
        }

        characterRepositoryService.UpdatePrestavolto(request.getChId(),request.getName());
        updateResponse.setUpdated(true);
        updateResponse.setMessage("Prestavolto Aggiornato! Ricorda di farlo ogni volta che cambi immagine");
        response = new ResponseEntity<>(updateResponse,HttpStatus.OK);
        return response;
    }
}
