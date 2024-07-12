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
    private Long vehiculeSpecifId;
    @ElementCollection
    @CollectionTable(name = "vehicule_assurances", joinColumns = @JoinColumn(name = "vehicule_id"))
    @Column(name = "assurance_id")
    private List<Long> assuranceId;
    @ElementCollection
    private List<Long> demandesIds;
    private Long enregistrementVehiculeId;
}
