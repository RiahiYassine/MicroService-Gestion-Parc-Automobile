package au.gestionparcautomobile.vehiculeService.feignClients.enregistrementVehicule;

import au.gestionparcautomobile.vehiculeService.feignClients.records.MaintenanceResponse;
import au.gestionparcautomobile.vehiculeService.feignClients.records.RaparationResponse;
import au.gestionparcautomobile.vehiculeService.feignClients.records.VesiteTechniqueResponse;

import java.util.List;

public record enregistrementVehiculeResponse(
        List<VesiteTechniqueResponse> vesiteTechniqueResponseList,
        List<RaparationResponse> raparationResponseList,
        List<MaintenanceResponse> maintenanceResponseList
) {
}
