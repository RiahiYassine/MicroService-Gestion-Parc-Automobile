package au.gestionparcautomobile.vehiculeService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @PastOrPresent(message = "Date achat must be in the past or present")
    @NotNull(message = "Date achat is required")
    @Column(nullable = false)
    private Date dateAchat;

    @NotNull(message = "Date achat is required")
    @Column(nullable = false)
    private boolean disponibilite;

    @Column(nullable = false,updatable = false)
    private Long vehiculeSpecifId;

    @ElementCollection
    @CollectionTable(
            name = "vehicule_assurances",
            joinColumns = @JoinColumn(name = "vehicule_id")
    )
    @Column(name = "assurance_id")
    private List<Long> assurances;

    @ElementCollection
    private List<Long> demandesIds;

    //@Column(nullable = false,updatable = false)
    private Long enregistrementVehiculeId;
}
