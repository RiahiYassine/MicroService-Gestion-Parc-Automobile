package au.gestionparcautomobile.vehiculeService.feignClients.assurance;

import java.util.Date;

public record AssuranceResponse(
        Long id,
        String nomFournisseur,
        String numeroPolice,
        Date dateDebutCouverture,
        Date dateFinCouverture,
        double cout,
        String detailsCouverture,
        Long vehiculeId,
        String nomFichier,
        byte[] donneesFichier
) {
}
