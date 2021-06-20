package Domain;

import lombok.*;

/**
 * Model class for Clients entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clients {
    /**
     * Clients id.
     */
    int idClient;

    /**
     * Clients name.
     */
    String nom;

    /**
     * Clients first name.
     */
    String prenom;

    /**
     * Clients adresse.
     */
    String adresse;

    /**
     * Clients phone number.
     */
    String telephone;

    /**
     * Clients balance.
     */
    float solde;

    /**
     * Clients amout of ordered pizzas.
     */
    int pizzaAchete;

    /**
     * Clients spendings.
     */
    float depenses;

}
