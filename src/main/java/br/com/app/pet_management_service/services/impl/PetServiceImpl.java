package br.com.app.pet_management_service.services.impl;

import br.com.app.pet_management_service.api.model.PetDTO;
import br.com.app.pet_management_service.entities.PetEntity;
import br.com.app.pet_management_service.exceptions.PayloadValidationException;
import br.com.app.pet_management_service.exceptions.PetNotFoundException; // Nova exceção
import br.com.app.pet_management_service.repository.PetRepository;
import br.com.app.pet_management_service.services.PetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public void deletePet(String id) {
        log.debug("Deleting pet with ID: {}", id);
        PetEntity pet = petRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado com ID: " + id));
        petRepository.delete(pet);
        log.info("Pet deleted successfully with ID: {}", id);
    }

    @Override
    public PetDTO getPetById(String id) {
        log.debug("Fetching pet with ID: {}", id);
        PetEntity pet = petRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado com ID: " + id));
        return convertToDTO(pet);
    }

    @Override
    public List<PetDTO> getPets() {
        log.info("Fetching all pets from the repository.");
        List<PetEntity> petEntityList = petRepository.findAll();
        log.info("Number of pets retrieved: {}", petEntityList.size());
        return convertToDTOs(petEntityList);
    }

    @Override
    public PetDTO addPet(PetDTO petDTO) {
        log.debug("Adding a new pet: {}", petDTO);
        validatePetDTO(petDTO);
        PetEntity pet = createPetEntity(petDTO);
        PetEntity savedPet = petRepository.save(pet);
        log.info("Pet saved successfully with ID: {}", savedPet.getId());
        return convertToDTO(savedPet);
    }

    @Override
    public PetDTO updatePet(String id, PetDTO petDTO) {
        log.debug("Updating pet with ID: {}", id);
        validatePetDTO(petDTO);

        PetEntity existingPet = petRepository.findById(UUID.fromString(id))
                .orElseThrow(() -> new PetNotFoundException("Pet não encontrado com ID: " + id));

        existingPet.setName(petDTO.getName());
        existingPet.setBreed(petDTO.getBreed());
        existingPet.setAge(petDTO.getAge());
        existingPet.setAdditionalInfo(petDTO.getAdditionalInfo());

        PetEntity updatedPet = petRepository.save(existingPet);
        log.info("Pet updated successfully with ID: {}", updatedPet.getId());
        return convertToDTO(updatedPet);
    }

    private void validatePetDTO(PetDTO petDTO) {
        if (petDTO.getName() == null || petDTO.getName().isEmpty()) {
            log.error("Validation failed: Pet name is required.");
            throw new PayloadValidationException("O nome do pet é obrigatório.");
        }
        if (petDTO.getBreed() == null || petDTO.getBreed().isEmpty()) {
            log.error("Validation failed: Pet breed is required.");
            throw new PayloadValidationException("A raça do pet é obrigatória.");
        }
        log.debug("Validation passed for pet: {}", petDTO);
    }

    private PetEntity createPetEntity(PetDTO petDTO) {
        return PetEntity.builder()
                .name(petDTO.getName())
                .breed(petDTO.getBreed())
                .age(petDTO.getAge())
                .additionalInfo(petDTO.getAdditionalInfo())
                .build();
    }

    private List<PetDTO> convertToDTOs(List<PetEntity> petEntities) {
        return petEntities.stream()
                .map(this::convertToDTO)
                .toList();
    }

    private PetDTO convertToDTO(PetEntity savedPet) {
        PetDTO responsePetDTO = new PetDTO();
        responsePetDTO.setId(String.valueOf(savedPet.getId()));
        responsePetDTO.setName(savedPet.getName());
        responsePetDTO.setBreed(savedPet.getBreed());
        responsePetDTO.setAge(savedPet.getAge());
        responsePetDTO.setAdditionalInfo(savedPet.getAdditionalInfo());
        return responsePetDTO;
    }
}
