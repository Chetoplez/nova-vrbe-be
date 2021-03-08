package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterInventoryObjectDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterInventoryObjectRepository extends CrudRepository<CharacterInventoryObjectDto, Integer>, JpaSpecificationExecutor<CharacterInventoryObjectDto> {
    default List<CharacterInventoryObjectDto> findEffectsForObject(Integer characterId){
        return findAll(
                (root, query, cb) -> {
                    return cb.equal(root.get("characterId"), characterId);
                }
        );
    }
}
