package au.gestionparcautomobile.vehiculeService.mapper;

import au.gestionparcautomobile.vehiculeService.entities.Vehicule;
import au.gestionparcautomobile.vehiculeService.feignClients.assurance.AssuranceResponse;
import au.gestionparcautomobile.vehiculeService.feignClients.vehiculeSpecif.VehiculeSpecifResponse;
import au.gestionparcautomobile.vehiculeService.records.AssuranceRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VehiculeMapper {

    public Vehicule toEntity(VehiculeRequest vehiculeRequest) {
        Vehicule vehicule = new Vehicule();
        vehicule.setDateAchat(vehiculeRequest.dateAchat());
        vehicule.setDisponibilite(true);
        List<Long> assuranceIds = vehiculeRequest.assurance().stream()
                .map(AssuranceRequest::id)
                .collect(Collectors.toList());
        vehicule.setAssurances(assuranceIds);
        vehicule.setVehiculeSpecifId(vehiculeRequest.specificationsVehicule().id());
        return vehicule;
    }

    public VehiculeResponse toResponse(Vehicule vehicule, List<AssuranceResponse> assuranceResponses, VehiculeSpecifResponse specificationVehiculeResponse) {
        return new VehiculeResponse(
                vehicule.getId(),
                vehicule.getDateAchat(),
                vehicule.isDisponibilite(),
                assuranceResponses,
                specificationVehiculeResponse
        );
    }
}
