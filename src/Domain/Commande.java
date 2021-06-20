package Domain;

import lombok.*;

import java.sql.Timestamp;

/**
 * Model class for Commande entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
    /**
     * Commande id.
     */
    int idCommande;

    /**
     * Ordered Pizza id.
     */
    int idPizza;

    /**
     * Assigned Livreur id.
     */
    int idLivreur;

    /**
     * Client id.
     */
    int idClient;

    /**
     * Assigned Vehicule id.
     */
    int idVehicule;

    /**
     * Original price.
     */
    float prix;

    /**
     * Chosen size.
     */
    String taille;

    /**
     * Departure date time for delivery.
     */
    Timestamp departLivraison;

    /**
     * Arrival date time for delivery.
     */
    Timestamp arriveLivraison;

}
