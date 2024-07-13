package au.gestionparcautomobile.vehiculeSpecificationService.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModeleRequest(

        Long id,

        @NotBlank(message = "Le nom du modèle ne doit pas être vide.")
        String modeleName,

        @NotNull(message = "L'ID de la marque ne doit pas être nul.")
        MarqueRequest marqueRequest
) {
}
