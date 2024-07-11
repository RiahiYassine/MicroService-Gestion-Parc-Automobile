package au.gestionparcautomobile.vehiculeService.demande;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(
        name = "demande-service")
public interface DemandeClient {
}
