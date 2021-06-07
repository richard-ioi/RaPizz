package Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Commande {
    int idCommande;

    int idPizza;

    int idLivreur;

    int idClient;

    int idVehicule;

    float prix;

    String taille;

    Timestamp departLivraison;

    Timestamp arriveLivraison;

}
