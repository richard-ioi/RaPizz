package Domain;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pizza {
    int idPizza;

    String nom;

    float prix;

    List<Ingredients> ingredientsList;

}
