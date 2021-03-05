package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterTemporaryEffectDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CharacterTemporaryEffectsRepository extends CrudRepository<CharacterTemporaryEffectDto, Integer>, JpaSpecificationExecutor<CharacterTemporaryEffectDto> {

    default List<CharacterTemporaryEffectDto> findTemporaryEffectForUser(Integer characterId){
        return findAll(
                (root, query, cb) -> {
                    return cb.equal(root.get("characterId"), characterId);
                }
        );
    }
}
