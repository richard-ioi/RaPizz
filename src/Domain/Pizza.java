package Domain;

import lombok.*;

import java.util.List;

/**
 * Model class from Pizza entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pizza {
    /**
     * Pizza id.
     */
    int idPizza;

    /**
     * Pizza name.
     */
    String nom;

    /**
     * Pizza standard price.
     */
    float prix;

    /**
     * Reference to pizza ingredient list, for table association.
     */
    List<Ingredients> ingredientsList;

}
