package au.gestionparcautomobile.vehiculeSpecificationService.records;

import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;

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
