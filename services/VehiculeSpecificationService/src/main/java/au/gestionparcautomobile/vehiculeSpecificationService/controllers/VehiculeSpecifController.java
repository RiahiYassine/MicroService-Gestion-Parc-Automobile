package au.gestionparcautomobile.vehiculeSpecificationService.controllers;

import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifResponse;
import au.gestionparcautomobile.vehiculeSpecificationService.services.IVehiculeSpecifService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehiculespecif")
public class VehiculeSpecifController {
    private final IVehiculeSpecifService iVehSpecifService;

    //tested
    @PostMapping
    public ResponseEntity<VehiculeSpecifResponse> createVehiculeSpecif(@RequestBody VehiculeSpecifRequest vehiculeSpecifRequest) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.createVehiculeSpecif(vehiculeSpecifRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeSpecifResponse);
    }

    //tested
    @PutMapping("/{id}")
    public ResponseEntity<VehiculeSpecifResponse> updateVehiculeSpecif(@PathVariable Long id, @RequestBody VehiculeSpecifRequest vehiculeSpecifRequest) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.updateVehiculeSpecif(id, vehiculeSpecifRequest);
        return ResponseEntity.ok(vehiculeSpecifResponse);
    }

    //tested
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculeSpecifById(@PathVariable Long id) {
        iVehSpecifService.deleteVehiculeSpecifById(id);
        return ResponseEntity.noContent().build();
    }
    //tested
    @GetMapping("/{id}")
    public ResponseEntity<VehiculeSpecifResponse> getVehiculeSpecifById(@PathVariable Long id) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.getVehiculeSpecifById(id);
        return ResponseEntity.ok(vehiculeSpecifResponse);
    }


    //tested
    @GetMapping
    public ResponseEntity<List<VehiculeSpecifResponse>> getAllVehiculeSpecifs() {
        List<VehiculeSpecifResponse> vehiculeSpecifResponses = iVehSpecifService.getAllVehiculeSpecifs();
        return ResponseEntity.ok(vehiculeSpecifResponses);
    }


    //tested
    @GetMapping("/marque/{marqueId}")
    public ResponseEntity<List<VehiculeSpecifResponse>> getVehiculeSpecifsByMarqueId(@PathVariable Long marqueId) {
        List<VehiculeSpecifResponse> vehiculeSpecifResponses = iVehSpecifService.getVehiculeSpecifsByMarqueId(marqueId);
        return ResponseEntity.ok(vehiculeSpecifResponses);
    }

    //tested
    @GetMapping("/modele/{modeleId}")
    public ResponseEntity<List<VehiculeSpecifResponse>> getVehiculeSpecifsByModeleId(@PathVariable Long modeleId) {
        List<VehiculeSpecifResponse> vehiculeSpecifResponses = iVehSpecifService.getVehiculeSpecifsByModeleId(modeleId);
        return ResponseEntity.ok(vehiculeSpecifResponses);
    }

    //tested
    @GetMapping("/marque/{marqueId}/modele/{modeleId}")
    public ResponseEntity<List<VehiculeSpecifResponse>> getVehiculeSpecifByMarqueAndModele(@PathVariable Long marqueId, @PathVariable Long modeleId) {
        List<VehiculeSpecifResponse> vehiculeSpecifResponses = iVehSpecifService.getVehiculeSpecifByMarqueAndModele(marqueId, modeleId);
        return ResponseEntity.ok(vehiculeSpecifResponses);
    }

    //tested
    @GetMapping("/typecarburant/{typeCarburant}")
    public ResponseEntity<List<VehiculeSpecifResponse>> getVehiculeSpecifsByTypeCarburant(@PathVariable TypeCarburant typeCarburant) {
        List<VehiculeSpecifResponse> vehiculeSpecifResponses = iVehSpecifService.getVehiculeSpecifsByTypeCarburant(typeCarburant);
        return ResponseEntity.ok(vehiculeSpecifResponses);
    }


    //tested
    @GetMapping("/typeimmatriculation/{typeImmatriculation}")
    public ResponseEntity<List<VehiculeSpecifResponse>> getVehiculeSpecifsByTypeImmatriculation(@PathVariable TypeImmatriculation typeImmatriculation) {
        List<VehiculeSpecifResponse> vehiculeSpecifResponses = iVehSpecifService.getVehiculeSpecifsByTypeImmatriculation(typeImmatriculation);
        return ResponseEntity.ok(vehiculeSpecifResponses);
    }
    //tested
    @GetMapping("/nchassis/{nChassis}")
    public ResponseEntity<VehiculeSpecifResponse> getVehiculeSpecifByNChassis(@PathVariable String nChassis) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.getVehiculeSpecifByNChassis(nChassis);
        return ResponseEntity.ok(vehiculeSpecifResponse);
    }

    //tested
    @GetMapping("/immatriculation/{immatriculation}")
    public ResponseEntity<VehiculeSpecifResponse> getVehiculeSpecifByImmatriculation(@PathVariable String immatriculation) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.getVehiculeSpecifByImmatriculation(immatriculation);
        return ResponseEntity.ok(vehiculeSpecifResponse);
    }

    //tested
    @GetMapping("/vehiculeid/{vehiculeId}")
    public ResponseEntity<VehiculeSpecifResponse> getVehiculeSpecifByVehiculeId(@PathVariable Long vehiculeId) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.getVehiculeSpecifByVehiculeId(vehiculeId);
        return ResponseEntity.ok(vehiculeSpecifResponse);
    }

    //tested
    @GetMapping("/marques")
    public ResponseEntity<List<String>> getListMarques() {
        List<String> marques = iVehSpecifService.getListMarques();
        return ResponseEntity.ok(marques);
    }

    //tested
    @GetMapping("/models/{marqueId}")
    public ResponseEntity<List<String>> getListModelsByMarque(@PathVariable Long marqueId) {
        List<String> models = iVehSpecifService.getListModelsByMarque(marqueId);
        return ResponseEntity.ok(models);
    }
}
