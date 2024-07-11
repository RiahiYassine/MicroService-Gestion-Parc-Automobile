package au.gestionparcautomobile.vehiculeService.services;

import au.gestionparcautomobile.vehiculeService.entities.Vehicule;
import au.gestionparcautomobile.vehiculeService.feignClients.assurance.AssuranceClient;
import au.gestionparcautomobile.vehiculeService.feignClients.assurance.AssuranceResponse;
import au.gestionparcautomobile.vehiculeService.feignClients.vehiculeSpecif.VehiculeSpecifClient;
import au.gestionparcautomobile.vehiculeService.feignClients.vehiculeSpecif.VehiculeSpecifResponse;
import au.gestionparcautomobile.vehiculeService.mapper.VehiculeMapper;
import au.gestionparcautomobile.vehiculeService.records.AssuranceRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeRequest;
import au.gestionparcautomobile.vehiculeService.records.VehiculeResponse;
import au.gestionparcautomobile.vehiculeService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeService.repositories.VehiculeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculeServiceImpl implements IVehiculeService {

    private final VehiculeRepository vehiculeRepository;
    private final AssuranceClient assuranceClient;
    private final VehiculeSpecifClient vehiculeSpecifClient;
    private final VehiculeMapper vehiculeMapper;

    @Override
    @Transactional
    public VehiculeResponse createVehicule(VehiculeRequest vehiculeRequest) {
        System.out.println("34"+vehiculeRequest);
        Vehicule vehicule = Vehicule.builder()
                .dateAchat(vehiculeRequest.dateAchat())
                .disponibilite(true)
                .build();

        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
        Long vehiculeId = savedVehicule.getId();

        List<Long> assuranceIds = vehiculeRequest.assurance().stream()
                .map(assuranceRequest -> {
                    AssuranceRequest updatedAssuranceRequest = assuranceRequest.withVehiculeId(vehiculeId);
                    AssuranceResponse assuranceResponse = assuranceClient.createAssurance(updatedAssuranceRequest);
                    return assuranceResponse.id();
                })
                .collect(Collectors.toList());

        savedVehicule.setAssuranceId(assuranceIds);

        VehiculeSpecifRequest updatedSpecifRequest = vehiculeRequest.specificationsVehicule().withVehiculeId(vehiculeId);
        System.out.println("54"+updatedSpecifRequest);
        VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.createVehiculeSpecif(updatedSpecifRequest);

        savedVehicule.setVehiculeSpecifId(specificationResponse.id());

        Vehicule updatedVehicule = vehiculeRepository.save(savedVehicule);

        List<AssuranceResponse> assuranceResponses = assuranceIds.stream()
                .map(assuranceClient::getAssuranceById)
                .collect(Collectors.toList());

        return vehiculeMapper.toResponse(updatedVehicule, assuranceResponses, specificationResponse);
    }

    @Override
    public VehiculeResponse getVehiculeById(Long id) {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);
        if (optionalVehicule.isEmpty()) {
            throw new RuntimeException("Véhicule non trouvé");
        }

        Vehicule vehicule = optionalVehicule.get();
        List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                .map(assuranceClient::getAssuranceById)
                .collect(Collectors.toList());
        VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
        return vehiculeMapper.toResponse(vehicule, assuranceResponses, specificationResponse);
    }

    @Override
    public VehiculeResponse updateVehicule(Long id, VehiculeRequest vehiculeRequest) {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);
        if (optionalVehicule.isEmpty()) {
            throw new RuntimeException("Véhicule non trouvé");
        }
        Vehicule vehicule = optionalVehicule.get();

        List<Long> assuranceIds = vehiculeRequest.assurance().stream()
                .map(assuranceRequest -> {
                    System.out.println("\nhey1:\n"+assuranceRequest.id());
                    AssuranceResponse assuranceResponse = assuranceClient.updateAssurance(assuranceRequest.id(), assuranceRequest);
                    System.out.println("\nhey2\n");
                    return assuranceResponse.id();
                })
                .collect(Collectors.toList());

        VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.updateVehiculeSpecif(vehicule.getVehiculeSpecifId(), vehiculeRequest.specificationsVehicule());

        vehicule.setDateAchat(vehiculeRequest.dateAchat());
        vehicule.setAssuranceId(assuranceIds);

        Vehicule updatedVehicule = vehiculeRepository.save(vehicule);
        List<AssuranceResponse> assuranceResponses = assuranceIds.stream()
                .map(assuranceClient::getAssuranceById)
                .collect(Collectors.toList());
        return vehiculeMapper.toResponse(updatedVehicule, assuranceResponses, specificationResponse);
    }

    @Override
    public void deleteVehicule(Long id) {
        VehiculeResponse vehiculeResponse = getVehiculeById(id);
        if (vehiculeResponse == null) {
            throw new RuntimeException("Véhicule non trouvé");
        }
        vehiculeResponse.assurance().forEach(assurance -> assuranceClient.deleteAssuranceById(assurance.id()));
        vehiculeSpecifClient.deleteVehiculeSpecifById(vehiculeResponse.vehiculeSpecif().id());
        vehiculeRepository.deleteById(id);
    }

    @Override
    public List<VehiculeResponse> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream().map(vehicule -> {
            List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                    .map(assuranceClient::getAssuranceById)
                    .collect(Collectors.toList());
            var specificationResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
            return vehiculeMapper.toResponse(vehicule, assuranceResponses, specificationResponse);
        }).collect(Collectors.toList());
    }
}