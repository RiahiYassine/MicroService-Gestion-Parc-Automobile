package au.gestionparcautomobile.vehiculeService.feignClients.records;

import java.util.List;

public record MarqueResponse(
        Long id,
        String nomMarque,
        List<ModeleResponse> modeleAssocies
) {
}
