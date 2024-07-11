package au.gestionparcautomobile.vehiculeSpecificationService.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "modele")
public class Modele {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomModel;
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;
    @OneToMany(mappedBy = "modele",cascade = CascadeType.ALL)
    private List<VehiculeSpecif> associatedVehSpecif;
}
