package br.com.app.pet_management_service.services.impl;

import br.com.app.pet_management_service.api.model.PetDTO;
import br.com.app.pet_management_service.entities.PetEntity;
import br.com.app.pet_management_service.exceptions.PayloadValidationException;
import br.com.app.pet_management_service.exceptions.PetNotFoundException;
import br.com.app.pet_management_service.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    private PetRepository petRepository;
    private PetServiceImpl petService;

    @BeforeEach
    void setUp() {
        petRepository = mock(PetRepository.class);
        petService = new PetServiceImpl(petRepository);
    }

    @Test
    void deletePet_ShouldDeletePet_WhenPetExists() {
        String petId = UUID.randomUUID().toString();
        PetEntity pet = new PetEntity();
        pet.setId(UUID.fromString(petId));

        when(petRepository.findById(UUID.fromString(petId))).thenReturn(Optional.of(pet));

        assertDoesNotThrow(() -> petService.deletePet(petId));

        verify(petRepository, times(1)).delete(pet);
    }

    @Test
    void deletePet_ShouldThrowPetNotFoundException_WhenPetDoesNotExist() {
        String petId = UUID.randomUUID().toString();

        when(petRepository.findById(UUID.fromString(petId))).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> petService.deletePet(petId));
        assertEquals("Pet não encontrado com ID: " + petId, exception.getMessage());
    }

    @Test
    void getPetById_ShouldReturnPetDTO_WhenPetExists() {
        String petId = UUID.randomUUID().toString();
        PetEntity pet = new PetEntity();
        pet.setId(UUID.fromString(petId));
        pet.setName("Fido");
        pet.setBreed("Labrador");

        when(petRepository.findById(UUID.fromString(petId))).thenReturn(Optional.of(pet));

        PetDTO result = petService.getPetById(petId);

        assertNotNull(result);
        assertEquals("Fido", result.getName());
    }

    @Test
    void getPetById_ShouldThrowPetNotFoundException_WhenPetDoesNotExist() {
        String petId = UUID.randomUUID().toString();

        when(petRepository.findById(UUID.fromString(petId))).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> petService.getPetById(petId));
        assertEquals("Pet não encontrado com ID: " + petId, exception.getMessage());
    }

    @Test
    void addPet_ShouldSavePet_WhenPetDTOIsValid() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Fido");
        petDTO.setBreed("Labrador");

        PetEntity petEntity = new PetEntity();
        petEntity.setId(UUID.randomUUID());
        petEntity.setName(petDTO.getName());
        petEntity.setBreed(petDTO.getBreed());

        when(petRepository.save(any(PetEntity.class))).thenReturn(petEntity);

        PetDTO result = petService.addPet(petDTO);

        assertNotNull(result);
        assertEquals(petDTO.getName(), result.getName());
        assertEquals(petDTO.getBreed(), result.getBreed());
    }

    @Test
    void addPet_ShouldThrowPayloadValidationException_WhenPetDTOIsInvalid() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Nome");
        petDTO.setBreed("");

        PayloadValidationException exception = assertThrows(PayloadValidationException.class, () -> petService.addPet(petDTO));
        assertEquals("A raça do pet é obrigatória.", exception.getMessage());
    }

    @Test
    void addPet_ShouldThrowPayloadValidationException_WhenPetDTONameIsInvalid() {
        PetDTO petDTO = new PetDTO();
        petDTO.setName("");
        petDTO.setBreed("Labrador");

        PayloadValidationException exception = assertThrows(PayloadValidationException.class, () -> petService.addPet(petDTO));
        assertEquals("O nome do pet é obrigatório.", exception.getMessage());
    }

    @Test
    void updatePet_ShouldUpdatePet_WhenPetExists() {
        String petId = UUID.randomUUID().toString();
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Fido Updated");
        petDTO.setBreed("Labrador Updated");

        PetEntity existingPet = new PetEntity();
        existingPet.setId(UUID.fromString(petId));
        existingPet.setName("Fido");
        existingPet.setBreed("Labrador");

        when(petRepository.findById(UUID.fromString(petId))).thenReturn(Optional.of(existingPet));
        when(petRepository.save(any(PetEntity.class))).thenReturn(existingPet);

        PetDTO result = petService.updatePet(petId, petDTO);

        assertNotNull(result);
        assertEquals("Fido Updated", result.getName());
        verify(petRepository, times(1)).save(existingPet);
    }

    @Test
    void updatePet_ShouldThrowPetNotFoundException_WhenPetDoesNotExist() {
        String petId = UUID.randomUUID().toString();
        PetDTO petDTO = new PetDTO();
        petDTO.setName("Fido");
        petDTO.setBreed("Labrador");

        when(petRepository.findById(UUID.fromString(petId))).thenReturn(Optional.empty());

        PetNotFoundException exception = assertThrows(PetNotFoundException.class, () -> petService.updatePet(petId, petDTO));
        assertEquals("Pet não encontrado com ID: " + petId, exception.getMessage());
    }
    @Test
    void getPets_ShouldReturnListOfPetDTOs() {
        List<PetEntity> petEntityList = new ArrayList<>();
        PetEntity petEntity = new PetEntity();
        petEntity.setId(UUID.randomUUID());
        petEntity.setName("Fido");
        petEntity.setBreed("Labrador");
        petEntity.setAge(3);
        petEntity.setAdditionalInfo("Friendly");

        petEntityList.add(petEntity);

        when(petRepository.findAll()).thenReturn(petEntityList);

        List<PetDTO> result = petService.getPets();

        assertEquals(1, result.size());
        assertEquals("Fido", result.getFirst().getName());
        assertEquals("Labrador", result.getFirst().getBreed());
        assertEquals(3, result.getFirst().getAge());
        verify(petRepository).findAll();
    }
}
