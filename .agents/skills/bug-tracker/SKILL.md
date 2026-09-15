---
name: bug-tracker
description: Isoler et résoudre les bugs de ScarbelleIA en séparant strictement l'analyse Vue (Swing) et Modèle (Logique/DAWG).
---

# Assistant de Débogage MVC

Lorsque l'utilisateur vous demande de corriger un bug, appliquez cette méthode systématique :

## 1. Isolation du problème
Identifiez si le bug est purement visuel/interface ou algorithmique :
- **Problème Visuel** (ex: tuile manquante, plateau décalé) : Cherchez dans `Main.java`, `panel.java`, `cell.java` ou vérifiez la validité des chemins d'accès aux images dans `tests/ressources/`.
- **Problème Logique** (ex: points mal calculés, mot valide refusé) : Cherchez dans `GameManager.java`, `Dawg.java` ou `model.java`.

## 2. Validation par les Logs / Tests
- Avant de proposer un correctif, demandez ou lisez les logs (exceptions Java).
- Écrivez (ou suggérez) un petit script de test ou un test unitaire qui reproduit le bug.

## 3. Résolution sans régression
- Lorsque vous proposez la correction, expliquez clairement pourquoi le bug se produisait et en quoi la modification le corrige, sans introduire d'effets de bord.
