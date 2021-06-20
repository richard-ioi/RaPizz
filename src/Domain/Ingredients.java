package Domain;

import lombok.*;


/**
 * Model class for Ingredients entity.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients {
    /**
     * Ingredients id.
     */
    int idIngredients;

    /**
     * Ingredients name.
     */
    String nom;

    /**
     * Ingredients type.
     */
    String type;


}
