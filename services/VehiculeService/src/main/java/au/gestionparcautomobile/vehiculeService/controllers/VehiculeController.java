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
        System.out.println("\n\n"+vehiculeRequest);
        VehiculeResponse vehicleResponse = iVehiculeService.createVehicule(vehiculeRequest);
        return vehicleResponse;
    }


    @GetMapping("/{id}")
    public VehiculeResponse getVehiculeById(@PathVariable Long id) {
        VehiculeResponse vehiculeResponse = iVehiculeService.getVehiculeById(id);
        return vehiculeResponse;
    }



    @PutMapping("/{id}")
    public VehiculeResponse updateVehicle(@PathVariable Long id, @RequestBody VehiculeRequest vehiculeRequest) {
        VehiculeResponse updatedVehicle = iVehiculeService.updateVehicule(id, vehiculeRequest);
        return updatedVehicle;
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        iVehiculeService.deleteVehicule(id);
    }


    @GetMapping
    public List<VehiculeResponse> getVehicules() {
        List<VehiculeResponse> vehiculeResponse = iVehiculeService.getAllVehicules();
        return vehiculeResponse;
    }
    
}
