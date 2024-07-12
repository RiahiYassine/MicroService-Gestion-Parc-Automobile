package au.getstionparcautomobile.assuranceService.feignClients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "vehicule-service", url = "${application.config.vehicule-url}")

public interface VehiculeClient {

    @PostMapping("/{vehiculeId}/assurances/{assuranceId}")
    void assignAssuranceToVehicule(@PathVariable Long vehiculeId, @PathVariable Long assuranceId);

    @DeleteMapping("/{vehiculeId}/assurances/{assuranceId}")
    void removeAssuranceFromVehicule(@PathVariable Long vehiculeId, @PathVariable Long assuranceId);
}
