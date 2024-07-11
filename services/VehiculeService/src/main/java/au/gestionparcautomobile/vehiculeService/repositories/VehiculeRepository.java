package au.gestionparcautomobile.vehiculeService.repositories;

import au.gestionparcautomobile.vehiculeService.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
}
