package au.gestionparcautomobile.vehiculeSpecificationService.repositories;

import au.gestionparcautomobile.vehiculeSpecificationService.entities.Marque;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarqueRepository extends JpaRepository<Marque, Long> {
}
