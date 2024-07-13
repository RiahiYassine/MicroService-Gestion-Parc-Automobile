package au.gestionparcautomobile.vehiculeSpecificationService.entities;

import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name="vehiculespecif")
public class VehiculeSpecif {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Numero de Chassis is required")
    @Column(nullable = false, unique = true)
    private String nChassis;

    @NotNull(message = "TypeImmatriculation is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeImmatriculation typeImmatriculation;

    @NotBlank(message = "Numero de Chassis is required")
    @Column(nullable = false, unique = true)
    private String immatriculation;

    @NotNull(message = "puissance required")
    @Column(nullable = false)
    @Positive(message = "puissance doit etre positive")
    private int puissance;

    @NotNull(message = "poids required")
    @Column(nullable = false)
    @Positive(message = "porids doit etre positive")
    private int poids;

    @NotNull(message = "nombre de place required")
    @Column(nullable = false)
    @Positive(message = "nombre de place doit etre positive")
    private int nomberPlace;

    @NotNull(message = "kilometrage required")
    @Column(nullable = false)
    @Positive(message = "kilometrage doit etre positive")
    private int kilometrage;

    @NotNull(message = "modele is required")
    @Column(nullable = false, unique = true)
    @ManyToOne
    @JoinColumn(name="modele_id")
    private Modele modele;

    @NotNull(message = "TypeCarburant is required")
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeCarburant typeCarburant;

    private Long vehiculeId;
}
