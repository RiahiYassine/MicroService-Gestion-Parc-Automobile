package au.getstionparcautomobile.assuranceService.services;

import au.getstionparcautomobile.assuranceService.entities.Assurance;
import au.getstionparcautomobile.assuranceService.exceptions.AssuranceNotFoundException;
import au.getstionparcautomobile.assuranceService.mapper.AssuranceMapper;
import au.getstionparcautomobile.assuranceService.records.AssuranceRequest;
import au.getstionparcautomobile.assuranceService.records.AssuranceResponse;
import au.getstionparcautomobile.assuranceService.repositories.AssuranceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssuranceServiceImpl implements IAssuranceService{

    private final AssuranceRepository assuranceRepository;
    private final AssuranceMapper assuranceMapper;

    @Override
    public AssuranceResponse getAssuranceById(Long id) {
        System.out.println("id:"+id);
        Assurance assurance = assuranceRepository.findById(id).orElseThrow(() -> new AssuranceNotFoundException("Assurance not found for this id :: " + id));
        return assuranceMapper.toResponse(assurance);
    }

    @Override
    @Transactional
    public AssuranceResponse createAssurance(AssuranceRequest assuranceRequest) {
        Assurance assurance = this.assuranceMapper.toEntity(assuranceRequest);
        Assurance assuranceSaved = this.assuranceRepository.save(assurance);
        return assuranceMapper.toResponse(assuranceSaved);
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

    @Override
    @Transactional(readOnly=true)
    public List<AssuranceResponse> getAllAssurances() {
        try {
            List<Assurance> assurances = assuranceRepository.findAll();
            return assurances.stream()
                    .map(assuranceMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve assurances", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssuranceResponse> findByNomFournisseur(String nomFournisseur) {
        List<Assurance> assurances = assuranceRepository.findByNomFournisseur(nomFournisseur);
        return assurances.stream()
                .map(assuranceMapper::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    @Transactional(readOnly = true)
    public List<AssuranceResponse> findByVehiculeId(Long vehiculeId) {
        List<Assurance> assurances = assuranceRepository.findByVehiculeId(vehiculeId);
        return assurances.stream()
                .map(assuranceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public long countByNomFournisseur(String nomFournisseur) {
        return assuranceRepository.countByNomFournisseur(nomFournisseur);
    }

    @Override
    @Transactional(readOnly = true)
    public AssuranceResponse findAssuranceByNumeroPolice(String numeroPolice) {
        Assurance assurance = assuranceRepository.findByNumeroPolice(numeroPolice)
                .orElseThrow(() -> new AssuranceNotFoundException("Assurance not found with numeroPolice: " + numeroPolice));
        return assuranceMapper.toResponse(assurance);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssuranceResponse> findAllAssuranceExpired() {
        List<Assurance> assurances = assuranceRepository.findByDateFinCouvertureBefore(new Date());
        return assurances.stream()
                .map(assuranceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AssuranceResponse> findAssurancesByExpirationDate(Date expirationDate) {
        List<Assurance> assurances = assuranceRepository.findByDateFinCouverture(expirationDate);
        return assurances.stream()
                .map(assuranceMapper::toResponse)
                .collect(Collectors.toList());
    }
}
