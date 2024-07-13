package au.gestionparcautomobile.vehiculeSpecificationService.repositories;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.VehiculeSpecif;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculeSpecifRepository extends JpaRepository<VehiculeSpecif, Long> {

    @Query("SELECT COUNT(v) > 0 FROM VehiculeSpecif v WHERE v.modele.id = :modeleId")
    boolean existsByModeleId(@Param("modeleId") Long modeleId);

    List<VehiculeSpecif> findByModeleMarqueId(Long marqueId);
    List<VehiculeSpecif> findByModeleId(Long modeleId);
    List<VehiculeSpecif> findByModeleMarqueIdAndModeleId(Long marqueId, Long modeleId);

    List<VehiculeSpecif> findByTypeCarburant(TypeCarburant typeCarburant);
    List<VehiculeSpecif> findByTypeImmatriculation(TypeImmatriculation typeImmatriculation);

    @Query("SELECT v FROM VehiculeSpecif v WHERE v.nChassis=:nChassis")
    Optional<VehiculeSpecif> findByNChassis(@Param("nChassis") String nChassis);
    Optional<VehiculeSpecif> findByImmatriculation(String immatriculation);
    Optional<VehiculeSpecif> findByVehiculeId(Long vehiculeId);
}
