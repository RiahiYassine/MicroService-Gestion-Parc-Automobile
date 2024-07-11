package au.gestionparcautomobile.vehiculeSpecificationService.mapper;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Modele;
import au.gestionparcautomobile.vehiculeSpecificationService.entities.VehiculeSpecif;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VehiculeSpecifMapper {

    private final ModeleMapper modeleMapper;

    public VehiculeSpecif toEntity(VehiculeSpecifRequest vehiculeSpecifRequest, Modele modele) {
        return VehiculeSpecif.builder()
                .id(vehiculeSpecifRequest.id())
                .nChassis(vehiculeSpecifRequest.nChassis())
                .typeImmatriculation(vehiculeSpecifRequest.typeImmatriculation())
                .immatriculation(vehiculeSpecifRequest.immatriculation())
                .nomberPlace(vehiculeSpecifRequest.nombreDePlaces())
                .puissance(vehiculeSpecifRequest.puissance())
                .poids(vehiculeSpecifRequest.poids())
                .kilometrage(vehiculeSpecifRequest.kilometrage())
                .modele(modele)
                .typeCarburant(vehiculeSpecifRequest.typeCarburant())
                .vehiculeId(vehiculeSpecifRequest.vehiculeId())
                .build();
    }

    public VehiculeSpecifResponse toResponse(VehiculeSpecif vehiculeSpecif) {
        if(vehiculeSpecif == null){
            return null;
        }else{
            return new VehiculeSpecifResponse(
                    vehiculeSpecif.getId(),
                    vehiculeSpecif.getNChassis(),
                    vehiculeSpecif.getTypeImmatriculation(),
                    vehiculeSpecif.getImmatriculation(),
                    vehiculeSpecif.getPuissance(),
                    vehiculeSpecif.getPoids(),
                    vehiculeSpecif.getNomberPlace(),
                    vehiculeSpecif.getKilometrage(),
                    modeleMapper.toResponse(vehiculeSpecif.getModele()),
                    vehiculeSpecif.getTypeCarburant(),
                    vehiculeSpecif.getVehiculeId()
            );
        }
    }
}