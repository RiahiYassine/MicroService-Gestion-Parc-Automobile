package au.getstionparcautomobile.assuranceService.mapper;

import au.getstionparcautomobile.assuranceService.entities.Assurance;
import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;
import org.springframework.stereotype.Component;

@Component
public class AssuranceMapper {


    public Assurance toEntity(AssuranceRequest assuranceRequest) {
        if(assuranceRequest == null){
            return null;
        }else{
            return Assurance.builder()
                    .id(assuranceRequest.id())
                    .nomFournisseur(assuranceRequest.nomFournisseur())
                    .numeroPolice(assuranceRequest.numeroPolice())
                    .dateDebutCouverture(assuranceRequest.dateDebutCouverture())
                    .dateFinCouverture(assuranceRequest.dateFinCouverture())
                    .cout(assuranceRequest.cout())
                    .detailsCouverture(assuranceRequest.detailsCouverture())
                    .vehiculeId(assuranceRequest.vehiculeId())
                    .nomFichier(assuranceRequest.nomFichier())
                    .donneesFichier(assuranceRequest.donneesFichier())
                    .build();
        }
    }


    public AssuranceResponse toResponse(Assurance assurance){
        if(assurance == null){
            return null;
        }else{
            return new AssuranceResponse(
                    assurance.getId(),
                    assurance.getNomFournisseur(),
                    assurance.getNumeroPolice(),
                    assurance.getDateDebutCouverture(),
                    assurance.getDateFinCouverture(),
                    assurance.getCout(),
                    assurance.getDetailsCouverture(),
                    assurance.getVehiculeId(),
                    assurance.getNomFichier(),
                    assurance.getDonneesFichier()
            );
        }
    }
}
