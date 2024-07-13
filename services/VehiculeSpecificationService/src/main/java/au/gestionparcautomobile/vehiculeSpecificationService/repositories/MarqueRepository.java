package au.gestionparcautomobile.vehiculeSpecificationService.repositories;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {
    Optional<Marque> findByNomMarque(String nomMarque);
    @Query("SELECT m.nomMarque FROM Marque m")
    List<String> findAllNomMarque();
}
