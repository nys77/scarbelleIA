---
name: java-updater
description: Mettre à jour le projet vers Java 21.0.2 et adapter le code pour utiliser les nouvelles fonctionnalités.
---

# Assistant de Migration vers Java 21.0.2

Lorsque vous êtes invité à mettre à jour la version de Java ou à utiliser les fonctionnalités de Java 21, suivez ces directives :

## 1. Mise à jour du `pom.xml`
- Ajoutez ou mettez à jour les propriétés de compilation Maven pour cibler explicitement Java 21 :
  ```xml
  <properties>
      <maven.compiler.source>21</maven.compiler.source>
      <maven.compiler.target>21</maven.compiler.target>
  </properties>
  ```
- Assurez-vous que le plugin `maven-compiler-plugin` est suffisamment récent pour supporter Java 21.

## 2. Refactorisation vers Java 21
L'objectif n'est pas seulement de compiler en Java 21, mais de moderniser le code (Clean Code) en utilisant ses fonctionnalités :
- **Records** : Remplacez les petites classes de données par des `record`.
- **Pattern Matching (instanceof & switch)** : Utilisez le filtrage par motif pour simplifier les vérifications de types et les `switch` complexes.
- **Text Blocks** : Si vous manipulez des chaînes de caractères multilignes, utilisez les Text Blocks (`"""`).
- **Collections et Streams** : Profitez des nouvelles méthodes utilitaires (`toList()`, etc.).

## 3. Nettoyage des alertes de compilation
- Identifiez et résolvez tous les avertissements de dépréciation (par exemple l'instanciation de `Character(char)` via `new Character('A')` qui est obsolète depuis Java 9, remplacez-la par `Character.valueOf('A')`).
- Validez systématiquement la migration en exécutant `mvn clean compile test`.
