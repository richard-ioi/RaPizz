USE RAPIZZ;

INSERT INTO Clients(nom, prenom, adresse, telephone, solde, pizza_achete, depenses)
VALUES('FOUQUOIRE', 'Richard', '1 rue Keble Oaks', 0672374210, 52.50, 12, 160),
	('LARSSON', 'Kastor', '314 Homefield Pines', '0672374210', 15.00, 0, 0),
    ('BATES', 'Madeline', '3a Craythorne Lane', '0143123477', 30.23, 1, 15.50),
    ('ALESCIO', 'Thracius', '13 Thorpe Fairway', 0672374210, 3.01, 5, 75),
    ('BASS', 'Ekaterina', '5 Colridge Court', '0798100023', 12.99, 1, 12.99),
    ('KATO', 'Gitta', '10 Lonsdale Hills', '0612101132', 120.57, 3, 42.50),
    ('PEYTON', 'Ivan', '57 Southview Isaf', '0233904598', 2.21, 2, 28.98),
    ('BERNARDI', 'Dominika', '131 Barton Road', 0672374210, 15.90, 1, 14.50);

INSERT INTO Ingredients(nom, `type`)
VALUES('mozzarella', 'fromage'), -- 1
	('chevre', 'fromage'), -- 2
    ('bleu', 'fromage'), -- 3
    ('reblochon', 'fromage'), -- 4
    ('creme fraiche', 'base'), -- 5
    ('sauce tomate', 'base'), -- 6
    ('poulet', 'viande'), -- 7
    ('jambon', 'viande'), -- 8
    ('pepperoni', 'viande'), -- 9
    ('oignon', 'legume'), -- 10
    ('aubergine', 'legume'), -- 11
    ('roquette', 'legume'), -- 12
    ('olive', 'extra'), -- 13
    ('ananas', 'extra'); -- 14
    
INSERT INTO Pizza(nom,prix)
VALUES('vacanza',10.50), -- 1
	('insegnamento',15.99), -- 2
    ('tante', 12.99), -- 3
    ('favore', 13.00), -- 4
    ('sedere', 15.00), -- 5
    ('inserire', 13.50),  -- 6
    ('servizio', 8.99), -- 7
    ('spero', 10.99), -- 8
    ('rompere', 17.00); -- 9
    
INSERT INTO Pizza_Ingredients
VALUES(1, 6),(1, 1),(1,2),(1, 9), (1,13),
	(2, 5), (2,1),(2,10),
    (3,6),(3,1),(3,2),(3,3),(3,4),
    (4,5),(4,1),(4,4),(4,10),
    (5,6),(5,1),(5,8),(5,14),
    (6,1),(6,4),(6,6),(6,11),(6,12),
    (7,5),(7,13),(7,12),(7,7),(7,9),
    (8,5),(8,1),(8,2),(8,7),(8,12),
    (9,1),(9,2),(9,3),(9,4),(9,5),(9,7),(9,8),(9,9),(9,10),(9,11),(9,12),(9,13),(9,14);
    
INSERT INTO Vehicule(immatriculation, type)
VALUES ('BA731GG', 'scooter'), ('AZ666DM', 'camion'),('JK020RW', 'scooter');

INSERT INTO Livreur(nom, prenom, retards)
VALUES('Chevalier', 'Leon', 0), ('Carrel', 'Gérald', 3), ('Rodin', 'Arlene', 7);

INSERT INTO Commande(id_pizza, id_client, id_livreur, id_vehicule, prix, taille, depart_livraison, arrive_livraison)
VALUES (1, 5, 2, 3, 0, 'humaine', '2005-03-30 19:32:47', '2005-03-30 20:03:03'),
		(2, 1, 2, 2, 0, 'naine', '2005-03-30 12:05:47', '2005-03-30 12:20:03'),
        (3, 3, 1, 2, 0, 'ogresse', '2005-03-30 19:14:47', '2005-03-30 19:40:03'),
        (4, 1, 3, 1, 0, 'humaine', '2021-05-30 19:32:47', '2021-05-30 20:03:03'),
        (5, 6, 1, 1, 0, 'naine', '2021-06-30 19:32:47', '2021-06-30 20:03:03'),
        (4, 4, 3, 1, 0, 'ogresse', '2021-07-30 19:32:47', '2021-07-30 20:03:03'),
        (2, 5, 2, 1, 0, 'humaine', '2021-08-30 19:32:47', '2021-08-30 20:03:03'),
        (8, 3, 2, 2, 0, 'naine', '2021-08-30 19:32:47', '2021-08-30 20:03:03'),
        (6, 6, 1, 3, 0, 'ogresse', '2021-09-30 19:32:47', '2021-09-30 20:03:03'),
        (2, 1, 2, 2, 0, 'humaine', '2021-10-30 19:32:47', '2021-10-30 20:03:03')
        ;

   

