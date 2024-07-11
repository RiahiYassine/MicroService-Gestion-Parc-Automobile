package au.getstionparcautomobile.assuranceService.repositories;

import au.getstionparcautomobile.assuranceService.entities.Assurance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssuranceRepository extends JpaRepository<Assurance, Long> {
}
