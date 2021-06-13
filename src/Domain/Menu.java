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

    int idPizza;

    int idIngredient;

    Pizza pizza;

    Set<Ingredients> ingredientsSet;

}
