package Domain;

import lombok.*;

/**
 * Model class from Vehicule entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicule {
    /**
     * Vehicule id
     */
    int idVehicule;

    /**
     * Vehicule immatriculation.
     */
    String immatriculation;

    /**
     * Vehicule type.
     */
    String type;
}
