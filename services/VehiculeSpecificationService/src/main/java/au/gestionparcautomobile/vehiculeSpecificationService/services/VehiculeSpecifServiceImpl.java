package au.gestionparcautomobile.vehiculeSpecificationService.services;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Marque;
import au.gestionparcautomobile.vehiculeSpecificationService.entities.Modele;
import au.gestionparcautomobile.vehiculeSpecificationService.entities.VehiculeSpecif;
import au.gestionparcautomobile.vehiculeSpecificationService.records.MarqueRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.ModeleRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifRequest;
import au.gestionparcautomobile.vehiculeSpecificationService.records.VehiculeSpecifResponse;
import au.gestionparcautomobile.vehiculeSpecificationService.repositories.MarqueRepository;
import au.gestionparcautomobile.vehiculeSpecificationService.repositories.ModeleRepository;
import au.gestionparcautomobile.vehiculeSpecificationService.repositories.VehiculeSpecifRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import au.gestionparcautomobile.vehiculeSpecificationService.mapper.MarqueMapper;
import au.gestionparcautomobile.vehiculeSpecificationService.mapper.ModeleMapper;
import au.gestionparcautomobile.vehiculeSpecificationService.mapper.VehiculeSpecifMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehiculeSpecifServiceImpl implements IVehiculeSpecifService{
    private final MarqueRepository marqueRepository;
    private final ModeleRepository modeleRepository;
    private final VehiculeSpecifRepository vehiculeSpecifRepository;

    private final MarqueMapper marqueMapper;
    private final ModeleMapper modeleMapper;
    private final VehiculeSpecifMapper vehiculeSpecifMapper;


    @Override
    @Transactional
    public VehiculeSpecifResponse createVehiculeSpecif(VehiculeSpecifRequest vehiculeSpecifRequest) {

        MarqueRequest marqueRequest = vehiculeSpecifRequest.modeleRequest().marqueRequest();
        Marque marque = marqueMapper.toEntity(marqueRequest);
        Marque savedMarque = marqueRepository.save(marque);

        ModeleRequest modeleRequest = vehiculeSpecifRequest.modeleRequest();
        Modele modele = modeleMapper.toEntity(modeleRequest, savedMarque);
        Modele savedModele = modeleRepository.save(modele);

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifMapper.toEntity(vehiculeSpecifRequest, savedModele);

        VehiculeSpecif savedVehiculeSpecif = vehiculeSpecifRepository.save(vehiculeSpecif);
        VehiculeSpecifResponse vehiculeSpecifResponse = vehiculeSpecifMapper.toResponse(savedVehiculeSpecif);

        return vehiculeSpecifResponse;
    }

    @Override
    @Transactional
    public void updateVehiculeSpecif(Long id, VehiculeSpecifRequest vehiculeSpecifRequest) {

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("VehiculeSpecif not found with id: " + id));
        MarqueRequest marqueRequest = vehiculeSpecifRequest.modeleRequest().marqueRequest();
        Marque marque = marqueRepository.findById(vehiculeSpecif.getModele().getMarque().getId()).orElseThrow(() -> new IllegalArgumentException("Marque not found with id: " + vehiculeSpecif.getModele().getMarque().getId()));
        marque.setNomMarque(marqueRequest.nomMarque());
        Marque savedMarque = marqueRepository.save(marque);

        ModeleRequest modeleRequest = vehiculeSpecifRequest.modeleRequest();
        Modele modele = modeleRepository.findById(vehiculeSpecif.getModele().getId()).orElseThrow(() -> new IllegalArgumentException("Modele not found with id: " + vehiculeSpecif.getModele().getId()));
        modele.setNomModel(modeleRequest.modeleName());
        modele.setMarque(savedMarque);
        Modele savedModele = modeleRepository.save(modele);

        vehiculeSpecif.setNChassis(vehiculeSpecifRequest.nChassis());
        vehiculeSpecif.setTypeImmatriculation(vehiculeSpecifRequest.typeImmatriculation());
        vehiculeSpecif.setImmatriculation(vehiculeSpecifRequest.immatriculation());
        vehiculeSpecif.setNomberPlace(vehiculeSpecifRequest.nombreDePlaces());
        vehiculeSpecif.setKilometrage(vehiculeSpecifRequest.kilometrage());
        vehiculeSpecif.setModele(savedModele);
        vehiculeSpecif.setTypeCarburant(vehiculeSpecifRequest.typeCarburant());
        vehiculeSpecif.setVehiculeId(vehiculeSpecifRequest.vehiculeId());
        vehiculeSpecifRepository.save(vehiculeSpecif);

    }

    @Override
    @Transactional
    public void deleteVehiculeSpecifById(Long id) {

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("VehiculeSpecif not found with id: " + id));

        Modele modele = vehiculeSpecif.getModele();
        Marque marque = modele.getMarque();

        vehiculeSpecifRepository.delete(vehiculeSpecif);

        if (!vehiculeSpecifRepository.existsByModeleId(modele.getId())) {
            modeleRepository.delete(modele);
        }

        if (!modeleRepository.existsByMarqueId(modele.getId())) {
            marqueRepository.delete(marque);
        }
    }

    @Override
    public VehiculeSpecifResponse getVehiculeSpecifById(Long vehiculeSpecifId) {

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findById(vehiculeSpecifId).orElseThrow(() -> new IllegalArgumentException("VehiculeSpecif not found with id: " + vehiculeSpecifId));
        return vehiculeSpecifMapper.toResponse(vehiculeSpecif);
    }
}
