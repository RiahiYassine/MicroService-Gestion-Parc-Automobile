package au.gestionparcautomobile.vehiculeService.feignClients.records;

import java.util.Date;

public record RaparationResponse(
        Long id,
        Date dateReparation,
        String numeroFacture,
        double cout,
        String description,
        String nomFichier,
        byte[] donneesFichier
) {
}
