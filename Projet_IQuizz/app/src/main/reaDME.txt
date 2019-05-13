==============================
Mode d'emploi de l'application 
==============================

L'application en elle-même a été conçue pour être la plus intuitive et simple à utiliser que possible. 
- Pour se connecter, il faut s'être créé un compte. 
- Pour lancer un Quizz, il faut se connecter puis cliquer sur "jouer". 
- Si on veut jouer à plusieurs, alors il faut cliquer sur "Defi", choisir ses amise à défier et puis choisir le Quizz. 
-- Si on veut jouer en même temps, il faudra que les différents amis défiés se connectent avant de lancer le Quizz. 
- Pour changer ses informations, il faut aller dans les paramètres du compte (engrenage en haut à droite). 
- Pour Ajouter ses amis ou voir ses statistiques, il faut cliquer sur le bouton correspondant se trouvant dans la page de comptes. 

====================
Structure du code
====================

- assets/   : Repertoire contenant les différents fichiers de requetes SQL ainsi que les fichiers de quizz à créer. 
- java/     : Répertoire contenant les fichiers de code. 
--  java/controller : Répertoire contenant toutes les activités chargées de l'affichage des données ainsi que de la gestion de l'interaction avec les boutons. 
--  java/model  : Répertoire contenant les diférentes classes principales (Quizz, Question, ...) ainsi que la classe BDD chargée de la gestion des données. 
--  java/vue    : Répertoire vide
- res/  : Répertoire contenant tous les fichiers XML servant à la disposition des éléments graphiques. 