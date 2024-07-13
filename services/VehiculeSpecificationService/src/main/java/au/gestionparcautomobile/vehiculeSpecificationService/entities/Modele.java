package au.gestionparcautomobile.vehiculeSpecificationService.entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "modele")
public class Modele {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @NotBlank(message = "nomModel is required")
    @Column(nullable = false, unique = true)
    private String nomModel;

    @NotNull(message = "marque is required")
    @Column(nullable = false, unique = true)
    @ManyToOne
    @JoinColumn(name = "marque_id")
    private Marque marque;

    @OneToMany(mappedBy = "modele",cascade = CascadeType.ALL)
    private List<VehiculeSpecif> associatedVehSpecif;
}
