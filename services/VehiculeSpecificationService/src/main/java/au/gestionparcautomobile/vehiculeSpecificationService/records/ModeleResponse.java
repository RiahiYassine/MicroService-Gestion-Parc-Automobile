package au.gestionparcautomobile.vehiculeSpecificationService.records;

public record ModeleResponse(
        Long id,
        String modeleName,
        MarqueResponse marque
) {
}
