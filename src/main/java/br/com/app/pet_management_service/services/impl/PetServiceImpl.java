package br.com.app.pet_management_service.services.impl;

import br.com.app.pet_management_service.api.model.PetDTO;
import br.com.app.pet_management_service.entities.PetEntity;
import br.com.app.pet_management_service.repository.PetRepository;
import br.com.app.pet_management_service.services.PetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public List<PetDTO> getPets() {
        log.info("Fetching all pets from the repository.");
        List<PetEntity> petEntityList = petRepository.findAll();
        log.info("Number of pets retrieved: {}", petEntityList.size());
        return convertToDTOs(petEntityList);
    }

    private List<PetDTO> convertToDTOs(List<PetEntity> petEntities) {
        return petEntities.stream()
                .map(this::convertToDTO)
                .toList();
    }
