package View.Components;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class ScoreboardPanel extends JPanel implements GameStateObserver {

    private final JLabel player1ScoreLabel;
    private final JLabel player2ScoreLabel;
    private final JLabel remainingTilesLabel;
    private final JLabel turnLabel;
    private final JButton passButton;
    private final JButton exchangeButton;
    private final JButton replayButton;

    public ScoreboardPanel(String player1Name, String player2Name, int initialTiles) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(15, 23, 42));
        setBorder(new CompoundBorder(
                new LineBorder(new Color(51, 65, 85), 2, true),
                new EmptyBorder(20, 15, 20, 15)
        ));
        setPreferredSize(new Dimension(240, 600));

        // En-tête
        JLabel titleLabel = new JLabel("TABLEAU DE BORD", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(new Color(251, 191, 36));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Score Badges
        player1ScoreLabel = createScoreBadge(player1Name, "0", new Color(79, 70, 229));
        player2ScoreLabel = createScoreBadge(player2Name, "0", new Color(13, 148, 136));

        // Tour Label
        turnLabel = new JLabel("Tour : " + player1Name, SwingConstants.CENTER);
        turnLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        turnLabel.setForeground(new Color(226, 232, 240));
        turnLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Sac de Lettres Label
        remainingTilesLabel = new JLabel("🧺 Sac : " + initialTiles + " lettres", SwingConstants.CENTER);
        remainingTilesLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        remainingTilesLabel.setForeground(new Color(248, 250, 252));
        remainingTilesLabel.setOpaque(true);
        remainingTilesLabel.setBackground(new Color(51, 65, 85));
        remainingTilesLabel.setBorder(new EmptyBorder(8, 12, 8, 12));
        remainingTilesLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        remainingTilesLabel.setMaximumSize(new Dimension(200, 35));

        // Action Buttons
        passButton = createActionButton("⏭️ Passer le tour", new Color(100, 116, 139));
        exchangeButton = createActionButton("🔄 Échanger lettres", new Color(217, 119, 6));
        replayButton = createActionButton("🔁 Rejouer la partie", new Color(16, 185, 129));

        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(player1ScoreLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(player2ScoreLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(turnLabel);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(remainingTilesLabel);
        add(Box.createRigidArea(new Dimension(0, 20)));
        add(passButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(exchangeButton);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(replayButton);
        add(Box.createVerticalGlue());

        updateRemainingTiles(initialTiles);
    }

    private JLabel createScoreBadge(String name, String score, Color color) {
        JLabel label = new JLabel(name + " : " + score + " pts", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(Color.WHITE);
        label.setOpaque(true);
        label.setBackground(color);
        label.setBorder(new EmptyBorder(8, 12, 8, 12));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        label.setMaximumSize(new Dimension(200, 38));
        return label;
    }

    private JButton createActionButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(200, 38));
        btn.setPreferredSize(new Dimension(200, 38));
        return btn;
    }

    public void setPassAction(ActionListener listener) {
        passButton.addActionListener(listener);
    }

    public void setExchangeAction(ActionListener listener) {
        exchangeButton.addActionListener(listener);
    }

    public void setReplayAction(ActionListener listener) {
        replayButton.addActionListener(listener);
    }

    public void updatePlayer1Score(String name, int score) {
        player1ScoreLabel.setText(name + " : " + score + " pts");
    }

    public void updatePlayer2Score(String name, int score) {
        player2ScoreLabel.setText(name + " : " + score + " pts");
    }

    public void updateRemainingTiles(int count) {
        remainingTilesLabel.setText("🧺 Sac : " + count + " lettres");
        exchangeButton.setEnabled(count >= 7);
        if (count < 7) {
            exchangeButton.setToolTipText("L'échange nécessite au moins 7 lettres dans le sac");
        } else {
            exchangeButton.setToolTipText("Échanger entre 1 et 7 lettres avec le sac");
        }
    }

    public void updateTurn(String turnText) {
        turnLabel.setText(turnText);
    }

    @Override
    public void onScoreUpdated(int player1Score, int player2Score) {
        updatePlayer1Score("Joueur 1", player1Score);
        updatePlayer2Score("Joueur 2", player2Score);
    }

    @Override
    public void onRemainingTilesChanged(int remainingCount) {
        updateRemainingTiles(remainingCount);
    }

    @Override
    public void onTurnChanged(String activePlayerName) {
        updateTurn("Tour : " + activePlayerName);
    }
}
