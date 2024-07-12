package au.gestionparcautomobile.vehiculeService.repositories;

import au.gestionparcautomobile.vehiculeService.entities.Vehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehiculeRepository extends JpaRepository<Vehicule, Long> {
    List<Vehicule> findByDisponibilite(boolean disponibilite);
}
