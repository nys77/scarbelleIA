---
name: swing-ui-enhancer
description: Amélioration du design Swing et génération d'images ultra-réalistes pour le plateau et les tuiles de Scrabble.
---

# Design & Amélioration Visuelle Swing pour ScarbelleIA

Lorsque vous êtes invité à améliorer le visuel de la grille, des cases ou du tableau de bord de ScarbelleIA, suivez ces directives d'excellence esthétique et de rendu UI Swing :

## 1. Rendu Visuel Réaliste de la Grille 15x15 (Board Aesthetics)
- **Texture Bois & Tuiles d'Érable** : Les lettres du chevalet et les tuiles posées sur le plateau doivent simuler des pièces en bois d'érable avec des bords biseautés (bevels), des ombres portées douces et la valeur du point affichée en indice au bas de chaque lettre.
- **Harmonie des Couleurs des Cases Spéciales** :
  - **Mot Triple (MT)** : Rouge bordeaux / carmin riche (`#991B1B`) avec typographie dorée/blanche.
  - **Mot Double (MD)** : Rose magenta profond / corail élégant (`#BE185D`).
  - **Lettre Compte Triple (LT)** : Bleu nuit / indigo profond (`#1E40AF`).
  - **Lettre Compte Double (LD)** : Bleu ciel / zinzolin doux (`#0284C7`).
  - **Case Centrale (\*)** : Étoile dorée sur fond Mot Double (`#D97706` / `#BE185D`).
  - **Case Normale (_)** : Vert feutrine de jeu de société (`#1E293B` ou vert feutrine profond `#14532D`).

## 2. Génération d'Assets Graphiques HD (via `generate_image`)
Lors de la création ou du remplacement des visuels dans `src/main/resources/` :
- Générer des images au format PNG haute résolution sans cadres de fenêtres ni bordures externes d'appareil.
- Centraliser les déclarations de chemins dans `Config/AssetsConfig.java`.
- Exemple de prompt pour `generate_image` :
  > "A realistic Scrabble wooden tile with letter 'A' engraved in bold black typography and small subscript number '1' in the bottom right corner, polished maple wood texture, subtle bevel border, clean white background, digital game asset."

## 3. Optimisation & Lissage Swing (Java 21 Graphics2D)
- Activer systématiquement l'antialiasing et le lissage du texte lors de la peinture custom dans Swing :
  ```java
  g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
  g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
  g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
  ```
- Utiliser des bordures arrondies douces `CompoundBorder(new LineBorder(..., true), new EmptyBorder(...))`.
- Éviter le rognage des conteneurs en utilisant un `BorderLayout` responsive dans `gridWrapper` et `GridLayout(15, 15)` avec marges adaptatives.
