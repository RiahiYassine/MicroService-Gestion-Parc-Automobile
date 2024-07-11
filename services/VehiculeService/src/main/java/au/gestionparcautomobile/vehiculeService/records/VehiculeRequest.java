package au.gestionparcautomobile.vehiculeService.records;

import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;

public record VehiculeRequest(
        Date dateAchat,
        @NotNull(message = "Vehicule doit avoir une assurance")
        List<AssuranceRequest> assurance,
        @NotNull(message = "Veuillez saisir les informations de vehicule")
        VehiculeSpecifRequest specificationsVehicule
) {}
