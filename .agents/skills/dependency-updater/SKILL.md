---
name: dependency-updater
description: Mettre à jour les dépendances Maven (pom.xml) et adapter le code Java source pour maintenir la compatibilité.
---

# Gestionnaire de Dépendances

Lorsqu'il faut mettre à jour des frameworks ou ajouter de nouvelles librairies :

## 1. Mise à jour de `pom.xml`
- Vérifiez la compatibilité des versions (ex: passage à JUnit 5 depuis JUnit 4, ajout d'outils de log comme SLF4J, ou de frameworks plus récents).
- Assurez-vous que le `<scope>` des dépendances est correct (ex: `test` pour JUnit).

## 2. Migration du code source
- Si la mise à jour introduit des "breaking changes" (changements majeurs), vous devez inspecter et modifier le code source pour l'adapter.
- Par exemple, un passage à JUnit 5 nécessite de changer les imports `@Test` de `org.junit.Test` vers `org.junit.jupiter.api.Test` et d'adapter les assertions.

## 3. Vérification de la compilation
- Après chaque mise à jour, assurez-vous que le projet compile (ex: via la commande `mvn clean install` ou équivalent).
