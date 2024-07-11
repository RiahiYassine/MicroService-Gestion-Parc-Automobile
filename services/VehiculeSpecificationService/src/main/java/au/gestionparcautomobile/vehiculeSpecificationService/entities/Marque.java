package au.gestionparcautomobile.vehiculeSpecificationService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "marques")
public class Marque {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomMarque;
    @OneToMany(mappedBy = "marque",cascade = CascadeType.ALL)
    private List<Modele> modeleAssocies;
}
