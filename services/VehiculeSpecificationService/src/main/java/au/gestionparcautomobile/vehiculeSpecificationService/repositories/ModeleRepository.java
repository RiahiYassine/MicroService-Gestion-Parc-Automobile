package au.gestionparcautomobile.vehiculeSpecificationService.repositories;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Modele;
import au.gestionparcautomobile.vehiculeSpecificationService.entities.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ModeleRepository extends JpaRepository<Modele, Long> {
    Optional<Modele> findByNomModelAndMarque(String nomModel, Marque marque);

    @Query("SELECT COUNT(m) > 0 FROM Modele m WHERE m.marque.id = :marqueId")
    boolean existsByMarqueId(@Param("marqueId") Long marqueId);
}
