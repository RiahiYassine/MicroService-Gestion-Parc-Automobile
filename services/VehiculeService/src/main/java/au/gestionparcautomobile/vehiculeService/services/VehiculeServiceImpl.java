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
        Vehicule vehicule = Vehicule.builder()
                .dateAchat(vehiculeRequest.dateAchat())
                .disponibilite(true)
                .build();

        Vehicule savedVehicule = vehiculeRepository.save(vehicule);
        Long vehiculeId = savedVehicule.getId();

        AssuranceRequest updatedAssuranceRequest = vehiculeRequest.assurance().withVehiculeId(vehiculeId);
        AssuranceResponse assuranceResponse = assuranceClient.createAssurance(updatedAssuranceRequest);

        VehiculeSpecifRequest updatedSpecifRequest = vehiculeRequest.specificationsVehicule().withVehiculeId(vehiculeId);
        VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.createVehiculeSpecif(updatedSpecifRequest);

        savedVehicule.setAssuranceId(assuranceResponse.id());
        savedVehicule.setVehiculeSpecifId(specificationResponse.id());

        Vehicule updatedVehicule = vehiculeRepository.save(savedVehicule);

        return vehiculeMapper.toResponse(updatedVehicule, assuranceResponse, specificationResponse);
    }

    @Override
    public VehiculeResponse getVehiculeById(Long id) {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);
        if (optionalVehicule.isEmpty()) {
            throw new RuntimeException("Véhicule non trouvé");
        }
        Vehicule vehicule = optionalVehicule.get();
        AssuranceResponse assuranceResponse = assuranceClient.getAssuranceById(vehicule.getAssuranceId());
        VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
        return vehiculeMapper.toResponse(vehicule, assuranceResponse, specificationResponse);
    }

    @Override
    public VehiculeResponse updateVehicule(Long id, VehiculeRequest vehiculeRequest) {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);
        if (optionalVehicule.isEmpty()) {
            throw new RuntimeException("Véhicule non trouvé");
        }
        Vehicule vehicule = optionalVehicule.get();

        AssuranceResponse assuranceResponse = assuranceClient.updateAssurance(vehicule.getAssuranceId(), vehiculeRequest.assurance());
        VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.updateVehiculeSpecif(vehicule.getVehiculeSpecifId(), vehiculeRequest.specificationsVehicule());

        vehicule.setDateAchat(vehiculeRequest.dateAchat());

        Vehicule updatedVehicule = vehiculeRepository.save(vehicule);

        return vehiculeMapper.toResponse(updatedVehicule, assuranceResponse, specificationResponse);
    }

    @Override
    public void deleteVehicule(Long id) {
        VehiculeResponse vehiculeResponse = getVehiculeById(id);
        if (vehiculeResponse == null) {
            throw new RuntimeException("Véhicule non trouvé");
        }
        assuranceClient.deleteAssuranceById(vehiculeResponse.assurance().id());
        vehiculeSpecifClient.deleteVehiculeSpecifById(vehiculeResponse.vehiculeSpecif().id());
        vehiculeRepository.deleteById(id);
    }

    @Override
    public List<VehiculeResponse> getAllVehicules() {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        List<VehiculeResponse> vehiculesResponse = vehicules.stream().map(vehicule -> {
            var assuranceResponse = assuranceClient.getAssuranceById(vehicule.getAssuranceId());
            var specificationResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
            return vehiculeMapper.toResponse(vehicule, assuranceResponse, specificationResponse);
        }).collect(Collectors.toList());
        return vehiculesResponse;
    }

}