package au.gestionparcautomobile.vehiculeService.controllers;

import au.gestionparcautomobile.vehiculeService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeService.enums.TypeImmatriculation;
import au.gestionparcautomobile.vehiculeService.feignClients.assurance.AssuranceResponse;
import au.gestionparcautomobile.vehiculeService.records.VehiculeRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeResponse;
import au.gestionparcautomobile.vehiculeService.services.IVehiculeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicules")
public class VehiculeController {
    private final IVehiculeService iVehiculeService;

    //tested
    @PostMapping
    public VehiculeResponse createVehicle(@RequestBody @Valid VehiculeRequest vehiculeRequest) {
        return iVehiculeService.createVehicule(vehiculeRequest);
    }

    //tested
    @GetMapping("/{id}")
    public VehiculeResponse getVehiculeById(@PathVariable Long id) {
        return iVehiculeService.getVehiculeById(id);
    }

    //tested
    @PutMapping("/{id}")
    public VehiculeResponse updateVehicle(@PathVariable Long id, @RequestBody @Valid VehiculeRequest vehiculeRequest) {
        return iVehiculeService.updateVehicule(id, vehiculeRequest);
    }

    //tested
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        iVehiculeService.deleteVehicule(id);
    }

    //tested
    @GetMapping
    public List<VehiculeResponse> getVehicules() {
        return iVehiculeService.getAllVehicules();
    }

    //tested
    @GetMapping("/disponibilite/{disponibilite}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByDisponibilite(@PathVariable boolean disponibilite) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByDisponibilite(disponibilite);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }

    //tested
    @GetMapping("/typecarburant/{typeCarburant}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByTypeCarburant(@PathVariable TypeCarburant typeCarburant) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByTypeCarburant(typeCarburant);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }


    //tested
    @GetMapping("/modele/{modeleId}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByModeleId(@PathVariable Long modeleId) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByModeleId(modeleId);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }

    //tested
    @GetMapping("/marque/{marqueId}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByMarqueId(@PathVariable Long marqueId) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByMarqueId(marqueId);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }

    //tested
    @PostMapping("/{vehiculeId}/assurances/{assuranceId}")
    public ResponseEntity<Void> assignAssuranceToVehicule(@PathVariable Long vehiculeId, @PathVariable Long assuranceId) {
        iVehiculeService.assignAssuranceToVehicule(vehiculeId, assuranceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    //tested
    @DeleteMapping("/{vehiculeId}/assurances/{assuranceId}")
    public ResponseEntity<Void> removeAssuranceFromVehicule(@PathVariable Long vehiculeId, @PathVariable Long assuranceId) {
        iVehiculeService.removeAssuranceFromVehicule(vehiculeId, assuranceId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //tested
    @GetMapping("/nChassis/{nChassis}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByNChassis(@PathVariable String nChassis) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByNChassis(nChassis);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }

    //tested
    @GetMapping("/typeImmatriculation/{typeImmatriculation}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByTypeImmatriculation(@PathVariable TypeImmatriculation typeImmatriculation) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByTypeImmatriculation(typeImmatriculation);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }

    //tested
    @GetMapping("/immatriculation/{immatriculation}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByImmatriculation(@PathVariable String immatriculation) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByImmatriculation(immatriculation);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }

    //tested
    @GetMapping("/nombreDePlaces/{nombreDePlaces}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByNombreDePlaces(@PathVariable int nombreDePlaces) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByNombreDePlaces(nombreDePlaces);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }

    //tested
    @GetMapping("/{vehiculeId}/assurances")
    public ResponseEntity<List<AssuranceResponse>> getAllAssurancesByVehiculeId(@PathVariable Long vehiculeId) {
        List<AssuranceResponse> assuranceResponses = iVehiculeService.getAllAssurancesByVehiculeId(vehiculeId);
        return new ResponseEntity<>(assuranceResponses, HttpStatus.OK);
    }

    @GetMapping("/modele/{modeleId}/marque/{marqueId}")
    public ResponseEntity<List<VehiculeResponse>> getVehiculesByModeleIdAndMarqueId(@PathVariable Long modeleId, @PathVariable Long marqueId) {
        List<VehiculeResponse> vehiculeResponses = iVehiculeService.getVehiculesByModeleIdAndMarqueId(modeleId, marqueId);
        return new ResponseEntity<>(vehiculeResponses, HttpStatus.OK);
    }
}