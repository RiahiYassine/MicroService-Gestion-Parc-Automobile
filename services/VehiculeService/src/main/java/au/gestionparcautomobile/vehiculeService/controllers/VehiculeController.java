package au.gestionparcautomobile.vehiculeService.controllers;

import au.gestionparcautomobile.vehiculeService.records.VehiculeRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeResponse;
import au.gestionparcautomobile.vehiculeService.services.IVehiculeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/vehicules")
public class VehiculeController {
    private final IVehiculeService iVehiculeService;

    @PostMapping
    public VehiculeResponse createVehicle(@RequestBody @Valid VehiculeRequest vehiculeRequest) {
        return iVehiculeService.createVehicule(vehiculeRequest);
    }


    @GetMapping("/{id}")
    public VehiculeResponse getVehiculeById(@PathVariable Long id) {
        return iVehiculeService.getVehiculeById(id);
    }



    @PutMapping("/{id}")
    public VehiculeResponse updateVehicle(@PathVariable Long id, @RequestBody VehiculeRequest vehiculeRequest) {
        return iVehiculeService.updateVehicule(id, vehiculeRequest);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        iVehiculeService.deleteVehicule(id);
    }


    @GetMapping
    public List<VehiculeResponse> getVehicules() {
        return iVehiculeService.getAllVehicules();
    }
    
}
