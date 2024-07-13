package au.gestionparcautomobile.vehiculeService.records;

import jakarta.validation.constraints.NotBlank;

public record MarqueRequest(

        Long id,

        @NotBlank(message = "Le nom de la marque ne doit pas être vide.")
        String nomMarque
) {
}
