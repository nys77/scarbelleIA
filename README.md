# 🔤 ScarbelleIA - Jeu de Scrabble & Moteur d'IA en Java 21

![Java 21](https://img.shields.io/badge/Java-21.0.2-orange.svg)
![Maven](https://img.shields.io/badge/Maven-3.8+-blue.svg)
![JUnit 5](https://img.shields.io/badge/JUnit-5.10.0-green.svg)
![Architecture](https://img.shields.io/badge/Architecture-MVC-purple.svg)

**ScarbelleIA** est une implémentation complète et moderne du jeu de société **Scrabble** développée en Java 21. Le projet intègre une interface graphique Java Swing ainsi qu'un moteur d'**Intelligence Artificielle "maison"** parallélisé s'appuyant sur un dictionnaire optimisé sous forme de **DAWG** (*Directed Acyclic Word Graph*).

---

## 🚀 Fonctionnalités Principales

- **🎮 Modes de Jeu** :
  - **Joueur vs Joueur (PvP)** : Mode classique à deux joueurs sur la même machine.
  - **Joueur vs IA (PvE)** : Affrontez une IA intelligente. L'IA possède systématiquement l'initiative du premier coup et réagit automatiquement lors d'un "Passer le tour".
- **🤖 Intelligence Artificielle Équitable & Strictement Conforme** :
  - **Non-triche** : L'IA agit exclusivement à partir de ses 7 lettres et de la grille. Elle n'a aucun accès aux lettres du sac.
  - **Validation Anti-Écrasement & Mots Croisés** : Interdiction absolue d'écraser des tuiles occupées. Validation rigoureuse de tous les mots croisés et extensions produits dans le dictionnaire DAWG (`validateCrossWordsAndExtensions`).
  - **Équilibrage Voyelles / Consonnes** : Sélection automatique des lettres à échanger si aucun mot n'est jouable (ratio 3V/4C, délestage des consonnes lourdes `Q, W, X, Z, K, V`, protection des Jokers `!`).
  - **Cartographie Unifiée & Multi-Offsets (`Anchor(row, col)`)** : Cartographie explicite (row, col) sans inversion X/Y et recherche multi-offsets ($0$ à $L-1$) garantissant le jeu continu de l'IA sans blocage après plusieurs passes.
- **⚡ Structure DAWG (Dictionnaire)** : Validation et génération des mots en temps linéaire $O(L)$, insensible à la casse.
- **🎨 Interface Graphique (Swing)** :
  - Écran d'accueil interactif pour la sélection du mode.
  - Layout responsive sans rognage de la grille 15x15.
  - Tableau de bord latéral réactif (`ScoreboardPanel`) avec bouton **Rejouer la partie (`🔁 Rejouer`)**.
- **🏆 Décompte Exact des Points & Actions Scrabble** :
  - Actions **Passer son tour** et **Échanger des lettres** (avec sac $\ge 7$).
  - Fin de partie en cas de 3 passes consécutives avec déduction de la main.
  - Bonus Scrabble (+50 pts) lors du placement des 7 lettres en un seul tour.

---

## 🏗️ Architecture du Projet (MVC & Design Patterns)

Le projet suit le pattern **Modèle-Vue** (MVC) enrichi par 6 Design Patterns (Strategy, Singleton, Builder, Observer, Polymorphism) et une segmentation à 5 fichiers max par dossier :

```text
scarbelleIA/
├── .agents/                    # Agents et skills (ai-scrabble-helper, bug-tracker, etc.)
├── docs/                       # Documentation d'architecture (Mermaid)
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── Main.java       # Point d'entrée principal
   │   │   ├── Config/        # AssetsConfig (Centralisation des constantes)
│   │   │   ├── Model/         # Logique métier (Model, Move, MoveBuilder, GameManager)
│   │   │   │   ├── AI/        # ScrabbleAI, AIStrategy, ParallelDAWGStrategy
│   │   │   │   ├── Board/     # Cell, Map (placedGrid persistant)
│   │   │   │   ├── Dictionary/# Dawg, DictionarySingleton
│   │   │   │   ├── Enum/      # GameMode
│   │   │   │   └── Player/    # Player, HumanPlayer, AIPlayer
│   │   │   └── View/          # Interface Swing
│   │   │       ├── Components/# ScoreboardPanel, Panel, GameStateObserver
│   │   │       ├── Screens/   # HomeView, Init
│   │   │       └── Combineur  # Calculateur de mots
│   │   └── resources/         # Images des tuiles, lettres et dictionnaire (dico.txt)
│   └── test/
│       ├── java/              # 20 tests unitaires JUnit 5 (modelTest, dawgTest, viewTest)
│       └── resources/         # Fichiers textes de test
└── pom.xml                     # Configuration Maven & Dépendances
```

---

## 🛠️ Prérequis

- **Java Development Kit (JDK)** : Version **21.0.2** ou supérieure.
- **Apache Maven** : Version **3.8+**.

---

## 🔧 Installation & Compilation

1. **Cloner le dépôt** :
   ```bash
   git clone https://github.com/votre-compte/scarbelleIA.git
   cd scarbelleIA
   ```

2. **Compiler le projet** :
   ```bash
   mvn clean compile
   ```

3. **Exécuter les tests unitaires** :
   ```bash
   mvn test
   ```

4. **Lancer l'application** :
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

---

## 🧪 Tests Unitaires

Les 19 tests sont rédigés avec **JUnit 5 (Jupiter)** et couvrent :
- La création du graphe **DAWG** et la recherche de mots insensibles à la casse.
- La détection automatique des ancrages sur le plateau 15x15.
- La validation stricte de non-écrasement et le rejet des mots croisés invalides (`testInvalidCrossWordRejected`).
- L'évaluation des coups de l'IA (scores, multiplicateurs, bonus Scrabble).
- L'échange optimisé voyelles/consonnes et la parallélisation multi-threads.

Pour lancer les tests :
```bash
mvn test
```

---

## 📄 Documentation Complémentaire

La documentation détaillée avec les schémas **Mermaid.js** se trouve dans le dossier `docs/` :
- [`docs/architecture_globale.md`](docs/architecture_globale.md) : Diagramme des composants, flux MVC et règles de validation.
- [`docs/dawg_structure.md`](docs/dawg_structure.md) : Explication du dictionnaire sous forme de graphe orienté acyclique.

---

## 📜 Licence

Ce projet est sous licence open-source. Libre à vous de le réutiliser ou d'y contribuer !
