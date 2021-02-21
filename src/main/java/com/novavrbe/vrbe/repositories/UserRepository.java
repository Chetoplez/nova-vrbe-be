package com.novavrbe.vrbe.repositories;


import com.novavrbe.vrbe.models.charactermodels.GenericUser;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<GenericUser, String> {
}
