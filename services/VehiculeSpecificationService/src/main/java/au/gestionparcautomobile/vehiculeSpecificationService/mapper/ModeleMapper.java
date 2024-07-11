package au.gestionparcautomobile.vehiculeSpecificationService.mapper;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Marque;
import au.gestionparcautomobile.vehiculeSpecificationService.entities.Modele;
import au.gestionparcautomobile.vehiculeSpecificationService.records.ModeleRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.ModeleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ModeleMapper {

    private final MarqueMapper marqueMapper;

    public Modele toEntity(ModeleRequest modeleRequest,Marque marque) {
        if(modeleRequest == null){
            return null;
        }else{
            return Modele.builder()
                    .id(modeleRequest.id())
                    .nomModel(modeleRequest.modeleName())
                    .marque(marque)
                    .build();
        }
    }

    public ModeleResponse toResponse(Modele modele) {
        if(modele == null){
            return null;
        }else{
            return new ModeleResponse(
                    modele.getId(),
                    modele.getNomModel(),
                    marqueMapper.toResponse(modele.getMarque())
            );
        }
    }

}
