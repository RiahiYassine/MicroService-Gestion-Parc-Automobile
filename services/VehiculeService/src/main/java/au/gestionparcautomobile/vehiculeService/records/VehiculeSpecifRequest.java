package au.gestionparcautomobile.vehiculeService.records;

import au.gestionparcautomobile.vehiculeService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeService.enums.TypeImmatriculation;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record VehiculeSpecifRequest(

        Long id,

        @NotBlank(message = "numero de chassis est requis")
        String nChassis,

        @NotNull(message = "Type d'immatriculation est requis")
        @Enumerated(EnumType.STRING)
        TypeImmatriculation typeImmatriculation,

        @NotBlank(message = "Immatriculation est requise")
        String immatriculation,

        @NotNull(message = "Puissance est requise")
        @Positive(message = "puissance doit etre positive")
        int puissance,

        @NotNull(message = "Poids est requis")
        @Positive(message = "poids doit etre positive")
        int poids,

        @NotNull(message = "Nombre de places est requis")
        @Positive(message = "nombreDePlaces doit etre positive")
        int nombreDePlaces,

        @NotNull(message = "Kilométrage est requis")
        @Positive(message = "kilemetrage doit etre positive")
        int kilometrage,

        @NotNull(message = "Marque est requise")
        ModeleRequest modeleRequest,

        @NotNull(message = "Type de carburant est requis")
        @Enumerated(EnumType.STRING)
        TypeCarburant typeCarburant,

        Long vehiculeId
) {
        public VehiculeSpecifRequest withVehiculeId(Long vehiculeId) {
                return new VehiculeSpecifRequest(id, nChassis, typeImmatriculation, immatriculation, puissance, poids, nombreDePlaces, kilometrage, modeleRequest, typeCarburant, vehiculeId);
        }
}
