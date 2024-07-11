package au.gestionparcautomobile.vehiculeSpecificationService.mapper;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Marque;
import au.gestionparcautomobile.vehiculeSpecificationService.records.MarqueRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.MarqueResponse;
import org.springframework.stereotype.Component;

@Component
public class MarqueMapper {

    public Marque toEntity(MarqueRequest marqueRequest) {
        if(marqueRequest == null){
            return null;
        }else{
            return Marque.builder()
                    .nomMarque(marqueRequest.nomMarque())
                    .build();
        }
    }

    public MarqueResponse toResponse(Marque marque) {
        if (marque == null) {
            return null;
        } else {
            return new MarqueResponse(
                    marque.getId(),
                    marque.getNomMarque()
            );
        }
    }

}
