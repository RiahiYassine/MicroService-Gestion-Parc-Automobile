package au.gestionparcautomobile.vehiculeService.mapper;

import au.gestionparcautomobile.vehiculeService.entities.Vehicule;
import au.gestionparcautomobile.vehiculeService.feignClients.assurance.AssuranceResponse;
import au.gestionparcautomobile.vehiculeService.feignClients.vehiculeSpecif.VehiculeSpecifResponse;
import au.gestionparcautomobile.vehiculeService.records.VehiculeRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeResponse;
import org.springframework.stereotype.Component;

@Component
public class VehiculeMapper {

    public Vehicule toEntity(VehiculeRequest vehiculeRequest) {
        Vehicule vehicule = new Vehicule();
        vehicule.setDateAchat(vehiculeRequest.dateAchat());
        vehicule.setDisponibilite(true);
        return vehicule;
    }

    public VehiculeResponse toResponse(Vehicule vehicule, AssuranceResponse assuranceResponse, VehiculeSpecifResponse specificationVehiculeResponse) {
        return new VehiculeResponse(
                vehicule.getId(),
                vehicule.getDateAchat(),
                vehicule.isDisponibilite(),
                assuranceResponse,
                specificationVehiculeResponse
        );
    }
}
