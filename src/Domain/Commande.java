package Domain;

import lombok.*;

import java.sql.Timestamp;

@Data
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
