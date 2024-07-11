package au.getstionparcautomobile.assuranceService.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name="assurances")
public class Assurance {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomFournisseur;
    private String numeroPolice;
    private Date dateDebutCouverture;
    private Date dateFinCouverture;
    private double cout;
    private String detailsCouverture;
    private Long vehiculeId;
    private String nomFichier;
    private byte[] donneesFichier;
}
