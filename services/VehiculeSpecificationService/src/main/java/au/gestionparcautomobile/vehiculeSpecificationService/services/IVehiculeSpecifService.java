package au.gestionparcautomobile.vehiculeSpecificationService.services;

import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifResponse;

public interface IVehiculeSpecifService {

    VehiculeSpecifResponse createVehiculeSpecif(VehiculeSpecifRequest vehiculeSpecifRequest);
    VehiculeSpecifResponse updateVehiculeSpecif(Long id,VehiculeSpecifRequest vehiculeSpecifRequest);
    void deleteVehiculeSpecifById(Long id);
    VehiculeSpecifResponse getVehiculeSpecifById(Long id);
}
