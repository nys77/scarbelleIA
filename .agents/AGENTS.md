# Règles et Contexte de ScarbelleIA

## Contexte du projet
- **Nom du projet** : ScarbelleIA
- **Type de projet** : Jeu vidéo de société en Java.
- **Principe** : Il s'agit d'une implémentation du jeu de Scrabble.
- **Objectifs principaux** : 
  1. Permettre de jouer en mode Joueur contre Joueur (PvP), Joueur contre IA (PvE) et IA contre IA (EvE).
  2. Développer et intégrer une Intelligence Artificielle "maison" (Logique) capable de jouer contre un joueur humain ou de s'affronter en duel automatique (EvE).
- **Architecture** : Le projet suit un pattern Modèle-Vue.
  - La logique (plateau, dictionnaire DAWG, validations) est dans le package `Model`.
  - L'interface graphique (Swing) est dans le package `View`.

## Règles de documentation
- Toute la documentation doit être rédigée au format **Markdown**.
- Les schémas architecturaux, algorithmiques et les flux de données doivent être générés à l'aide de **Mermaid.js**.
- **Mise à Jour Systématique Obligatoire d'AGENTS.md & Doc** : Le fichier `.agents/AGENTS.md`, la documentation (dossier `docs/`) et le `README.md` DOIVENT IMPÉRATIVEMENT être mis à jour à CHAQUE GÉNÉRATION ou modification de code (voir les skills `doc-generator` et `cleaner`).
- Le style de la documentation doit être clair, concis, et utiliser des diagrammes chaque fois qu'un concept complexe (ex: DAWG, GameManager, Design Patterns) est abordé.

## Règles de développement et maintenance
- **Séparation des préoccupations** : Ne jamais mélanger la logique de la vue (Swing/AWT) et la logique du jeu (Modèle).
- **Évolution de l'IA** : L'IA ne doit pas tricher. Elle doit interagir avec l'état du jeu exactement comme un joueur humain, en consultant les tuiles disponibles et en respectant le lexique (via DAWG).
- **Initiative et Déclenchement IA (PvE)** : En mode PVE, l'IA Scarbelle commence toujours la partie et réagit immédiatement et automatiquement lors d'un "Passer le tour" sans bloquer le thread UI Swing.
- **Interdiction d'Écrasement & Mots Croisés** : Il est STRICTEMENT INTERDIT au moteur de jeu et à l'IA d'écraser des cases occupées par des lettres différentes. Tout coup posé doit impérativement valider TOUS les mots croisés perpendiculaires et extensions créés dans le dictionnaire DAWG (`validateCrossWordsAndExtensions`).
- **Persistance Matérielle du Plateau** : `Map.java` conserve durablement l'état des lettres posées dans `placedGrid`.
- **Nettoyage Systématique Obligatoire (Skill `cleaner`)** : À la fin de CHAQUE implémentation, vous devez IMPÉRATIVEMENT exécuter le skill `cleaner` pour supprimer le code commenté, les variables/fonctions inutilisées, les imports superflus, les dépendances et fichiers statiques non utilisés.
- Lors de l'ajout de nouvelles fonctionnalités, toujours s'assurer que les classes existantes sont documentées (voir le skill `doc-generator`).

