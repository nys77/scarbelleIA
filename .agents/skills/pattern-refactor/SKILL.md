---
name: pattern-refactor
description: Appliquer et intégrer les Design Patterns (Strategy, Observer, Visitor, Composite, Façade, Decorator, Adapter, Singleton, Prototype, Builder, Polymorphisme, Héritage) dans l'architecture Java de ScarbelleIA.
---

# Skill : Réfactorisation par Design Patterns (`pattern-refactor`)

Lorsque vous êtes invité à appliquer ou introduire des Design Patterns dans ScarbelleIA, suivez ce guide structuré pour garantir une architecture Java élégante, flexible et conforme aux principes SOLID.

---

## 1. Catalogue des Design Patterns pour ScarbelleIA

### 🔹 Patterns Créationnels

#### 1. Builder (Monte-charge)
- **Utilisation** : Construction pas-à-pas des objets complexes ou immutables.
- **Application dans le projet** : `MoveBuilder` pour assembler un coup (`startX`, `startY`, `isHorizontal`, `word`, `score`, `bonus`) ou `GameConfigBuilder`.
- **Exemple** :
  ```java
  Move move = new MoveBuilder()
      .atPosition(7, 7)
      .horizontally()
      .withWord("SCRABBLE")
      .build();
  ```

#### 2. Singleton
- **Utilisation** : Garantir une instance unique d'une ressource coûteuse.
- **Application dans le projet** : Chargement unique du dictionnaire DAWG ou gestionnaire de ressources `AssetsConfig`.

#### 3. Prototype
- **Utilisation** : Dupliquer un objet complexe sans se coupler à sa classe exacte.
- **Application dans le projet** : Cloner rapidement la matrice 15x15 du plateau (`board.clone()`) lors des simulations anticipées du moteur IA.

---

### 🔹 Patterns Structurels

#### 4. Composite
- **Utilisation** : Traiter des objets individuels et des compositions d'objets de manière uniforme.
- **Application dans le projet** : Composition de conteneurs UI (`PanelGroup`, `BoardView`) ou d'arborescences de mots.

#### 5. Façade
- **Utilisation** : Fournir une interface simplifiée et unifiée à un sous-système complexe.
- **Application dans le projet** : `GameEngineFacade` masquant la complexité combinée du DAWG, du sac de lettres, de la validation lexicographique et de l'IA.

#### 6. Décorateur (Decorator)
- **Utilisation** : Ajouter des responsabilités à un objet de façon dynamique sans altérer son code source.
- **Application dans le projet** : Appliquer des règles ou multiplicateurs temporaires sur le score d'une case (`TripleWordDecorator`, `DoubleLetterDecorator`).

#### 7. Adaptateur (Adapter)
- **Utilisation** : Convertir l'interface d'une classe en une autre interface attendue par le client.
- **Application dans le projet** : Adapter les événements graphiques Swing (`ActionListener`, `MouseListener`) vers les contrôleurs du package `Model`.

---

### 🔹 Patterns Comportementaux & POO

#### 8. Strategy
- **Utilisation** : Définir une famille d'algorithmes et les rendre interchangeables.
- **Application dans le projet** : `IAStrategy` (`RandomAIStrategy`, `HeuristicAIStrategy`, `ParallelDAWGStrategy`) pour faire varier le niveau de difficulté de l'IA.

#### 9. Observer
- **Utilisation** : Relation 1-à-N où les changements d'état du sujet notifient automatiquement les observateurs.
- **Application dans le projet** : Découplage strict MVC : Le Modèle (`GameManager`) notifie la Vue (`ScoreboardPanel`, `BoardView`) des mises à jour du score, du tour et du plateau.

#### 10. Visitor
- **Utilisation** : Séparer un algorithme de la structure d'objets sur laquelle il agit.
- **Application dans le projet** : Parcours de la grille 15x15 par un `BoardVisitor` (calcul des scores, recherche des ancrages, génération d'histogrammes).

#### 11. Héritage & Polymorphisme
- **Utilisation** : Concevoir des hiérarchies orientées objet propres avec substitution de Liskov.
- **Application dans le projet** : `Player` (classe abstraite) ➔ `HumanPlayer` et `AIPlayer`.

---

## 2. Démarche d'Application

1. **Identifier le besoin architectural** : Ne pas survendre de pattern inutile ("Over-engineering"). Appliquez un pattern seulement s'il simplifie le code ou répond à une évolution.
2. **Garantir la non-régression** : Exécuter systématiquement la suite de tests unitaires Maven (`mvn test`) après chaque refactorisation par pattern.
3. **Mettre à jour les schémas Mermaid** dans `docs/` pour refléter la nouvelle structure de classes (ex: interface `Strategy`, classes `ConcreteStrategy`).
