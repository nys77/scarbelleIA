package View.Components;

import Model.AI.ScrabbleAI;
import Model.Board.Map;
import Model.Dictionary.Dawg;
import Model.Move;
import Model.Player.Player;
import View.Combineur;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HumanMoveDialog extends JDialog {

    private final Player player_;
    private final Map map_;
    private final Dawg dawg_;

    private final JTextField wordField;
    private final JComboBox<Integer> rowCombo;
    private final JComboBox<String> colCombo;
    private final JRadioButton horizRadio;
    private final JRadioButton vertRadio;
    private final JLabel feedbackLabel;
    private final JButton confirmButton;

    private Move validatedMove = null;

    public HumanMoveDialog(JFrame parent, Player player, Map map, Dawg dawg) {
        super(parent, "ScarbelleIA - Placer un Mot", true);
        this.player_ = player;
        this.map_ = map;
        this.dawg_ = dawg;

        setSize(560, 640);
        setLocationRelativeTo(parent);
        setResizable(true);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(new Color(15, 23, 42));
        mainPanel.setBorder(new EmptyBorder(20, 25, 20, 25));

        // En-tête
        JLabel headerLabel = new JLabel("🎯 VOTRE TOUR DE JEU", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        headerLabel.setForeground(new Color(251, 191, 36));
        headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Affichage de la main
        JPanel rackPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 6, 6));
        rackPanel.setOpaque(false);
        rackPanel.setBorder(BorderFactory.createTitledBorder(
                new LineBorder(new Color(51, 65, 85), 1, true),
                "Votre Chevalet Actuel", 0, 0,
                new Font("Segoe UI", Font.BOLD, 12), new Color(148, 163, 184)
        ));

        if (player.getMain() != null && player.getMain().charac_main != null) {
            for (Character c : player.getMain().charac_main) {
                JPanel tile = createTileView(c);
                rackPanel.add(tile);
            }
        }

        // Formulaire de Saisie
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setOpaque(false);
        formPanel.setBorder(new EmptyBorder(10, 0, 10, 0));

        JLabel wordLabel = new JLabel("Mot à placer :");
        wordLabel.setForeground(new Color(226, 232, 240));
        wordLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        wordField = new JTextField();
        wordField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        wordField.setBackground(new Color(30, 41, 59));
        wordField.setForeground(Color.WHITE);
        wordField.setCaretColor(Color.WHITE);

        JLabel rowLabel = new JLabel("Ligne de départ (0 - 14) :");
        rowLabel.setForeground(new Color(226, 232, 240));
        rowLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        Integer[] rows = new Integer[15];
        for (int i = 0; i < 15; i++) rows[i] = i;
        rowCombo = new JComboBox<>(rows);
        rowCombo.setSelectedItem(7);

        JLabel colLabel = new JLabel("Colonne de départ (0 - 14 / A - O) :");
        colLabel.setForeground(new Color(226, 232, 240));
        colLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        String[] cols = new String[15];
        for (int i = 0; i < 15; i++) cols[i] = i + " (" + (char) ('A' + i) + ")";
        colCombo = new JComboBox<>(cols);
        colCombo.setSelectedIndex(7);

        JLabel dirLabel = new JLabel("Orientation :");
        dirLabel.setForeground(new Color(226, 232, 240));
        dirLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));

        horizRadio = new JRadioButton("Horizontale ➔", true);
        vertRadio = new JRadioButton("Verticale ⬇️");
        horizRadio.setOpaque(false);
        vertRadio.setOpaque(false);
        horizRadio.setForeground(Color.WHITE);
        vertRadio.setForeground(Color.WHITE);

        ButtonGroup dirGroup = new ButtonGroup();
        dirGroup.add(horizRadio);
        dirGroup.add(vertRadio);

        JPanel dirPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        dirPanel.setOpaque(false);
        dirPanel.add(horizRadio);
        dirPanel.add(Box.createRigidArea(new Dimension(15, 0)));
        dirPanel.add(vertRadio);

        formPanel.add(wordLabel);
        formPanel.add(wordField);
        formPanel.add(rowLabel);
        formPanel.add(rowCombo);
        formPanel.add(colLabel);
        formPanel.add(colCombo);
        formPanel.add(dirLabel);
        formPanel.add(dirPanel);

        // Feedback Label
        feedbackLabel = new JLabel("Entrez un mot pour prévisualiser le coup", SwingConstants.CENTER);
        feedbackLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        feedbackLabel.setForeground(new Color(148, 163, 184));
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Boutons de validation / annulation (Annuler à gauche, Valider à droite)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setOpaque(false);

        JButton cancelButton = new JButton("❌ Annuler");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cancelButton.setBackground(new Color(239, 68, 68));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setOpaque(true);
        cancelButton.setBorderPainted(false);

        confirmButton = new JButton("✅ Valider le coup");
        confirmButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        confirmButton.setBackground(new Color(16, 185, 129));
        confirmButton.setForeground(Color.BLACK);
        confirmButton.setOpaque(true);
        confirmButton.setBorderPainted(false);
        confirmButton.setEnabled(false);

        buttonPanel.add(cancelButton);
        buttonPanel.add(confirmButton);

        // Écouteurs d'événements
        wordField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { validateMoveLive(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { validateMoveLive(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { validateMoveLive(); }
        });

        rowCombo.addActionListener(e -> validateMoveLive());
        colCombo.addActionListener(e -> validateMoveLive());
        horizRadio.addActionListener(e -> validateMoveLive());
        vertRadio.addActionListener(e -> validateMoveLive());

        confirmButton.addActionListener(e -> {
            if (validatedMove != null) {
                dispose();
            }
        });

        cancelButton.addActionListener(e -> {
            validatedMove = null;
            dispose();
        });

        mainPanel.add(headerLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(rackPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(feedbackLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(buttonPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.getViewport().setBackground(new Color(15, 23, 42));

        add(scrollPane);
    }

    private void validateMoveLive() {
        String inputWord = wordField.getText().trim().toUpperCase();
        if (inputWord.isEmpty()) {
            feedbackLabel.setText("Entrez un mot pour prévisualiser le coup");
            feedbackLabel.setForeground(new Color(148, 163, 184));
            confirmButton.setEnabled(false);
            validatedMove = null;
            return;
        }

        int row = (int) rowCombo.getSelectedItem();
        int col = colCombo.getSelectedIndex();
        boolean isHorizontal = horizRadio.isSelected();

        // 1. Vérifier si le mot existe dans le dictionnaire DAWG
        if (dawg_ != null && !dawg_.word_existe(dawg_, inputWord, 0)) {
            feedbackLabel.setText("❌ Mot '" + inputWord + "' non trouvé dans le dictionnaire ODS");
            feedbackLabel.setForeground(new Color(248, 113, 113));
            confirmButton.setEnabled(false);
            validatedMove = null;
            return;
        }

        // 2. Vérifier la légalité du placement sur la grille
        List<Character> rack = player_.getMain() != null ? player_.getMain().charac_main : new ArrayList<>();
        boolean validPlacement = ScrabbleAI.isValidPlacement(map_.get_matrix(), row, col, isHorizontal, inputWord, rack, dawg_);

        if (!validPlacement) {
            feedbackLabel.setText("❌ Placement invalide (non rattaché, cases occupées ou lettres manquantes)");
            feedbackLabel.setForeground(new Color(248, 113, 113));
            confirmButton.setEnabled(false);
            validatedMove = null;
            return;
        }

        // 3. Calculer le score estimé
        int score = ScrabbleAI.calculateScore(map_.get_matrix(), row, col, isHorizontal, inputWord, rack.size());
        validatedMove = new Move(row, col, isHorizontal, inputWord, score);

        feedbackLabel.setText("✅ Mot valide ! Score estimé : +" + score + " points");
        feedbackLabel.setForeground(new Color(52, 211, 153));
        confirmButton.setEnabled(true);
    }

    private JPanel createTileView(Character c) {
        JPanel tile = new JPanel(new BorderLayout());
        tile.setPreferredSize(new Dimension(36, 42));
        tile.setBackground(new Color(254, 243, 199));
        tile.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(217, 119, 6), 2, true),
                BorderFactory.createEmptyBorder(1, 2, 1, 2)
        ));

        char letterChar = Character.toUpperCase(c);
        JLabel letterLabel = new JLabel(String.valueOf(letterChar), SwingConstants.CENTER);
        letterLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        letterLabel.setForeground(new Color(30, 41, 59));

        int val = Combineur.convert_cost(letterChar);
        JLabel valLabel = new JLabel(val > 0 ? String.valueOf(val) : "", SwingConstants.RIGHT);
        valLabel.setFont(new Font("Segoe UI", Font.BOLD, 9));
        valLabel.setForeground(new Color(120, 53, 15));

        tile.add(letterLabel, BorderLayout.CENTER);
        tile.add(valLabel, BorderLayout.SOUTH);
        return tile;
    }

    public Move getValidatedMove() {
        return validatedMove;
    }
}
