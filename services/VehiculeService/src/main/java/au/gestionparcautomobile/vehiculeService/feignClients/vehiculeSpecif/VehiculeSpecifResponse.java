package au.gestionparcautomobile.vehiculeService.feignClients.vehiculeSpecif;

import au.gestionparcautomobile.vehiculeService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeService.enums.TypeImmatriculation;
import au.gestionparcautomobile.vehiculeService.feignClients.records.ModeleResponse;
public record VehiculeSpecifResponse(
        Long id,
        String nChassis,
        TypeImmatriculation typeImmatriculation,
        String immatriculation,
        int puissance,
        int poids,
        int nombreDePlaces,
        int kilometrage,
        ModeleResponse modele,
        TypeCarburant typeCarburant,
        Long vehiculeId
) {


}
