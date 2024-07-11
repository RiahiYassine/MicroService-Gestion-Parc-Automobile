package au.gestionparcautomobile.vehiculeService.feignClients.records;

public record ModeleResponse(
        Long id,
        String modeleName,
        MarqueResponse marque
) {
}
