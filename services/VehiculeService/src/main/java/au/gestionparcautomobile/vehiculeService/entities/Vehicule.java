package au.gestionparcautomobile.vehiculeService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name="vehicules")
public class Vehicule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date dateAchat;
    private boolean disponibilite;
    private Long assuranceId;
    private Long vehiculeSpecifId;
    private Long enregistrementVehiculeId;
    @ElementCollection
    private List<Long> demandesIds;
}
