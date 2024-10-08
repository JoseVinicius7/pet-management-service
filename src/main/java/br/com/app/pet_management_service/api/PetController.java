package br.com.app.pet_management_service.api;

import br.com.app.pet_management_service.api.model.PetDTO;
import br.com.app.pet_management_service.services.PetService;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Log4j2
@RestController
public class PetController implements PetsApi {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @Override
    public ResponseEntity<List<PetDTO>> petsGet() {
        log.info("Fetching all pets.");
        List<PetDTO> response = petService.getPets();
        log.info("Number of pets retrieved: {}", response.size());
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> petsIdDelete(String id) {
        log.debug("Request to delete pet with ID: {}", id);
        petService.deletePet(id);
        log.info("Pet with ID: {} deleted successfully.", id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<PetDTO> petsIdGet(String id) {
        log.debug("Request to fetch pet with ID: {}", id);
        PetDTO petDTO = petService.getPetById(id);
        log.info("Pet found: {}", petDTO);
        return ResponseEntity.ok(petDTO);
    }

    @Override
    public ResponseEntity<PetDTO> petsIdPut(PetDTO body, String id) {
        log.debug("Request to update pet with ID: {} with data: {}", id, body);
        PetDTO response = petService.updatePet(id, body);
        log.info("Pet with ID: {} updated successfully.", id);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<PetDTO> petsPost(@RequestBody @Valid PetDTO body) {
        log.debug("Request to add new pet: {}", body);
        PetDTO response = petService.addPet(body);
        log.info("Pet added successfully with ID: {}", response.getId());
        return ResponseEntity.ok(response);
    }
}
