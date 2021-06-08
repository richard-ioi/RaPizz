package Domain;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Livreur {
    int idLivreur;

    String nom;

    String prenom;

    int retards;
}
