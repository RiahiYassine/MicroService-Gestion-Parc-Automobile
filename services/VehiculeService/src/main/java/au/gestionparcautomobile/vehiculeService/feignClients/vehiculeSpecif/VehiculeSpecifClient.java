package au.gestionparcautomobile.vehiculeService.feignClients.vehiculeSpecif;


import au.gestionparcautomobile.vehiculeService.records.VehiculeSpecifRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "speceficationVehicule-service",
        url = "${application.config.vehiculeSpecification-url}"
)
public interface VehiculeSpecifClient {

    @PostMapping
    VehiculeSpecifResponse createVehiculeSpecif(@RequestBody VehiculeSpecifRequest vehSpecifRequest);

    @GetMapping("{id}")
    VehiculeSpecifResponse getVehiculeSpecifById(@PathVariable Long id);

    @PutMapping("{id}")
    VehiculeSpecifResponse updateVehiculeSpecif(@PathVariable Long id ,@RequestBody VehiculeSpecifRequest vehiculeSpecifRequest);

    @DeleteMapping("{id}")
    void deleteVehiculeSpecifById(@PathVariable Long id);
}
