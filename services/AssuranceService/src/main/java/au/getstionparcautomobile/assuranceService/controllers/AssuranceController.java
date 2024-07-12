package au.getstionparcautomobile.assuranceService.controllers;


import au.getstionparcautomobile.assuranceService.feignClients.VehiculeClient;
import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;
import au.getstionparcautomobile.assuranceService.services.IAssuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assurances")
public class AssuranceController {

    private final IAssuranceService iAssuranceService;
    private final VehiculeClient vehiculeClient;

    @PostMapping
    public ResponseEntity<AssuranceResponse> createAssurance(@RequestBody @Valid AssuranceRequest assuranceRequest){
        AssuranceResponse assuranceResponse = this.iAssuranceService.createAssurance(assuranceRequest);
        vehiculeClient.assignAssuranceToVehicule(assuranceRequest.vehiculeId(), assuranceResponse.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(assuranceResponse);
    }
    @PutMapping("/{id}")
    public ResponseEntity<AssuranceResponse> updateAssurance(@PathVariable Long id,@RequestBody @Valid AssuranceRequest assuranceRequest){
         AssuranceResponse assuranceResponse = this.iAssuranceService.updateAssurance(id,assuranceRequest);
         return ResponseEntity.ok().body(assuranceResponse);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAssuranceById(@PathVariable Long id){
        AssuranceResponse assuranceResponse = iAssuranceService.getAssuranceById(id);
        vehiculeClient.removeAssuranceFromVehicule(assuranceResponse.vehiculeId(), id);
        iAssuranceService.deleteAssurance(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AssuranceResponse> getAssuranceById(@PathVariable Long id){
        AssuranceResponse assuranceResponse = this.iAssuranceService.getAssuranceById(id);
        return ResponseEntity.ok(assuranceResponse);
    }

    @GetMapping
    public ResponseEntity<List<AssuranceResponse>> getAllAssurances() {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.getAllAssurances();
        return ResponseEntity.ok(assuranceResponses);
    }

    @GetMapping("/byNomFournisseur")
    public ResponseEntity<List<AssuranceResponse>> findByNomFournisseur(@RequestParam String nomFournisseur) {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.findByNomFournisseur(nomFournisseur);
        return new ResponseEntity<>(assuranceResponses, HttpStatus.OK);
    }

    @GetMapping("/byVehiculeId/{vehiculeId}")
    public ResponseEntity<List<AssuranceResponse>> findByVehiculeId(@PathVariable Long vehiculeId) {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.findByVehiculeId(vehiculeId);
        return new ResponseEntity<>(assuranceResponses, HttpStatus.OK);
    }

    @GetMapping("/expired")
    public ResponseEntity<List<AssuranceResponse>> findAllAssuranceExpired() {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.findAllAssuranceExpired();
        return new ResponseEntity<>(assuranceResponses, HttpStatus.OK);
    }

    @GetMapping("/countByNomFournisseur")
    public ResponseEntity<Long> countByNomFournisseur(@RequestParam String nomFournisseur) {
        long count = iAssuranceService.countByNomFournisseur(nomFournisseur);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

}
