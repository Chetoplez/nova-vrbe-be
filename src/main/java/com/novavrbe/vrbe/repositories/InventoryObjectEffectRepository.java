package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.InventoryObjectEffectDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryObjectEffectRepository extends CrudRepository<InventoryObjectEffectDto, Integer>, JpaSpecificationExecutor<InventoryObjectEffectDto> {

    default List<InventoryObjectEffectDto> findEffectsForObject(Integer objectId){
        return findAll(
                (root, query, cb) -> {
                    return cb.equal(root.get("inventoryObjectId"), objectId);
                }
        );
    }
}
