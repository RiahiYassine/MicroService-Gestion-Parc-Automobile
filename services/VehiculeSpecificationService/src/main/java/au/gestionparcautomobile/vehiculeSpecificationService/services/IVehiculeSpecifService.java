package au.gestionparcautomobile.vehiculeSpecificationService.services;

import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifResponse;

import java.util.List;

public interface IVehiculeSpecifService {

    VehiculeSpecifResponse createVehiculeSpecif(VehiculeSpecifRequest vehiculeSpecifRequest);
    VehiculeSpecifResponse updateVehiculeSpecif(Long id,VehiculeSpecifRequest vehiculeSpecifRequest);
    void deleteVehiculeSpecifById(Long id);
    VehiculeSpecifResponse getVehiculeSpecifById(Long id);


    List<VehiculeSpecifResponse> getAllVehiculeSpecifs();
    List<VehiculeSpecifResponse> getVehiculeSpecifsByMarqueId(Long marqueId);
    List<VehiculeSpecifResponse> getVehiculeSpecifsByModeleId(Long modeleId);
    List<VehiculeSpecifResponse> getVehiculeSpecifByMarqueAndModele(Long marqueId, Long modeleId);

    List<VehiculeSpecifResponse> getVehiculeSpecifsByTypeCarburant(TypeCarburant typeCarburant);
    List<VehiculeSpecifResponse> getVehiculeSpecifsByTypeImmatriculation(TypeImmatriculation typeImmatriculation);

    VehiculeSpecifResponse getVehiculeSpecifByNChassis(String nChassis);
    VehiculeSpecifResponse getVehiculeSpecifByImmatriculation(String immatriculation);
    VehiculeSpecifResponse getVehiculeSpecifByVehiculeId(Long vehiculeId);

    List<String> getListMarques();
    List<String> getListModelsByMarque(Long marqueId);



}
