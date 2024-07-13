package au.gestionparcautomobile.vehiculeSpecificationService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "marques")
public class Marque {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @NotBlank(message = "nomModel is required")
    @Column(nullable = false, unique = true)
    private String nomMarque;

    @OneToMany(mappedBy = "marque",cascade = CascadeType.ALL)
    private List<Modele> modeleAssocies;
}
