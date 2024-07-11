package au.gestionparcautomobile.vehiculeService.records;

import au.gestionparcautomobile.vehiculeService.feignClients.assurance.AssuranceResponse;
import au.gestionparcautomobile.vehiculeService.feignClients.vehiculeSpecif.VehiculeSpecifResponse;

import java.util.Date;
import java.util.List;

public record VehiculeResponse(
        Long id,
        Date dateAchat,
        boolean disponibilite,
        List<AssuranceResponse> assurance,
        VehiculeSpecifResponse vehiculeSpecif
) {}
