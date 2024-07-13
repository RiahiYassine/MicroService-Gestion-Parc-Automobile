package au.getstionparcautomobile.assuranceService.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "assurances")
public class Assurance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(insertable = false, updatable = false)
    private Long id;

    @NotBlank(message = "Nom fournisseur is required")
    @Column(nullable = false, unique = true)
    private String nomFournisseur;

    @NotBlank(message = "Numéro de police is required")
    @Column(nullable = false, unique = true)
    private String numeroPolice;

    @PastOrPresent(message = "Date début couverture must be in the past or present")
    @NotNull(message = "Date début couverture is required")
    @Column(nullable = false)
    private Date dateDebutCouverture;

    @Future(message = "Date fin couverture must be in the future")
    @NotNull(message = "Date fin couverture is required")
    @Column(nullable = false)
    private Date dateFinCouverture;

    @Positive(message = "Cout must be positive")
    @NotNull(message = "Cout is required")
    @Column(nullable = false)
    private double cout;

    @NotBlank(message = "Détails couverture is required")
    @Size(max = 1000, message = "Détails couverture must not exceed 1000 characters")
    @Column(nullable = false)
    private String detailsCouverture;

    @Column(nullable = false,updatable = false)
    private Long vehiculeId;

    @NotBlank(message = "Nom fichier is required")
    @Column(nullable = false)
    private String nomFichier;

    @NotNull(message = "Données fichier is required")
    @Lob
    @Column(nullable = false)
    private byte[] donneesFichier;
}
