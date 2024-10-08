package br.com.app.pet_management_service.api;

import br.com.app.pet_management_service.api.model.PetDTO;
import br.com.app.pet_management_service.services.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PetControllerTest {

    @InjectMocks
    private PetController petController;

    @Mock
    private PetService petService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void petsGet_ShouldReturnListOfPets() {
        List<PetDTO> petList = new ArrayList<>();
        PetDTO pet = new PetDTO();
        pet.setId("1");
        pet.setName("Fido");
        petList.add(pet);

        when(petService.getPets()).thenReturn(petList);

        ResponseEntity<List<PetDTO>> response = petController.petsGet();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Fido", response.getBody().get(0).getName());
        verify(petService).getPets();
    }

    @Test
    void petsIdDelete_ShouldDeletePet() {
        String petId = "1";

        doNothing().when(petService).deletePet(petId);

        ResponseEntity<Void> response = petController.petsIdDelete(petId);

        assertEquals(204, response.getStatusCodeValue());
        verify(petService).deletePet(petId);
    }

    @Test
    void petsIdGet_ShouldReturnPet() {
        String petId = "1";
        PetDTO pet = new PetDTO();
        pet.setId(petId);
        pet.setName("Fido");

        when(petService.getPetById(petId)).thenReturn(pet);

        ResponseEntity<PetDTO> response = petController.petsIdGet(petId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(petId, response.getBody().getId());
        verify(petService).getPetById(petId);
    }

    @Test
    void petsIdPut_ShouldUpdatePet() {
        String petId = "1";
        PetDTO petToUpdate = new PetDTO();
        petToUpdate.setId(petId);
        petToUpdate.setName("Fido Updated");

        when(petService.updatePet(eq(petId), any(PetDTO.class))).thenReturn(petToUpdate);

        ResponseEntity<PetDTO> response = petController.petsIdPut(petToUpdate, petId);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Fido Updated", response.getBody().getName());
        verify(petService).updatePet(petId, petToUpdate);
    }

    @Test
    void petsPost_ShouldAddPet() {
        PetDTO petToAdd = new PetDTO();
        petToAdd.setName("Fido");

        PetDTO addedPet = new PetDTO();
        addedPet.setId("1");
        addedPet.setName("Fido");

        when(petService.addPet(any(PetDTO.class))).thenReturn(addedPet);

        ResponseEntity<PetDTO> response = petController.petsPost(petToAdd);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("1", response.getBody().getId());
        assertEquals("Fido", response.getBody().getName());
        verify(petService).addPet(petToAdd);
    }
}
