package au.getstionparcautomobile.assuranceService.controllers;


import au.getstionparcautomobile.assuranceService.feignClients.VehiculeClient;
import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;
import au.getstionparcautomobile.assuranceService.services.IAssuranceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/assurances")
public class AssuranceController {

    private final IAssuranceService iAssuranceService;
    private final VehiculeClient vehiculeClient;

    //tested
    @PostMapping
    public ResponseEntity<AssuranceResponse> createAssurance(@RequestBody @Valid AssuranceRequest assuranceRequest){
        AssuranceResponse assuranceResponse = this.iAssuranceService.createAssurance(assuranceRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(assuranceResponse);
    }

    //tested
    @PostMapping("/createAndAssignAssurance")
    public ResponseEntity<AssuranceResponse> createAssuranceAndAssignItTovehicule(@RequestBody @Valid AssuranceRequest assuranceRequest){
        AssuranceResponse assuranceResponse = this.iAssuranceService.createAssurance(assuranceRequest);
        vehiculeClient.assignAssuranceToVehicule(assuranceRequest.vehiculeId(), assuranceResponse.id());
        return ResponseEntity.status(HttpStatus.CREATED).body(assuranceResponse);
    }
    //tested
    @PutMapping("/{id}")
    public ResponseEntity<AssuranceResponse> updateAssurance(@PathVariable Long id,@RequestBody @Valid AssuranceRequest assuranceRequest){
         AssuranceResponse assuranceResponse = this.iAssuranceService.updateAssurance(id,assuranceRequest);
         return ResponseEntity.ok().body(assuranceResponse);
    }

    //tested
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteAssuranceById(@PathVariable Long id){
        AssuranceResponse assuranceResponse = iAssuranceService.getAssuranceById(id);
        vehiculeClient.removeAssuranceFromVehicule(assuranceResponse.vehiculeId(), id);
        iAssuranceService.deleteAssurance(id);
        return ResponseEntity.noContent().build();
    }
    //tested
    @GetMapping("{id}")
    public ResponseEntity<AssuranceResponse> getAssuranceById(@PathVariable Long id){
        AssuranceResponse assuranceResponse = this.iAssuranceService.getAssuranceById(id);
        return ResponseEntity.ok().body(assuranceResponse);
    }
    //tested
    @GetMapping
    public ResponseEntity<List<AssuranceResponse>> getAllAssurances() {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.getAllAssurances();
        return ResponseEntity.ok().body(assuranceResponses);
    }

    //tested
    @GetMapping("/byNomFournisseur/{nomFournisseur}")
    public ResponseEntity<List<AssuranceResponse>> findByNomFournisseur(@PathVariable String nomFournisseur) {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.findByNomFournisseur(nomFournisseur);
        return ResponseEntity.ok().body(assuranceResponses);
    }
    //tested
    @GetMapping("/vehicule/{vehiculeId}")
    public ResponseEntity<List<AssuranceResponse>> findByVehiculeId(@PathVariable Long vehiculeId) {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.findByVehiculeId(vehiculeId);
        return ResponseEntity.ok().body(assuranceResponses);
    }
    //tested
    @GetMapping("/expired")
    public ResponseEntity<List<AssuranceResponse>> findAllAssuranceExpired() {
        List<AssuranceResponse> assuranceResponses = iAssuranceService.findAllAssuranceExpired();
        return ResponseEntity.ok().body(assuranceResponses);
    }
    //tested
    @GetMapping("/countByFournisseur/{nomFournisseur}")
    public ResponseEntity<Long> countByNomFournisseur(@PathVariable String nomFournisseur) {
        long count = iAssuranceService.countByNomFournisseur(nomFournisseur);
        return ResponseEntity.ok().body(count);
    }

    //tested
    @GetMapping("/byNumeroPolice/{numeroPolice}")
    public ResponseEntity<AssuranceResponse> findByNumeroPolice(@PathVariable String numeroPolice) {
        AssuranceResponse assuranceResponse = iAssuranceService.findAssuranceByNumeroPolice(numeroPolice);
        return ResponseEntity.ok().body(assuranceResponse);
    }

    //tested
    @GetMapping("/byExpirationDate/{expirationDate}")
    public ResponseEntity<List<AssuranceResponse>> findAssurancesByExpirationDate(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date expirationDate){
        List<AssuranceResponse> assuranceResponses = iAssuranceService.findAssurancesByExpirationDate(expirationDate);
        return ResponseEntity.ok().body(assuranceResponses);
    }

}
