package au.gestionparcautomobile.vehiculeService.services;

import au.gestionparcautomobile.vehiculeService.entities.Vehicule;
import au.gestionparcautomobile.vehiculeService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeService.enums.TypeImmatriculation;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        try {
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
            VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.createVehiculeSpecif(updatedSpecifRequest);
            savedVehicule.setVehiculeSpecifId(specificationResponse.id());
            Vehicule updatedVehicule = vehiculeRepository.save(savedVehicule);

            List<AssuranceResponse> assuranceResponses = assuranceIds.stream()
                    .map(assuranceClient::getAssuranceById)
                    .collect(Collectors.toList());

            return vehiculeMapper.toResponse(updatedVehicule, assuranceResponses, specificationResponse);
        }catch (Exception e){
            throw new RuntimeException("Failed to create vehicule", e);
        }
    }

    @Override
    public VehiculeResponse getVehiculeById(Long id) {
        Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);
        if (optionalVehicule.isEmpty()) {
            throw new RuntimeException("Véhicule non trouvé");
        }


        Vehicule vehicule = optionalVehicule.get();
        System.out.println(vehicule);
        System.out.println("\n1\n");
        List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                .map(assuranceClient::getAssuranceById)
                .collect(Collectors.toList());
        System.out.println("\n2\n");
        VehiculeSpecifResponse specificationResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
        return vehiculeMapper.toResponse(vehicule, assuranceResponses, specificationResponse);
    }

    @Override
    @Transactional
    public VehiculeResponse updateVehicule(Long id, VehiculeRequest vehiculeRequest) {
        try {
            Optional<Vehicule> optionalVehicule = vehiculeRepository.findById(id);
            if (optionalVehicule.isEmpty()) {
                throw new RuntimeException("Véhicule non trouvé");
            }
            Vehicule vehicule = optionalVehicule.get();

            List<Long> assuranceIds = vehiculeRequest.assurance().stream()
                    .map(assuranceRequest -> {
                        System.out.println(assuranceRequest.id());
                        AssuranceResponse assuranceResponse = assuranceClient.updateAssurance(assuranceRequest.id(), assuranceRequest);
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
        }catch(Exception e){
            throw new RuntimeException("Failed to update vehicule", e);
        }
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



    @Override
    public List<VehiculeResponse> getVehiculesByDisponibilite(boolean disponibilite) {
        List<Vehicule> vehicules = vehiculeRepository.findByDisponibilite(disponibilite);
        return vehicules.stream()
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculeResponse> getVehiculesByTypeCarburant(TypeCarburant typeCarburant) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream()
                .filter(vehicule -> {
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeSpecifResponse.typeCarburant() == typeCarburant;
                })
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculeResponse> getVehiculesByModeleId(Long modeleId) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream()
                .filter(vehicule -> {
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeSpecifResponse.modele().id().equals(modeleId);
                })
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculeResponse> getVehiculesByMarqueId(Long marqueId) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream()
                .filter(vehicule -> {
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeSpecifResponse.modele().marque().id().equals(marqueId);
                })
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public void assignAssuranceToVehicule(Long vehiculeId, Long assuranceId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new RuntimeException("Vehicule not found"));
        vehicule.getAssuranceId().add(assuranceId);
        vehiculeRepository.save(vehicule);
    }

    @Override
    public void removeAssuranceFromVehicule(Long vehiculeId, Long assuranceId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new RuntimeException("Vehicule not found"));
        vehicule.getAssuranceId().remove(assuranceId);
        vehiculeRepository.save(vehicule);
    }


    @Override
    public List<VehiculeResponse> getVehiculesByNChassis(String nChassis) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream()
                .filter(vehicule -> {
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeSpecifResponse.nChassis().equals(nChassis);
                })
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculeResponse> getVehiculesByTypeImmatriculation(TypeImmatriculation typeImmatriculation) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream()
                .filter(vehicule -> {
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeSpecifResponse.typeImmatriculation() == typeImmatriculation;
                })
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculeResponse> getVehiculesByImmatriculation(String immatriculation) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream()
                .filter(vehicule -> {
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeSpecifResponse.immatriculation().equals(immatriculation);
                })
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehiculeResponse> getVehiculesByNombreDePlaces(int nombreDePlaces) {
        List<Vehicule> vehicules = vehiculeRepository.findAll();
        return vehicules.stream()
                .filter(vehicule -> {
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeSpecifResponse.nombreDePlaces() == nombreDePlaces;
                })
                .map(vehicule -> {
                    List<AssuranceResponse> assuranceResponses = vehicule.getAssuranceId().stream()
                            .map(assuranceClient::getAssuranceById)
                            .collect(Collectors.toList());
                    VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifClient.getVehiculeSpecifById(vehicule.getVehiculeSpecifId());
                    return vehiculeMapper.toResponse(vehicule, assuranceResponses, vehiculeSpecifResponse);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<AssuranceResponse> getAllAssurancesByVehiculeId(Long vehiculeId) {
        Vehicule vehicule = vehiculeRepository.findById(vehiculeId).orElseThrow(() -> new RuntimeException("Vehicule not found"));
        return vehicule.getAssuranceId().stream()
                .map(assuranceClient::getAssuranceById)
                .collect(Collectors.toList());
    }
}