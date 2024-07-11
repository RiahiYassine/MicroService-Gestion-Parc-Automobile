package au.gestionparcautomobile.vehiculeSpecificationService.repositories;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.VehiculeSpecif;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeSpecifRepository extends JpaRepository<VehiculeSpecif, Long> {
    @Query("SELECT COUNT(v) > 0 FROM VehiculeSpecif v WHERE v.modele.id = :modeleId")
    boolean existsByModeleId(@Param("modeleId") Long modeleId);
}
