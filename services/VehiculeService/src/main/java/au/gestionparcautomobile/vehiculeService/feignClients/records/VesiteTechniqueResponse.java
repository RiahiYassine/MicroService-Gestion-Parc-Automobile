package au.gestionparcautomobile.vehiculeService.feignClients.records;

import java.util.Date;

public record VesiteTechniqueResponse(
        Long id,
        Date dateVisiteTechnique,
        String nomInspecteur,
        double cout,
        String nomFichier,
        byte[] donneesFichier
) {
}
