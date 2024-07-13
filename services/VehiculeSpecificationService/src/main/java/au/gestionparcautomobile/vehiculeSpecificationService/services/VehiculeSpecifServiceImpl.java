package au.gestionparcautomobile.vehiculeSpecificationService.services;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Marque;
import au.gestionparcautomobile.vehiculeSpecificationService.entities.Modele;
import au.gestionparcautomobile.vehiculeSpecificationService.entities.VehiculeSpecif;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;
import au.gestionparcautomobile.vehiculeSpecificationService.exceptions.VehiculeSpecifNotFoundException;
import au.gestionparcautomobile.vehiculeSpecificationService.records.MarqueRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.ModeleRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifResponse;
import au.gestionparcautomobile.vehiculeSpecificationService.repositories.MarqueRepository;
import au.gestionparcautomobile.vehiculeSpecificationService.repositories.ModeleRepository;
import au.gestionparcautomobile.vehiculeSpecificationService.repositories.VehiculeSpecifRepository;
import lombok.RequiredArgsConstructor;
import au.gestionparcautomobile.vehiculeSpecificationService.mapper.VehiculeSpecifMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VehiculeSpecifServiceImpl implements IVehiculeSpecifService{
    private final MarqueRepository marqueRepository;
    private final ModeleRepository modeleRepository;
    private final VehiculeSpecifRepository vehiculeSpecifRepository;
    private final VehiculeSpecifMapper vehiculeSpecifMapper;


    @Override
    @Transactional
    public VehiculeSpecifResponse createVehiculeSpecif(VehiculeSpecifRequest vehiculeSpecifRequest) {
        Modele modele = getOrCreateModele(vehiculeSpecifRequest.modeleRequest());

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifMapper.toEntity(vehiculeSpecifRequest, modele);

        VehiculeSpecif savedVehiculeSpecif = vehiculeSpecifRepository.save(vehiculeSpecif);

        return vehiculeSpecifMapper.toResponse(savedVehiculeSpecif);
    }

    @Override
    @Transactional
    public VehiculeSpecifResponse updateVehiculeSpecif(Long id, VehiculeSpecifRequest vehiculeSpecifRequest) {

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findById(id).orElseThrow(() -> new VehiculeSpecifNotFoundException("VehiculeSpecif not found with id: " + id));

        Modele modele = getOrCreateModele(vehiculeSpecifRequest.modeleRequest());

        vehiculeSpecif.setNChassis(vehiculeSpecifRequest.nChassis());
        vehiculeSpecif.setTypeImmatriculation(vehiculeSpecifRequest.typeImmatriculation());
        vehiculeSpecif.setImmatriculation(vehiculeSpecifRequest.immatriculation());
        vehiculeSpecif.setNomberPlace(vehiculeSpecifRequest.nombreDePlaces());
        vehiculeSpecif.setPuissance(vehiculeSpecifRequest.puissance());
        vehiculeSpecif.setPoids(vehiculeSpecifRequest.poids());
        vehiculeSpecif.setKilometrage(vehiculeSpecifRequest.kilometrage());
        vehiculeSpecif.setModele(modele);
        vehiculeSpecif.setTypeCarburant(vehiculeSpecifRequest.typeCarburant());

        VehiculeSpecif savedVehiculeSpecif = vehiculeSpecifRepository.save(vehiculeSpecif);

        return vehiculeSpecifMapper.toResponse(savedVehiculeSpecif);
    }

    private Modele getOrCreateModele(ModeleRequest modeleRequest) {
        Marque marque = getOrCreateMarque(modeleRequest.marqueRequest());
        return modeleRepository.findByNomModelAndMarque(modeleRequest.modeleName(), marque)
                .orElseGet(() -> {
                    Modele newModele = new Modele();
                    newModele.setNomModel(modeleRequest.modeleName());
                    newModele.setMarque(marque);
                    return modeleRepository.save(newModele);
                });
    }

    private Marque getOrCreateMarque(MarqueRequest marqueRequest) {
        return marqueRepository.findByNomMarque(marqueRequest.nomMarque())
                .orElseGet(() -> {
                    Marque newMarque = new Marque();
                    newMarque.setNomMarque(marqueRequest.nomMarque());
                    return marqueRepository.save(newMarque);
                });
    }




    @Override
    @Transactional
    public void deleteVehiculeSpecifById(Long id) {

        System.out.println("id:"+id);
        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findById(id).orElseThrow(() -> new VehiculeSpecifNotFoundException("VehiculeSpecif not found with id: " + id));
        System.out.println("\nmodele id:"+vehiculeSpecif.getModele().getId());
        Modele modele = vehiculeSpecif.getModele();
        System.out.println("\nmarque id:"+modele.getMarque().getId());
        Marque marque = modele.getMarque();
        System.out.println("\nmarque:"+marque.getNomMarque());
        vehiculeSpecifRepository.delete(vehiculeSpecif);

        if (!vehiculeSpecifRepository.existsByModeleId(modele.getId())) {
            System.out.println("hey111");
            modeleRepository.delete(modele);
        }

        if (!modeleRepository.existsByMarqueId(marque.getId())) {
            System.out.println("hey222");
            marqueRepository.delete(marque);
        }
    }

    @Override
    public VehiculeSpecifResponse getVehiculeSpecifById(Long vehiculeSpecifId) {

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findById(vehiculeSpecifId).orElseThrow(() -> new VehiculeSpecifNotFoundException("VehiculeSpecif not found with id: " + vehiculeSpecifId));
        return vehiculeSpecifMapper.toResponse(vehiculeSpecif);
    }












    @Override @Transactional(readOnly = true)
    public List<VehiculeSpecifResponse> getAllVehiculeSpecifs() {
        return vehiculeSpecifRepository.findAll().stream()
                .map(vehiculeSpecifMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<VehiculeSpecifResponse> getVehiculeSpecifsByMarqueId(Long marqueId) {
        return vehiculeSpecifRepository.findByModeleMarqueId(marqueId).stream()
                .map(vehiculeSpecifMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<VehiculeSpecifResponse> getVehiculeSpecifsByModeleId(Long modeleId) {
        return vehiculeSpecifRepository.findByModeleId(modeleId).stream()
                .map(vehiculeSpecifMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<VehiculeSpecifResponse> getVehiculeSpecifByMarqueAndModele(Long marqueId, Long modeleId) {
        return vehiculeSpecifRepository.findByModeleMarqueIdAndModeleId(marqueId, modeleId).stream()
                .map(vehiculeSpecifMapper::toResponse)
                .collect(Collectors.toList());
    }



    @Override @Transactional(readOnly = true)
    public List<VehiculeSpecifResponse> getVehiculeSpecifsByTypeCarburant(TypeCarburant typeCarburant) {
        return vehiculeSpecifRepository.findByTypeCarburant(typeCarburant).stream()
                .map(vehiculeSpecifMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override @Transactional(readOnly = true)
    public List<VehiculeSpecifResponse> getVehiculeSpecifsByTypeImmatriculation(TypeImmatriculation typeImmatriculation) {
        return vehiculeSpecifRepository.findByTypeImmatriculation(typeImmatriculation).stream()
                .map(vehiculeSpecifMapper::toResponse)
                .collect(Collectors.toList());
    }













    @Override @Transactional(readOnly = true)
    public VehiculeSpecifResponse getVehiculeSpecifByNChassis(String nChassis) {
        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findByNChassis(nChassis).orElseThrow(() -> new VehiculeSpecifNotFoundException("VehiculeSpecif not found with NChassis: " + nChassis));
        return vehiculeSpecifMapper.toResponse(vehiculeSpecif);
    }

    @Override @Transactional(readOnly = true)
    public VehiculeSpecifResponse getVehiculeSpecifByImmatriculation(String immatriculation) {
        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findByImmatriculation(immatriculation)
                .orElseThrow(() -> new IllegalArgumentException("VehiculeSpecif not found with immatriculation: " + immatriculation));
        return vehiculeSpecifMapper.toResponse(vehiculeSpecif);
    }

    @Override @Transactional(readOnly = true)
    public VehiculeSpecifResponse getVehiculeSpecifByVehiculeId(Long vehiculeId) {
        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findByVehiculeId(vehiculeId)
                .orElseThrow(() -> new IllegalArgumentException("VehiculeSpecif not found with vehiculeId: " + vehiculeId));
        return vehiculeSpecifMapper.toResponse(vehiculeSpecif);
    }


    @Override @Transactional(readOnly = true)
    public List<String> getListMarques() {
        return marqueRepository.findAllNomMarque();
    }

    @Override @Transactional(readOnly = true)
    public List<String> getListModelsByMarque(Long marqueId) {
        return modeleRepository.findAllNomModelByMarqueId(marqueId);
    }
}
