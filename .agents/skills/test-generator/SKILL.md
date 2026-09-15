---
name: test-generator
description: Générer ou mettre à jour les tests unitaires (JUnit) pour assurer la non-régression de ScarbelleIA.
---

# Générateur de Tests (Non-Régression)

Lorsque vous êtes invité à générer ou mettre à jour des tests, suivez ces directives :

## 1. Localisation
- Les tests doivent être placés dans l'arborescence `src/Test/Java/` (ou `src/test/java/` si restructuré).
- Conservez la même hiérarchie de packages que le code source (ex: `src/Test/Java/Model/DawgTest.java`).

## 2. Utilisation de JUnit
- Utilisez le framework JUnit 4 (ou la version définie dans le `pom.xml`).
- Chaque méthode de test doit tester une seule fonctionnalité ou un seul cas de figure (ex: `testMotValide()`, `testMotInvalide()`).

## 3. Mocking et Dépendances
- Ne testez pas la Vue (Interface Swing) via JUnit, cela est fragile. Concentrez-vous sur la logique métier (package `Model`).
- Si une classe dépend d'un dictionnaire externe, utilisez un mini-dictionnaire de test pour isoler la logique.

## 4. Test Unitaire de Partie Complète ("Full Game Simulation") avec Vraie Validation
- À chaque création ou mise à jour majeure du moteur de jeu, vous devez impérativement créer ou maintenir un test unitaire d'intégration d'une partie complète (ex: `RobustFullGameTest.java`).
- **Exigences du test de partie complète** :
  1. **Validation du chevalet à chaque tour** : `assertTrue(rack.size() <= 7)` pour garantir qu'aucune dérive de taille de main n'apparaît.
  2. **Validation des tuiles consommées** : Vérifier que `player.executeMove(move, map)` ne retire que les lettres utilisées depuis la main et n'écrase aucune case du plateau.
  3. **Respect de l'ordre d'échange des lettres** : La pioche des nouvelles lettres doit être effectuée **avant** de remettre les tuiles défaussées dans le sac.
  4. **Vérification des conditions de fin** : S'assurer que le jeu se termine correctement après 6 passes ou sac vide, et que les décomptes de score et pénalités sont valides.
