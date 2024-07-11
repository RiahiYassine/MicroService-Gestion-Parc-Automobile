package au.gestionparcautomobile.vehiculeService.feignClients.records;

import au.gestionparcautomobile.vehiculeService.enums.Categorie;

import java.util.Date;

public record MaintenanceResponse(
        Long id,
        Date dateMaintenance,
        String resultatMaintenance,
        double cout,
        Categorie categorie
) {
}
