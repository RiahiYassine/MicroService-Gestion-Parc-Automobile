package au.gestionparcautomobile.vehiculeService.services;

import au.gestionparcautomobile.vehiculeService.records.VehiculeRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeResponse;

import java.util.List;

public interface IVehiculeService{
    VehiculeResponse createVehicule(VehiculeRequest vehicleRequest);
    VehiculeResponse getVehiculeById(Long id);
    VehiculeResponse updateVehicule(Long id, VehiculeRequest vehiculeRequest);
    void deleteVehicule(Long id);

    List<VehiculeResponse> getAllVehicules();
}
