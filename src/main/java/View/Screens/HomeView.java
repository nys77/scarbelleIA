package View.Screens;

import Model.Dictionary.Dawg;
import Model.Enum.GameMode;
import Model.Model;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeView extends JFrame {

    private final Model model_;
    private final Dawg dawg_;

    public HomeView(Model model, Dawg dawg) {
        this.model_ = model;
        this.dawg_ = dawg;

        setTitle("ScarbelleIA - Menu Principal");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        Config.AssetsConfig.applyAppIcon(this);

        // Panneau principal avec dégradé sombre élégant
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 23, 42), 0, getHeight(), new Color(30, 41, 59));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(40, 50, 40, 50));

        // En-tête (Titre et Sous-titre)
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("SCARBELLE IA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 38));
        titleLabel.setForeground(new Color(248, 250, 252));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Jeu de Scrabble & Moteur d'IA Intelligente");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(148, 163, 184));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        headerPanel.add(subtitleLabel);

        // Zone centrale des boutons de mode de jeu
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        buttonsPanel.setLayout(new GridLayout(2, 1, 20, 20));
        buttonsPanel.setBorder(new EmptyBorder(40, 60, 40, 60));

        JButton pvpButton = createStyledButton("⚔️  Joueur vs Joueur (PvP)", new Color(79, 70, 229), new Color(99, 102, 241));
        pvpButton.addActionListener(e -> startGame(GameMode.PVP));

        JButton pveButton = createStyledButton("🤖  Joueur vs IA (PvE)", new Color(13, 148, 136), new Color(20, 184, 166));
        pveButton.addActionListener(e -> startGame(GameMode.PVE));

        buttonsPanel.add(pvpButton);
        buttonsPanel.add(pveButton);

        // Pied de page
        JLabel footerLabel = new JLabel("Propulsé par DAWG & Java 21");
        footerLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        footerLabel.setForeground(new Color(100, 116, 139));
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.CENTER);
        mainPanel.add(footerLabel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color baseColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(baseColor);
            }
        });

        return button;
    }

    private void startGame(GameMode mode) {
        this.dispose(); // Fermer la page d'accueil
        SwingUtilities.invokeLater(() -> new Init(model_, dawg_, mode));
    }
}
