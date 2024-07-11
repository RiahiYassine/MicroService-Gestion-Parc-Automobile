package au.gestionparcautomobile.vehiculeService.feignClients.assurance;

import au.gestionparcautomobile.vehiculeService.records.AssuranceRequest;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(
        name = "assurance-service",
        url = "${application.config.assurance-url}"
)
public interface AssuranceClient {
    @PostMapping
    AssuranceResponse createAssurance(@RequestBody @Valid AssuranceRequest assuranceRequest);

    @GetMapping("{id}")
    AssuranceResponse getAssuranceById(@PathVariable Long id);

    @PutMapping("{id}")
    AssuranceResponse updateAssurance(@PathVariable Long id, @RequestBody AssuranceRequest assuranceRequest);

    @DeleteMapping("{id}")
    void deleteAssuranceById(@PathVariable Long id);
}
