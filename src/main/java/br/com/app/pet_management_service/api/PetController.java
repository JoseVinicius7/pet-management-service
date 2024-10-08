package br.com.app.pet_management_service.api;

import br.com.app.pet_management_service.api.model.PetDTO;
import br.com.app.pet_management_service.services.PetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PetController implements PetsApi {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }


    @Override
    public ResponseEntity<List<PetDTO>> petsGet() {

        List<PetDTO> response = petService.getPets();

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> petsIdDelete(String id) {
        return null;
    }

    @Override
    public ResponseEntity<PetDTO> petsIdGet(String id) {
        return null;
    }

    @Override
    public ResponseEntity<PetDTO> petsIdPut(PetDTO body, String id) {
        return null;
    }

    @Override
    public ResponseEntity<PetDTO> petsPost(@RequestBody @Valid PetDTO body) {
        PetDTO response = petService.addPet(body);

        return ResponseEntity.ok(response);
    }
}
