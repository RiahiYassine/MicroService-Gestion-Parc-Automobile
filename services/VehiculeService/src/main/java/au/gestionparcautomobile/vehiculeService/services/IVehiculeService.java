package au.gestionparcautomobile.vehiculeService.services;

import au.gestionparcautomobile.vehiculeService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeService.enums.TypeImmatriculation;
import au.gestionparcautomobile.vehiculeService.feignClients.assurance.AssuranceResponse;
import au.gestionparcautomobile.vehiculeService.records.VehiculeRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeResponse;
import java.util.List;

public interface IVehiculeService{
    VehiculeResponse createVehicule(VehiculeRequest vehicleRequest);
    VehiculeResponse getVehiculeById(Long id);
    VehiculeResponse updateVehicule(Long id, VehiculeRequest vehiculeRequest);
    void deleteVehicule(Long id);

    List<VehiculeResponse> getAllVehicules();

    List<VehiculeResponse> getVehiculesByDisponibilite(boolean disponibilite);
    List<VehiculeResponse> getVehiculesByTypeCarburant(TypeCarburant typeCarburant);
    List<VehiculeResponse> getVehiculesByModeleId(Long modeleId);
    List<VehiculeResponse> getVehiculesByMarqueId(Long marqueId);
    void assignAssuranceToVehicule(Long vehiculeId, Long assuranceId);
    void removeAssuranceFromVehicule(Long vehiculeId, Long assuranceId);


    List<VehiculeResponse> getVehiculesByNChassis(String nChassis);
    List<VehiculeResponse> getVehiculesByTypeImmatriculation(TypeImmatriculation typeImmatriculation);
    List<VehiculeResponse> getVehiculesByImmatriculation(String immatriculation);
    List<VehiculeResponse> getVehiculesByNombreDePlaces(int nombreDePlaces);
    List<AssuranceResponse> getAllAssurancesByVehiculeId(Long vehiculeId);
}
