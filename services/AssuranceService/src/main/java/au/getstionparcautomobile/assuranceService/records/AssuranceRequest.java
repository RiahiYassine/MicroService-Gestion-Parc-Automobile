package au.getstionparcautomobile.assuranceService.records;
import jakarta.validation.constraints.*;
import java.util.Date;

public record AssuranceRequest(

        Long id,

        @NotBlank(message = "Le nom du fournisseur ne doit pas être vide.")
        String nomFournisseur,

        @NotBlank(message = "Le numéro de police ne doit pas être vide.")
        String numeroPolice,

        @NotNull(message = "La date de début de couverture ne doit pas être nulle.")
        @PastOrPresent(message = "La date de début de couverture doit être aujourd'hui ou avant.")
        Date dateDebutCouverture,

        @NotNull(message = "La date de fin de couverture ne doit pas être nulle.")
        @Future(message = "La date de fin de couverture doit être dans le futur.")
        Date dateFinCouverture,

        @Positive(message = "Le coût doit être une valeur positive.")
        double cout,

        @NotBlank(message = "Détails de couverture sont requis")
        String detailsCouverture,

        Long vehiculeId,

        @NotBlank(message = "Détails de couverture sont requis")
        String nomFichier,

        @NotNull(message = "Les données du fichier ne doivent pas être nulles.")
        byte[] donneesFichier
) {
}
