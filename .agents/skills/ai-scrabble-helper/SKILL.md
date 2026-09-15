---
name: ai-scrabble-helper
description: Guide to developing and evolving the ScarbelleIA Logic AI.
---

# Assistant IA ScarbelleIA

Lorsque vous travaillez sur l'IA de ScarbelleIA, suivez ces directives et règles de placement officielles :

## 1. Contexte & Intégrité de l'IA (Pas de Triche)
- L'IA ne doit **jamais** accéder au sac de lettres restants (`get_rand()` du Modèle) pour anticiper les tirages.
- L'IA ne doit **jamais** modifier la main du joueur adverse.
- Ses actions se traduisent par un `Move` : position `(startX, startY)`, orientation `isHorizontal` (true/false), mot et score.

## 2. Règles Strictes de Placement & Validation de Mots Croisés
- **Interdiction Absolue d'Écraser** : Si une case contient déjà une lettre $L_1$, il est **STRICTEMENT INTERDIT** d'y placer une lettre différente $L_2$.
- **Réutilisation de Tuiles** : Une case occupée par $L_1$ peut être réutilisée si le mot exige la même lettre $L_1$. Aucune tuile n'est prélevée de la main pour cette case.
- **Validation des Mots Croisés Perpendiculaires** : Toute nouvelle tuile posée à côté d'une case occupée crée un mot croisé perpendiculaire. **TOUS les mots croisés formés doivent impérativement exister dans le dictionnaire DAWG**.
- **Validation des Extensions** : Si le mot posé prolonge des lettres existantes (préfixe ou suffixe), le mot étendu complet doit impérativement être validé par le DAWG (ex: poser `FAQ` sous `E` forme `EFAQ`, qui doit être rejeté si invalide).

## 3. Persistance du Plateau & Synchronisation Vue/Modèle
- `Map.java` doit maintenir une matrice `placedGrid` permanente de toutes les lettres posées. `get_matrix()` doit retourner cette grille réelle à chaque tour.
- Les méthodes d'affichage (`placeMoveOnBoard`) doivent synchroniser simultanément la vue Swing (`grid_panel_`) et le modèle logique (`map_.setLetter`).

## 4. Stratégie & Équilibrage de la Main
- **Recherche par Ancrages** : Utiliser les cases d'ancrage (cases adjacentes aux lettres posées) et la parallélisation Java 21 (`parallelStream`).
- **Échange Optimisé Voyelles / Consonnes** : Si aucun mot n'est jouable et que le sac contient $\ge 7$ lettres, l'IA équilibre sa main selon le ratio idéal 3 Voyelles / 4 Consonnes (décharge des consonnes lourdes `Q, W, X, Z, K, V`, protection des jokers `!`).
- **Déclenchement Automatique** : Lors d'un "Passer le tour", le tour de l'IA s'exécute automatiquement via `SwingUtilities.invokeLater()` sans bloquer l'interface graphique.
