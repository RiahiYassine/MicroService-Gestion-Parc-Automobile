package au.gestionparcautomobile.vehiculeService.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ModeleRequest(
        Long id,
        @NotBlank
        String modeleName,
        @NotNull
        MarqueRequest marqueRequest
) {
}
