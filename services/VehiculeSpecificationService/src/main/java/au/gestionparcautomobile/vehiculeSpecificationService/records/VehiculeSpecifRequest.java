package au.gestionparcautomobile.vehiculeSpecificationService.records;

import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;


public record VehiculeSpecifRequest(
        Long id,
        @NotNull(message = "VIN est requis")
        String nChassis,
        @NotNull(message = "Type d'immatriculation est requis")
        @Enumerated(EnumType.STRING)
        TypeImmatriculation typeImmatriculation,
        @NotNull(message = "Immatriculation est requise")
        String immatriculation,
        @NotNull(message = "Puissance est requise")
        int puissance,
        @NotNull(message = "Poids est requis")
        int poids,
        @NotNull(message = "Nombre de places est requis")
        int nombreDePlaces,
        @NotNull(message = "Kilométrage est requis")
        int kilometrage,
        @NotNull(message = "Marque est requise")
        ModeleRequest modeleRequest,
        @NotNull(message = "Type de carburant est requis")
        @Enumerated(EnumType.STRING)
        TypeCarburant typeCarburant,
        Long vehiculeId
) {
}
