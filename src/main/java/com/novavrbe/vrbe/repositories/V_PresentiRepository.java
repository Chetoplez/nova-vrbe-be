package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.V_Presenti;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface V_PresentiRepository extends CrudRepository<V_Presenti,Integer> {
    List<V_Presenti> findByIdLuogoAndOnlineTrue(Integer idLuogo);
}
