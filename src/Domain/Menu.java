package Domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Fields for creating the menu.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    /**
     * Pizza id.
     */
    int idPizza;

    /**
     * Ingredient id.
     */
    int idIngredient;

    /**
     * Pizza object.
     */
    Pizza pizza;

    /**
     * List of pizza's Ingredients.
     */
    Set<String> ingredientsSet;

}
