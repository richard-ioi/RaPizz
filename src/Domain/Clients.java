package Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clients {
    int idClient;

    String nom;

    String prenom;

    String adresse;

    String telephone;

    float solde;

    int pizzaAchete;

    float depenses;

}
