package Domain;

import lombok.*;

/**
 * Model class for Livreur entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livreur {
    /**
     * Livreur id.
     */
    int idLivreur;

    /**
     * Livreur name.
     */
    String nom;

    /**
     * Livreur first name.
     */
    String prenom;

    /**
     * Livreur delays.
     */
    int retards;
}
