package au.getstionparcautomobile.assuranceService.services;

import au.getstionparcautomobile.assuranceService.entities.Assurance;
import au.getstionparcautomobile.assuranceService.exceptions.AssuranceNotFoundException;
import au.getstionparcautomobile.assuranceService.mapper.AssuranceMapper;
import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;
import au.getstionparcautomobile.assuranceService.repositories.AssuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AssuranceServiceImpl implements IAssuranceService{

    private final AssuranceRepository assuranceRepository;
    private final AssuranceMapper assuranceMapper;

    @Override
    public AssuranceResponse getAssuranceById(Long id) {
        Assurance assurance = assuranceRepository.findById(id).orElseThrow(() -> new AssuranceNotFoundException("Assurance not found for this id :: " + id));;
        return assuranceMapper.toResponse(assurance);
    }

    @Override
    public AssuranceResponse createAssurance(AssuranceRequest assuranceRequest) {
        Assurance assurance = this.assuranceMapper.toEntity(assuranceRequest);
        Assurance assuranceSaved = this.assuranceRepository.save(assurance);
        AssuranceResponse assuranceResponse = assuranceMapper.toResponse(assuranceSaved);
        return assuranceResponse;
    }

    @Override
    public AssuranceResponse updateAssurance(Long id , AssuranceRequest assuranceRequest) {
        Assurance existingAssurance = assuranceRepository.findById(id).orElseThrow(() -> new AssuranceNotFoundException("Assurance not found for this id :: " + assuranceRequest.id()));
        existingAssurance.setNomFournisseur(assuranceRequest.nomFournisseur());
        existingAssurance.setNumeroPolice(assuranceRequest.numeroPolice());
        existingAssurance.setDateDebutCouverture(assuranceRequest.dateDebutCouverture());
        existingAssurance.setDateFinCouverture(assuranceRequest.dateFinCouverture());
        existingAssurance.setCout(assuranceRequest.cout());
        existingAssurance.setDetailsCouverture(assuranceRequest.detailsCouverture());
        existingAssurance.setNomFichier(assuranceRequest.nomFichier());
        existingAssurance.setDonneesFichier(assuranceRequest.donneesFichier());
        return assuranceMapper.toResponse(this.assuranceRepository.save(existingAssurance));
    }

    @Override
    public void deleteAssurance(Long id) {
        this.assuranceRepository.deleteById(id);
    }

}
