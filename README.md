
![Logo](https://i.imgur.com/YzYLQq9.png)


# Zimdim Football - Projet Java

Jeu inspiré de SoRare, réalisé dans le cadre du cours de
"Programmation objet en Java" encadré par Julien Cohen à Polytech Nantes.

Le jeu consiste en une application Java permettant d'acheter et revendre des cartes de réels joueurs de football.
L'utilisateur peut constituer une équipe de manière stratégique, pour la semaine suivante.
Cette fonctionnalité lui permettera de _peut être_ gagner une carte d'une certaine rareté suivant sa position dans le classement.
Sa position sera déterminée par la performance réelle des joueurs choisis pour son équipe de la semaine.



## Auteurs


- Thomas Peray : thomas.peray@etu.uni-nantes.fr
- Rémi Delord : remi.delord@etu.uni-nantes.fr


## Installation et lancement

- Dans IntelliJ
  Avec une version récente d'IntelliJ(JDK version 17 ou +), il suffit de lancer l'application depuis la classe `Application.java`.
  Appuyez sur la flèche verte à gauche de la ligne `public class Application...`, le build sera fait automatiquement.
  Pour le prochain lancement, un appui sur le bouton Run en haut d'IntelliJ sera necessaire.





## Utilisation
- En tant qu'admin
  Le rôle d'admin permet de mettre en vente des cartes. Il peut aussi importer les fichiers des résultats recensant les matchs de la semaine prochaine et
  les fichiers de notes de joueurs.

Pour aquerir un compte admin, lancez d'abord l'application puis refermez la. Il faut ensuite modifier le fichier `src/main/resources/com/javafootball/data/utilisateurs.csv`
en y ajoutant une ligne comportant le nom d'utilisateur admin et son mot de passe sous ce format précis : `adminPseudo;adminMotDePasse`.
La connexion poura alors se fera comme n'importe quel utilisateur par la suite, dans les champs prévus à cet effet dans la première fenêtre.

