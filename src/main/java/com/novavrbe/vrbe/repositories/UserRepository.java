package com.novavrbe.vrbe.repositories;


import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.math.BigDecimal;
import java.util.List;

public interface UserRepository extends CrudRepository<GenericUser, BigDecimal>, JpaSpecificationExecutor<GenericUser> {

    default List<GenericUser> findUsersByEmail(String email){
        return findAll(
                (root, query, cb) -> {
                    return cb.equal(root.get("email"), email);
                }
        );
    }
}
