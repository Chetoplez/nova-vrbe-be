package com.novavrbe.vrbe.repositories;

import com.novavrbe.vrbe.dto.CharacterDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface CharacterRepository extends JpaRepository<CharacterDto, Integer> {

    @Query(value = "call getUnemployedCharacters();", nativeQuery = true)
    ArrayList<CharacterDto> getUnemolyedCharacters();

    CharacterDto findByCharacterNameAndCharacterSurname(String nome, String cognome);
}
