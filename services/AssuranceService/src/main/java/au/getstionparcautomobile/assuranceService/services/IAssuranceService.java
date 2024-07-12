package au.getstionparcautomobile.assuranceService.services;
import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;

import java.util.Date;
import java.util.List;

public interface IAssuranceService {

    AssuranceResponse createAssurance(AssuranceRequest assuranceRequest);
    AssuranceResponse updateAssurance(Long id , AssuranceRequest assuranceRequest);
    void deleteAssurance(Long id);
    AssuranceResponse getAssuranceById(Long id);
    List<AssuranceResponse> getAllAssurances();


    List<AssuranceResponse> findByNomFournisseur(String nomFournisseur);
    List<AssuranceResponse> findByVehiculeId(Long vehiculeId);
    List<AssuranceResponse> findAllAssuranceExpired();
    long countByNomFournisseur(String nomFournisseur);
    AssuranceResponse findAssuranceByNumeroPolice(String numeroPolice);
    List<AssuranceResponse> findAssurancesByExpirationDate(Date expirationDate);
}
