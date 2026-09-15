---
name: cleaner
description: Nettoyage systématique du projet à la fin de chaque implémentation (fichiers statiques et images inutiles, code commenté, variables et méthodes inutilisées, dépendances pom.xml inutilisées).
---

# Skill de Nettoyage Systématique (Cleaner)

Ce skill **DOIT ÊTRE EXÉCUTÉ À LA FIN DE CHAQUE IMPLÉMENTATION** pour maintenir un dépôt propre, léger et sans dette technique.

## 1. Nettoyage des Fichiers Statiques et Images Inutiles
- Parcourir les ressources dans `src/main/resources/` et `src/test/resources/`.
- Supprimer les images, icônes ou fichiers statiques temporaires qui ne sont plus référencés dans `AssetsConfig.java` ou dans le code Java.
- Supprimer les fichiers temporaires de build ou de scratch inutilisés.

## 2. Éradication du Code Commenté
- Supprimer les blocs de code désactivés ou mis en commentaire (ex: `// setPanel(...)`, `// player1 = new JPanel(...)`).
- Nettoyer les commentaires obsolètes ou superflus qui ne fournissent aucune valeur fonctionnelle.
- Conserver uniquement les Javadoc utiles et les explications d'algorithmes complexes.

## 3. Suppression des Variables, Méthodes et Imports Inutilisés (Dead Code)
- **Variables & Champs Inutilisés** : Détecter et supprimer les variables locales non lues et les membres/champs de classes qui ne sont jamais accédés.
- **Fonctions / Méthodes Morte** : Éliminer les méthodes privées ou internes qui ne sont plus appelées nulle part dans le projet.
- **Imports inutilisés** : Nettoyer les déclarations `import` superflues en haut de chaque classe Java.

## 4. Nettoyage du `pom.xml` et des Dépendances
- Vérifier le fichier `pom.xml` pour détecter et supprimer toute dépendance Maven non utilisée par le projet.
- S'assurer que seules les dépendances actives (JUnit 5, etc.) sont conservées.

## 5. Validation Post-Nettoyage
- Exécuter la commande `mvn clean compile test` pour s'assurer que le nettoyage n'a rien brisé et que le projet compile et passe l'ensemble de ses tests unitaires avec succès.
