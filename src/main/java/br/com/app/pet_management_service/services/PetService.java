package br.com.app.pet_management_service.services;

import br.com.app.pet_management_service.api.model.PetDTO;

import java.util.List;

public interface PetService {

    List<PetDTO> getPets();

    PetDTO addPet(PetDTO body);
}
