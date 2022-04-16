![Logo](https://i.imgur.com/YzYLQq9.png)


# Zimdim Football - Projet Java

Jeu inspiré de SoRare, réalisé dans le cadre du cours de "Programmation objet en Java" encadré par Julien Cohen à Polytech Nantes.

Le jeu consiste en une application Java permettant d'acheter et revendre des cartes de réels joueurs de football.

L'utilisateur peut constituer une équipe de manière stratégique, pour la semaine suivante.

Cette fonctionnalité lui permettera de _peut être_ gagner une carte d'une certaine rareté suivant sa position dans le classement.

Sa position sera déterminée par la performance réelle des joueurs choisis pour son équipe de la semaine.


## Auteurs



* Thomas Peray : thomas.peray@etu.uni-nantes.fr
* Rémi Delord : remi.delord@etu.uni-nantes.fr


## Installation et lancement



* Dans IntelliJ



Avec une version récente d'IntelliJ (JDK version 17 ou +), il suffit de lancer l'application depuis la classe `Application.java`.

Appuyez sur la flèche verte à gauche de la ligne `public class Application...`, le build sera fait automatiquement.

Pour le prochain lancement, un appui sur le bouton Run en haut d'IntelliJ suffira.


## Utilisation



* En tant qu'admin
    * Connexion

Pour acquérir un compte admin, lancez d'abord l'application puis refermez la. Il faut ensuite modifier le fichier `src/main/resources/com/javafootball/data/utilisateurs.csv` en y ajoutant une ligne comportant le nom d'utilisateur admin et son mot de passe sous ce format précis : `adminPseudo;adminMotDePasse`.

La connexion poura alors se faire comme n'importe quel utilisateur par la suite, dans les champs prévus à cet effet dans la première fenêtre



    * Utilisation de la fenêtre

Le rôle d'admin permet de mettre en vente des cartes. Il peut aussi importer les fichiers des résultats recensant les matchs de la semaine prochaine et les fichiers de notes de joueurs.



        * Partie supérieure

Cette partie permet de mettre en vente des cartes, qui seront placées dans l'onglet boutique pour les Utilisateurs Joueurs.

Pour mettre en vente une carte, sélectionnez un joueur dans le tableau à gauche, choisissez la rareté de la carte puis son prix et enfin la quantité de carte que vous voulez mettre en vente avec ces propriétés.

Vous n'avez plus qu'à cliquer sur le bouton "Mettre en vente X carte(s)".



        * Partie inférieure

Cette partie permet réaliser les actions relatives à la gestion des semaines.

Vous pouvez, d'abord à gauche, importer deux types de fichiers : les matchs de la semaine et les résultats de la semaine.

Les matchs de la semaine sont des fichiers décrivant les matchs à venir dans la semaine, et seront affichés pour les joueurs. Il doivent être des fichiers comportant une ou plusieurs ligne, dont le format d’une ligne sera comme suit : Equipe 1 / Equipe 2

Les résultats de la semaine sont des fichiers décrivant la performance des joueurs d’une même équipe. Chaque ligne du fichier doit suivre ce patron : Equipe,Prénom Nom(s),Note. Un nombre décimal pour la note est accepté.

À droite, vous avez le bouton “Passer à la semaine suivante” qui permet d’évaluer chaque score de chaque équipe soumise par les utilisateurs, et de les récompenser par des cartes en conséquence. Le premier obtiendra une carte Rare d’un joueur aléatoire, le deuxième une Peu Commune et le troisième une Commune. Les suivants ne gagneront rien. \
**Vous ne pouvez passer à la semaine suivante seulement après avoir importé au moins un fichier de match et un fichier de résultat.**



* En tant qu’utilisateur joueur
    * Connexion

La connexion se fait dans les champs prévus. Si vous n’avez pas encore de compte, renseignez les champs avec un pseudo et un mot de passe voulu et cliquez sur “S’inscrire”. Votre compte sera alors créé. Cliquez alors sur “Se connecter” pour vous connecter à votre compte.



    * Utilisation de la fenêtre

À l’ouverture de la fenêtre, 4 onglets se présentent à vous.



        * Onglet “Mes cartes”

Dans cet onglet sont présentes les cartes que vous possédez. Après un clique sur l’une d’entre elles dans le tableau, un aperçu vous sera présenté dans la partie droite.

Vous pouvez choisir de vendre une carte en la sélectionnant dans le tableau, puis en saisissant un prix de vente, puis enfin en cliquant sur “Vendre”.



        * Onglet “Boutique”

Dans cet onglet sont présentes les offres de carte à vendre. Après un clique sur l’une d’entre elles dans le tableau, un aperçu vous sera présenté dans la partie droite.

Vous pouvez en acheter une en la sélectionnant dans le tableau, puis en cliquant sur “Acheter”. (attention à avoir assez de Zimdim Coins).



        * Onglet “Mon équipe”

Dans cet onglet, pour pouvez composer votre équipe de la semaine pour rentrer en compétition avec les autres joueurs, et peut être gagner des grosses récompenses.

L’équipe est composée de 4 cartes, dont exactement une carte dont le joueur évolue au poste de Gardien. Vous pouvez la construire en sélectionnant chaque carte dans les 4 listes déroulantes à gauche. Les cartes seront alors affichées dans présentation graphique à droite. À noter qu’il n’est pas possible de construire une équipe comportant deux fois le même joueur, même si ce sont deux cartes de raretés différentes.

Après avoir composé votre équipe, cliquez sur “Enregistrer équipe de la semaine” pour enregistrer votre composition.



        * Onglet “Match”

Cet onglet a seulement pour but d’afficher des informations relatives aux matchs et aux résultats de la semaine passée.

Vous pourrez y trouver les matchs qui se sont déroulés la semaine passée, ainsi que le TOP 3  des meilleurs utilisateurs avec leurs points cumulés à partir de leur équipe.

Dans la partie droite y sont recensé les matchs des équipes à venir. Nous vous conseillons donc d’établir une équipe constituée de joueurs de ces équipes ;)
