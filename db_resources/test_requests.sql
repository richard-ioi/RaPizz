USE RAPIZZ;

SET SQL_SAFE_UPDATES=0;
UPDATE Commande
INNER JOIN Pizza ON Commande.id_pizza=Pizza.id_pizza
SET Commande.prix = CASE 
						WHEN Commande.taille="humaine" THEN Pizza.prix
                        WHEN Commande.taille="naine" THEN Pizza.prix-(Pizza.prix*(1/3))
                        WHEN Commande.taille="ogresse" THEN Pizza.prix+(Pizza.prix*(1/3))
                        ELSE Commande.prix
                        END
			WHERE Commande.taille in ("humaine","naine","ogresse");
