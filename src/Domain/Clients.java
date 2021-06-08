package Domain;

import lombok.*;

@Data
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
