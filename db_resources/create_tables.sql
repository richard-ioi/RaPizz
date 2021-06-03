CREATE DATABASE PIZZARAPIZZ;
USE PIZZARAPIZZ;

CREATE TABLE Clients (
  id_client INT AUTO_INCREMENT,
  nom VARCHAR(25) NOT NULL,
  prenom VARCHAR(25) NOT NULL,
  adresse VARCHAR(50) NOT NULL,
  telephone VARCHAR(10),
  solde DECIMAL(10,2) NOT NULL,
  pizza_achete INT,
  depenses DECIMAL(10,2),
  PRIMARY KEY (id_client)
);

CREATE TABLE Commande (
	id_commande INT AUTO_INCREMENT,
    id_pizza INT NOT NULL,
    id_client INT NOT NULL,
    id_livreur INT NOT NULL,
    id_vehicule INT NOT NULL,
    depart_livraison DATETIME NOT NULL,
    arrive_livraison DATETIME NOT NULL,
    PRIMARY KEY (id_commande)
);

CREATE TABLE Livreur(
   id_livreur INT,
   nom VARCHAR(50),
   prenom VARCHAR(50),
   retards VARCHAR(50),
   PRIMARY KEY(id_livreur)
);

CREATE TABLE Vehicule(
   id_vehicule INT AUTO_INCREMENT,
   immatriculation VARCHAR(10) NOT NULL,
   `type` VARCHAR(10) NOT NULL,
   -- id_commande INT NOT NULL,
   PRIMARY KEY(id_vehicule)
);

CREATE TABLE Pizza(
	id_pizza INT AUTO_INCREMENT,
    nom VARCHAR(20) NOT NULL,
    taille VARCHAR(8) NOT NULL,
    id_ingredients INT NOT NULL,
    prix DECIMAL(10,2) NOT NULL,
    PRIMARY KEY (id_pizza)
);

CREATE TABLE Ingredients(
	id_ingredient INT AUTO_INCREMENT,
    nom VARCHAR(20) NOT NULL,
    `type`VARCHAR(15) NOT NULL,
    PRIMARY KEY (id_ingredient)
);

-- TABLE INTERMEDIAIRE MANY TO MANY PIZZA INGREDIENTS
CREATE TABLE Pizza_Ingredients(
    id_pizza INT,
    id_ingredient INT,
    CONSTRAINT fk_pizza FOREIGN KEY (id_pizza) REFERENCES Pizza(id_pizza),
    CONSTRAINT fk_ingredient FOREIGN KEY (id_ingredient) REFERENCES Ingredients(id_ingredient),
    UNIQUE(id_pizza,id_ingredient)
);

-- TABLE INTERMEDIAIRE MANY TO MANY PIZZA COMMANDE
CREATE TABLE Commande_Pizza(
    id_pizza INT,
    id_commande INT,
    CONSTRAINT fk_pizza_commande FOREIGN KEY (id_pizza) REFERENCES Pizza(id_pizza),
    CONSTRAINT fk_commande FOREIGN KEY (id_commande) REFERENCES Commande(id_commande),
    UNIQUE(id_pizza,id_commande)
);

-- CONTRAINTE CLE ETRANGERE RELATION ONE TO MANY CLIENT-COMMANDE (un client passe plusieurs commandes, une commande est associée a un client)
ALTER TABLE Commande
	ADD CONSTRAINT fk_commande_client FOREIGN KEY (id_client) REFERENCES Clients(id_client);
    
 -- CONTRAINTE CLE ETRANGERE RELATION ONE TO MANY VEHICULE-COMMANDE (un vehicule a plusieurs commandes, une commande est associée a un vehicule)   
ALTER TABLE Commande
	ADD CONSTRAINT fk_commande_vehicule FOREIGN KEY (id_vehicule) REFERENCES Vehicule(id_vehicule);

-- CONTRAINTE CLE ETRANGERE RELATION ONE TO MANY Livreur-COMMANDE (un livreur a plusieurs commandes, une commande est associée a un livreur)   
ALTER TABLE Commande 
	ADD CONSTRAINT fk_commande_livreur FOREIGN KEY (id_livreur) REFERENCES Livreur(id_livreur);

