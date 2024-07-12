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
import au.gestionparcautomobile.vehiculeSpecificationService.mapper.VehiculeSpecifMapper;
import org.springframework.stereotype.Service;

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

        VehiculeSpecif vehiculeSpecif = vehiculeSpecifRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("VehiculeSpecif not found with id: " + id));

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
