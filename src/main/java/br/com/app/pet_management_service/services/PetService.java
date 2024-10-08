package br.com.app.pet_management_service.services;

import br.com.app.pet_management_service.api.model.PetDTO;

import java.util.List;

public interface PetService {

    List<PetDTO> getPets();

    PetDTO addPet(PetDTO body);

    PetDTO updatePet(String id,PetDTO body);

    void deletePet(String id);

    PetDTO getPetById(String id);
}

