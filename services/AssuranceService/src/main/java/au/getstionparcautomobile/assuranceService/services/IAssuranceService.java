package au.getstionparcautomobile.assuranceService.services;

import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;

public interface IAssuranceService {

    AssuranceResponse createAssurance(AssuranceRequest assuranceRequest);
    AssuranceResponse updateAssurance(Long id , AssuranceRequest assuranceRequest);
    void deleteAssurance(Long id);
    AssuranceResponse getAssuranceById(Long id);
}