## Bonnes Pratiques de Développement (Clean Code)
- **SOLID & DRY** : Respectez les principes SOLID et ne vous répétez pas (Don't Repeat Yourself).
- **Nommage Explicite et Fonctionnel** : Tout nom de variable, méthode, paramètre ou classe DOIT être ultra-explicite et révéler immédiatement son intention fonctionnelle. L'utilisation de noms génériques ou mono-lettres (tels que `a`, `b`, `t`, `res`, `tmp`, `ptest`, `cmp`) est strictly interdite. Utilisez des identifiants complets et descriptifs (ex: `targetCell`, `remainingLetterCount`, `playerRack`, `consecutivePassesCount`).
- **Nommage Majuscule des Fichiers** : Le nom de chaque fichier et classe Java DOIT impérativement commencer par une majuscule (PascalCase). Ex: `Main.java`, `Cell.java`, `Map.java`, `Combineur.java`, `Init.java`, `Model.java`.
- **Segmentation des Dossiers (Max 5 fichiers)** : Tout dossier contenant plus de 5 fichiers Java DOIT être re-segmenté en sous-dossiers thématiques (ex: `Model/Enum/` pour les énumérations, `Model/AI/` pour l'IA, `View/Components/` pour les composants visuels).
- **Fonctions Courtes** : Une méthode ne doit faire qu'une seule chose (Single Responsibility Principle). Si une méthode dépasse 20-30 lignes, envisagez de la scinder.
- **Éradication des Nombres Magiques** : Remplacez les chaînes de caractères codées en dur (ex: `"tests/ressources/dico.txt"`) et les nombres magiques (ex: `15` pour la taille du plateau) par des constantes globales (ex: `public static final int BOARD_SIZE = 15;`).
- **Gestion des Exceptions** : Capturez et traitez les exceptions correctement. Ne laissez pas de blocs `catch` vides.
- **Java Moderne** : Utilisez les fonctionnalités modernes de Java (ex: `Streams`, `Optional`, le mot-clé `final` pour l'immutabilité lorsque c'est pertinent) pour rendre le code plus sûr et plus lisible.
- **Variables d'Environnement & Configuration** : Ne stockez jamais de configuration sensible, de secrets ou de chemins absolus en dur. Utilisez des variables d'environnement (`System.getenv()`), un fichier de propriétés (`config.properties`), ou un `.env` (accompagné d'un `.env.example`) pour rendre le projet portable.

## Règles du jeu (Scarbelle)
- **Le Plateau** : Le jeu se déroule sur une grille de 15x15 cases.
- **Les Lettres** : Chaque joueur possède un chevalet de 7 lettres. À chaque tour, les lettres jouées sont remplacées en piochant aléatoirement dans le sac, jusqu'à épuisement.
- **Les Mots** : Les mots doivent se lire de gauche à droite ou de haut en bas. Tout nouveau mot formé doit s'accrocher à au moins une lettre déjà présente sur le plateau (sauf le premier mot qui doit recouvrir la case centrale).
- **Validation** : Tout mot posé (ainsi que les mots adjacents créés ou modifiés) doit exister dans le dictionnaire de référence (DAWG).
- **Les Points** : Le score d'un mot est la somme de la valeur de ses lettres. Il existe des cases "Multiplicateurs" sur le plateau : "Lettre Compte Double", "Lettre Compte Triple", "Mot Compte Double", "Mot Compte Triple". Le multiplicateur de mot s'applique au score total du mot (après avoir compté les multiplicateurs de lettres). Si un joueur pose ses 7 lettres en un seul tour, il obtient un bonus ("Scrabble").
- **Passer son tour** : Lors de son tour, un joueur peut choisir de ne poser aucun mot et de passer son tour à l'adversaire.
- **Échanger des lettres** : Si le sac contient encore au moins 7 lettres, un joueur (ou l'IA) peut choisir d'échanger de 1 à 7 lettres de sa main avec le sac. Conformément aux règles officielles, le joueur doit piocher ses nouvelles lettres dans le sac **AVANT** de remettre ses anciennes lettres défaussées dans le sac (afin d'éviter de repiocher immédiatement les mêmes jetons). Cela consomme son tour.
- **Fin de partie & Décompte final** : La partie s'arrête si aucun joueur ne peut plus poser de mot et que le sac contient moins de 7 lettres après 3 passes consécutives par joueur (ou si un joueur a vidé son chevalet et que le sac est vide). Les valeurs des lettres restant sur le chevalet de chaque joueur sont alors soustraites de leur score total.

### Matrice du Plateau (15x15)
L'IA et le jeu se basent sur la disposition classique du plateau de Scrabble. Voici la matrice exacte des cases spéciales :
- **MT** : Mot Triple
- **MD** : Mot Double
- **LT** : Lettre Triple
- **LD** : Lettre Double
- **\*** : Case Centrale (Mot Double)
- **_** : Case Normale

```text
Row 00 : MT,  _,  _, LD,  _,  _,  _, MT,  _,  _,  _, LD,  _,  _, MT
Row 01 :  _, MD,  _,  _,  _, LT,  _,  _,  _, LT,  _,  _,  _, MD,  _
Row 02 :  _,  _, MD,  _,  _,  _, LD,  _, LD,  _,  _,  _, MD,  _,  _
Row 03 : LD,  _,  _, MD,  _,  _,  _, LD,  _,  _,  _, MD,  _,  _, LD
Row 04 :  _,  _,  _,  _, MD,  _,  _,  _,  _,  _, MD,  _,  _,  _,  _
Row 05 :  _, LT,  _,  _,  _, LT,  _,  _,  _, LT,  _,  _,  _, LT,  _
Row 06 :  _,  _, LD,  _,  _,  _, LD,  _, LD,  _,  _,  _, LD,  _,  _
Row 07 : MT,  _,  _, LD,  _,  _,  _,  *,  _,  _,  _, LD,  _,  _, MT
Row 08 :  _,  _, LD,  _,  _,  _, LD,  _, LD,  _,  _,  _, LD,  _,  _
Row 09 :  _, LT,  _,  _,  _, LT,  _,  _,  _, LT,  _,  _,  _, LT,  _
Row 10 :  _,  _,  _,  _, MD,  _,  _,  _,  _,  _, MD,  _,  _,  _,  _
Row 11 : LD,  _,  _, MD,  _,  _,  _, LD,  _,  _,  _, MD,  _,  _, LD
Row 12 :  _,  _, MD,  _,  _,  _, LD,  _, LD,  _,  _,  _, MD,  _,  _
Row 13 :  _, MD,  _,  _,  _, LT,  _,  _,  _, LT,  _,  _,  _, MD,  _
Row 14 : MT,  _,  _, LD,  _,  _,  _, MT,  _,  _,  _, LD,  _,  _, MT
```
