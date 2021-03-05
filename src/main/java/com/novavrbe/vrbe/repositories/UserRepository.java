package com.novavrbe.vrbe.repositories;


import com.novavrbe.vrbe.dto.GenericUserDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends CrudRepository<GenericUserDto, BigDecimal>, JpaSpecificationExecutor<GenericUserDto> {

    default List<GenericUserDto> findUsersByEmail(String email){
        return findAll(
                (root, query, cb) -> {
                    return cb.equal(root.get("email"), email);
                }
        );
    }
}
