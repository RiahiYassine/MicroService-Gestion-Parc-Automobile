package au.gestionparcautomobile.vehiculeSpecificationService.entities;

import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeCarburant;
import au.gestionparcautomobile.vehiculeSpecificationService.enums.TypeImmatriculation;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name="vehiculespecif")
public class VehiculeSpecif {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nChassis;
    @Enumerated(EnumType.STRING)
    private TypeImmatriculation typeImmatriculation;
    private String immatriculation;
    private int puissance;
    private int poids;
    private int nomberPlace;
    private int kilometrage;
    @ManyToOne
    @JoinColumn(name="modele_id")
    private Modele modele;
    @Enumerated(EnumType.STRING)
    private TypeCarburant typeCarburant;
    private Long vehiculeId;
}
