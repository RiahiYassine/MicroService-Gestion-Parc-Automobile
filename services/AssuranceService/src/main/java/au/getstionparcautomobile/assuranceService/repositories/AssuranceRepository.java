package au.getstionparcautomobile.assuranceService.repositories;

import au.getstionparcautomobile.assuranceService.entities.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface AssuranceRepository extends JpaRepository<Assurance, Long> {
    List<Assurance> findByNomFournisseur(String nomFournisseur);
    List<Assurance> findByVehiculeId(Long vehiculeId);
    List<Assurance> findByDateFinCouvertureBefore(Date thresholdDate);
    Optional<Assurance> findByNumeroPolice(String numeroPolice);
    long countByNomFournisseur(String nomFournisseur);
    List<Assurance> findByDateFinCouverture(Date expirationDate);
}
