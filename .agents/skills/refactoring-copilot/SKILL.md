---
name: refactoring-copilot
description: Optimiser les algorithmes complexes et nettoyer le code tout en respectant strictement l'architecture MVC.
---

# Copilote de Refactoring et Optimisation

Pour garder le code propre et performant, suivez ces règles lors des refactorisations algorithmiques (hors structure/fichiers) :

## 1. Respect de l'architecture MVC
- Aucun objet Swing (`JPanel`, `JFrame`, etc.) ne doit être présent ou importé dans le package `Model`.
- Le Modèle ne doit pas avoir conscience de la Vue. La communication doit se faire via des retours de fonctions ou des observateurs/contrôleurs.

## 2. Optimisation des algorithmes
- Identifiez les calculs lourds (ex: force brute pour les combinaisons, factorielles) et remplacez-les par des approches optimisées (élagage, utilisation du DAWG, programmation dynamique).
- Ne sacrifiez pas la lisibilité pour une micro-optimisation.

## 3. Clean Code
- Supprimez les imports inutilisés, les variables mortes et le code commenté obsolète.
- Nommez les variables de manière descriptive (évitez les `tmp`, `a`, `b`).
