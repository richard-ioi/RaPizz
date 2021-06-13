package Domain;

import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Ingredients {
    int idIngredients;

    String nom;

    String type;

    Set<PizzaIngredients> pizzaList = new HashSet<>();

    void addPizza(Pizza pizza){
        pizzaList.add(new PizzaIngredients(pizza.getIdPizza()));
    }
}
