---
name: structure-organizer
description: Réorganiser la structure des fichiers et des composants statiques du projet pour améliorer la lisibilité et l'architecture.
---

# Organisateur de Structure et Composants

Pour restructurer le projet au niveau fichier et organisation spatiale :

## 1. Arborescence Standard Maven & Organisation des Dossiers
- **Convention de Nommage Majuscule** : Le nom de tout fichier et classe Java DOIT commencer par une MAJUSCULE (PascalCase). Renommez les anciens `main.java`, `cell.java`, `map.java`, `init.java`, `combineur.java`, `model.java` en `Main.java`, `Cell.java`, `Map.java`, `Init.java`, `Combineur.java`, `Model.java`.
- **Segmentation des Dossiers (Règle des 5 Fichiers)** : Si un dossier contient plus de **5 fichiers Java**, il DOIT être re-segmenté en sous-dossiers pertinents :
  - **Les énumérations** du modèle doivent être placées dans `Model/Enum/` (ex: `Model/Enum/GameMode.java`).
  - **L'Intelligence Artificielle** doit être regroupée dans `Model/AI/` (ex: `ScrabbleAI.java`, `AIStrategy.java`, `ParallelDAWGStrategy.java`).
  - **Les joueurs** doivent être regroupés dans `Model/Player/` (ex: `Player.java`, `HumanPlayer.java`, `AIPlayer.java`).
  - **Les composants UI Swing** doivent être segmentés dans `View/Components/` (ex: `ScoreboardPanel.java`, `CellView.java`) et `View/Screens/` (`HomeView.java`, `InitView.java`).
- Alignez le projet sur l'arborescence standard Maven (ex: basculer `src/Test/Java` vers `src/test/java`).

## 2. Gestion des Fichiers Statiques (Images, Dico, txt)
C'est un point critique pour la maintenabilité et la portabilité (surtout lors de la création d'un `.jar` final) :
- **Emplacement** : Tous les fichiers statiques (images des lettres, tuiles du plateau, dictionnaire `dico.txt`, fichiers de configuration) DOIVENT être placés dans le dossier `src/main/resources/`. Organisez ce dossier par sous-dossiers (ex: `src/main/resources/images/lettres/`, `src/main/resources/dico/`).
- **Chargement** : N'utilisez JAMAIS de chemins absolus ou relatifs codés en dur via la classe `File` (ex: `new File("tests/ressources/dico.txt")`). Cela casse lors de l'export du jeu. Utilisez toujours le ClassLoader de Java :
  - Pour lire un texte : `getClass().getResourceAsStream("/dico/dico.txt")`
  - Pour charger une image Swing : `new ImageIcon(getClass().getResource("/images/lettres/A.png"))`
- **Centralisation** : Les chemins (même ceux du `getResource()`) doivent être centralisés dans une classe de configuration (ex: `AssetsConfig.java`) ou des énumérations, et non éparpillés dans `Main.java` ou `cell.java`.

## 3. Impact de la Restructuration
- Lors du déplacement des fichiers, veillez à toujours mettre à jour les chemins dans les autres classes Java et à convertir les anciens `File(...)` en `getResourceAsStream(...)`.
