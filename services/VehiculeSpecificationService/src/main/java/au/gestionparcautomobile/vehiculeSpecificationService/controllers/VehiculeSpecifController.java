package au.gestionparcautomobile.vehiculeSpecificationService.controllers;

import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifResponse;
import au.gestionparcautomobile.vehiculeSpecificationService.services.IVehiculeSpecifService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehiculespecif")
public class VehiculeSpecifController {
    private final IVehiculeSpecifService iVehSpecifService;

    @PostMapping
    public ResponseEntity<VehiculeSpecifResponse> createVehiculeSpecif(@RequestBody VehiculeSpecifRequest vehiculeSpecifRequest) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.createVehiculeSpecif(vehiculeSpecifRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculeSpecifResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculeSpecifResponse> updateVehiculeSpecif(@PathVariable Long id, @RequestBody VehiculeSpecifRequest vehiculeSpecifRequest) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.updateVehiculeSpecif(id, vehiculeSpecifRequest);
        return ResponseEntity.ok(vehiculeSpecifResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehiculeSpecifById(@PathVariable Long id) {
        iVehSpecifService.deleteVehiculeSpecifById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculeSpecifResponse> getVehiculeSpecifById(@PathVariable Long id) {
        VehiculeSpecifResponse vehiculeSpecifResponse = iVehSpecifService.getVehiculeSpecifById(id);
        return ResponseEntity.ok(vehiculeSpecifResponse);
    }
}
