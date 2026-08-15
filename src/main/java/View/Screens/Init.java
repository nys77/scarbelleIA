package View.Screens;

import Model.AI.*;
import Model.Board.*;
import Model.Dictionary.Dawg;
import Model.Enum.GameMode;
import Model.Letter;
import Model.Model;
import Model.Move;
import Model.Player.*;
import View.Components.ScoreboardPanel;
import View.Combineur;
import View.Main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Init {

    Map map_;
    Player player1;
    Player player2;
    JPanel[][] grid_panel_;
    Dawg graph_;
    GameMode gameMode_;
    Model model_;

    public Init(Model model, Dawg graph) {
        this(model, graph, GameMode.PVP);
    }

    public Init(Model model, Dawg graph, GameMode gameMode) {
        this.gameMode_ = gameMode;
        this.model_ = model;
        map_ = new Map();
        graph_ = graph;
        map_.print_grid();
        JFrame t = new JFrame("ScarbelleIA - Plateau de Jeu");
        t.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        t.setLayout(new BorderLayout(15, 15));
        Config.AssetsConfig.applyAppIcon(t);
        grid_panel_ = new JPanel[15][15];
        t.setSize(new Dimension(1150, 880));
        t.setLocationRelativeTo(null);

        // Panneau latéral de score à DROITE (EAST)
        String p2Name = (gameMode == GameMode.PVE) ? "IA Scarbelle" : "Joueur 2";
        ScoreboardPanel scoreboard = new ScoreboardPanel("Joueur 1", p2Name, model.get_rand().size());
        
        JPanel eastWrapper = new JPanel(new BorderLayout());
        eastWrapper.setOpaque(false);
        eastWrapper.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 20));
        eastWrapper.add(scoreboard, BorderLayout.CENTER);
        t.add(eastWrapper, BorderLayout.EAST);

        // Grille du plateau centrée au MILIEU (CENTER) sans clipping
        JPanel gridWrapper = new JPanel(new BorderLayout());
        gridWrapper.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 10));
        gridWrapper.setBackground(new Color(15, 23, 42));

        JPanel pan = new JPanel(new GridLayout(15, 15, 1, 1));
        Border gridBorder = BorderFactory.createLineBorder(new Color(51, 65, 85), 1);
        Color boardBg = new Color(30, 41, 59);

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                Cell c = map_.list_cell[i][j];
                JPanel ptest = createStyledCellPanel(c, i, j);
                pan.add(ptest);
                grid_panel_[i][j] = ptest;
            }
        }
        pan.setBackground(boardBg);
        gridWrapper.add(pan, BorderLayout.CENTER);
        t.add(gridWrapper, BorderLayout.CENTER);

        player1 = new HumanPlayer(t, 1, model, "Joueur 1");
        if (gameMode == GameMode.PVE) {
            player2 = new AIPlayer(t, 2, model, "IA Scarbelle", new ParallelDAWGStrategy());
        } else {
            player2 = new HumanPlayer(t, 2, model, "Joueur 2");
        }
        player1.getMain().print_main();
        player2.getMain().print_main();

        // Connexion des boutons d'action du tableau de bord
        scoreboard.setPassAction(e -> handlePassTurn(scoreboard, model));
        scoreboard.setExchangeAction(e -> handleExchangeLetters(scoreboard, model));
        scoreboard.setReplayAction(e -> handleReplayGame(t));

        t.setMinimumSize(new Dimension(1150, 880));
        t.setVisible(true);

        if (gameMode == GameMode.PVE) {
            // L'IA commence toujours à jouer le premier mot en mode PVE
            String bestOption = Combineur.best_solution(conv(player2.getMain().charac_main), graph_);
            System.out.println("IA Premier Mot : " + bestOption);
            if (bestOption != null && !bestOption.isEmpty()) {
                String word = bestOption.toUpperCase();
                int score = Combineur.valuer_of_string(word);
                Move firstMove = new Move(7, 7, false, word, score);
                player2.executeMove(firstMove, map_);
                placeMoveOnBoard(7, 7, false, word);
                scoreboard.updatePlayer2Score("IA Scarbelle", player2.getPoint());
                player2.getMain().print_main();
            }
            scoreboard.updateTurn("Tour : Joueur 1");
            scoreboard.updateRemainingTiles(model.get_rand().size());
        } else {
            // Mode PvP : Joueur 1 pose le premier mot
            String bestOption = Combineur.best_solution(conv(player1.getMain().charac_main), graph_);
            System.out.println("Joueur 1 Premier Mot : " + bestOption);
            if (bestOption != null && !bestOption.isEmpty()) {
                String word = bestOption.toUpperCase();
                int score = Combineur.valuer_of_string(word);
                Move firstMove = new Move(7, 7, false, word, score);
                player1.executeMove(firstMove, map_);
                placeMoveOnBoard(7, 7, false, word);
                scoreboard.updatePlayer1Score("Joueur 1", player1.getPoint());
                player1.getMain().print_main();
            }
            scoreboard.updateTurn("Tour : Joueur 2");
            scoreboard.updateRemainingTiles(model.get_rand().size());
        }
    }

    private int consecutivePasses = 0;

    private void handlePassTurn(ScoreboardPanel scoreboard, Model model) {
        if (!player1.canPlay()) {
            System.out.println("Action ignorée : ce n'est pas le tour du Joueur 1.");
            return;
        }

        consecutivePasses++;
        int bagCount = model.get_rand().size();
        scoreboard.updateRemainingTiles(bagCount);

        if (consecutivePasses >= 6 || (consecutivePasses >= 3 && bagCount < 7)) {
            triggerEndGame("3 passes consécutives avec moins de 7 lettres dans le sac.");
            return;
        }

        switchTurn(scoreboard);
    }

    private void handleExchangeLetters(ScoreboardPanel scoreboard, Model model) {
        if (!player1.canPlay()) {
            System.out.println("Action ignorée : ce n'est pas le tour du Joueur 1.");
            return;
        }

        if (model.get_rand().size() < 7) {
            JOptionPane.showMessageDialog(null, "L'échange nécessite au moins 7 lettres dans le sac.", "Action Impossible", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Player activePlayer = player1.canPlay() ? player1 : player2;
        String currentRack = activePlayer.getMain().charac_main.toString();

        String input = JOptionPane.showInputDialog(null,
                "Entrez les lettres à échanger depuis votre main " + currentRack + " (ex: A E) :",
                "Échanger des lettres", JOptionPane.QUESTION_MESSAGE);

        if (input != null && !input.trim().isEmpty()) {
            java.util.List<String> toExchange = convert_to_array(input.trim().toUpperCase());
            activePlayer.getMain().remove_caracter(new ArrayList<>(toExchange));

            // Pioche d'abord les nouvelles lettres dans le sac
            activePlayer.getMain().tirage(toExchange.size());

            // Remet ensuite les anciennes lettres défaussées dans le sac
            for (String s : toExchange) {
                if (!s.isEmpty()) {
                    model.get_rand().add(s.charAt(0));
                }
            }

            consecutivePasses++;
            scoreboard.updateRemainingTiles(model.get_rand().size());
            switchTurn(scoreboard);
        }
    }

    private void switchTurn(ScoreboardPanel scoreboard) {
        boolean p1Turn = player1.canPlay();
        player1.setCanPlay(!p1Turn);
        player2.setCanPlay(p1Turn);

        String activeName = !p1Turn ? "Joueur 1" : (gameMode_ == GameMode.PVE ? "IA Scarbelle" : "Joueur 2");
        scoreboard.updateTurn("Tour : " + activeName);

        if (gameMode_ == GameMode.PVE && player2.canPlay()) {
            SwingUtilities.invokeLater(() -> executeAITurn(scoreboard));
        }
    }

    private void executeAITurn(ScoreboardPanel scoreboard) {
        if (gameMode_ != GameMode.PVE || !player2.canPlay()) return;

        Move aiMove = player2.playTurn(map_.get_matrix(), graph_);
        if (aiMove != null && aiMove.score() > 0 && aiMove.word() != null && !aiMove.word().isEmpty()) {
            player2.executeMove(aiMove, map_);
            placeMoveOnBoard(aiMove.startX(), aiMove.startY(), aiMove.isHorizontal(), aiMove.word());
            scoreboard.updatePlayer2Score("IA Scarbelle", player2.getPoint());
            consecutivePasses = 0;
            System.out.println("IA Scarbelle a joué : " + aiMove.word() + " (+" + aiMove.score() + " pts)");
        } else if (model_.get_rand().size() >= 7 && player2 instanceof AIPlayer aiPlayer) {
            List<Character> toExchange = aiPlayer.chooseLettersToExchange();
            if (toExchange != null && !toExchange.isEmpty()) {
                ArrayList<String> toExStr = new ArrayList<>();
                for (Character c : toExchange) {
                    toExStr.add(Character.toString(c));
                }
                player2.getMain().remove_caracter(toExStr);

                // Pioche d'abord les nouvelles lettres
                player2.getMain().tirage(toExchange.size());

                // Remet ensuite les lettres défaussées dans le sac
                for (Character c : toExchange) {
                    model_.get_rand().add(c);
                }

                System.out.println("IA Scarbelle a optimisé sa main en échangeant " + toExchange.size() + " lettres.");
                consecutivePasses++;
            } else {
                System.out.println("IA Scarbelle n'a pas trouvé d'échange optimal et passe son tour.");
                consecutivePasses++;
            }
        } else {
            System.out.println("IA Scarbelle a passé son tour (sac vide ou aucun coup possible).");
            consecutivePasses++;
        }

        scoreboard.updateRemainingTiles(model_.get_rand().size());

        if (consecutivePasses >= 6 || (consecutivePasses >= 3 && model_.get_rand().size() < 7)) {
            triggerEndGame("3 passes consécutives avec sac insuffisant.");
            return;
        }

        // Redonner la main au Joueur 1
        player1.setCanPlay(true);
        player2.setCanPlay(false);
        scoreboard.updateTurn("Tour : Joueur 1");
    }

    public void placeMoveOnBoard(int startX, int startY, boolean isHorizontal, String word) {
        for (int i = 0; i < word.length(); i++) {
            int r = isHorizontal ? startX : startX + i;
            int c = isHorizontal ? startY + i : startY;
            char letter = Character.toUpperCase(word.charAt(i));

            map_.setLetter(r, c, letter);

            grid_panel_[r][c].removeAll();
            Main.setPanel(grid_panel_[r][c], letter);
            grid_panel_[r][c].updateUI();
        }
    }

    private JPanel createStyledCellPanel(Cell c, int i, int j) {
        JPanel ptest = new JPanel(new BorderLayout());
        ptest.setBorder(BorderFactory.createLineBorder(new Color(51, 65, 85), 1));

        Color bg;
        String text = "";
        Color fg = Color.WHITE;

        if (c.is_center(i, j)) {
            bg = new Color(190, 24, 93);
            text = "★";
            fg = new Color(251, 191, 36);
        } else if (c.is_word_triple(i, j)) {
            bg = new Color(153, 27, 27);
            text = "MOT TRIPLE";
        } else if (c.is_word_double(i, j)) {
            bg = new Color(190, 24, 93);
            text = "MOT DOUBLE";
        } else if (c.is_letter_triple(i, j)) {
            bg = new Color(30, 64, 175);
            text = "LETTRE TRIPLE";
        } else if (c.is_letter_double(i, j)) {
            bg = new Color(2, 132, 199);
            text = "LETTRE DOUBLE";
        } else {
            bg = new Color(15, 23, 42);
        }

        ptest.setBackground(bg);

        if (!text.isEmpty()) {
            JLabel lab = new JLabel(text, SwingConstants.CENTER);
            if (text.equals("★")) {
                lab.setFont(new Font("Segoe UI", Font.BOLD, 20));
            } else {
                lab.setFont(new Font("Segoe UI", Font.BOLD, 8));
            }
            lab.setForeground(fg);
            ptest.add(lab, BorderLayout.CENTER);
        }

        return ptest;
    }

    private void handleReplayGame(JFrame currentFrame) {
        int confirm = JOptionPane.showConfirmDialog(currentFrame,
                "Voulez-vous vraiment recommencer une nouvelle partie ?",
                "Rejouer la Partie", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            currentFrame.dispose();
            Model newModel = new Model();
            new Init(newModel, graph_, gameMode_);
        }
    }

    private void triggerEndGame(String reason) {
        int p1Penalty = calculateRackPenalty(player1.getMain().charac_main);
        int p2Penalty = calculateRackPenalty(player2.getMain().charac_main);

        player1.deductPoints(p1Penalty);
        player2.deductPoints(p2Penalty);

        // Règle officielle : le joueur qui a vidé son chevalet reçoit en bonus la valeur des lettres restant sur le chevalet adverse
        if (player1.getMain().charac_main.isEmpty() && !player2.getMain().charac_main.isEmpty()) {
            player1.addPoints(p2Penalty);
        } else if (player2.getMain().charac_main.isEmpty() && !player1.getMain().charac_main.isEmpty()) {
            player2.addPoints(p1Penalty);
        }

        int p1Score = player1.getPoint();
        int p2Score = player2.getPoint();

        String winner;
        if (p1Score > p2Score) winner = "Joueur 1 Victoire ! 🏆";
        else if (p2Score > p1Score) winner = (gameMode_ == GameMode.PVE ? "IA Scarbelle Victoire ! 🤖" : "Joueur 2 Victoire ! 🏆");
        else winner = "Égalité parfaite ! 🤝";

        String msg = String.format("Fin de Partie !\nRaison : %s\n-----------------------------\nScore Joueur 1 : %d pts (Pénalité main : -%d pts)\nScore Joueur 2 / IA : %d pts (Pénalité main : -%d pts)\n-----------------------------\nRésultat : %s\n\nSouhaitez-vous rejouer ?",
                reason, p1Score, p1Penalty, p2Score, p2Penalty, winner);

        int choice = JOptionPane.showOptionDialog(null, msg, "Fin de Partie - ScarbelleIA",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null,
                new String[]{"🔁 Rejouer", "❌ Quitter"}, "🔁 Rejouer");

        if (choice == JOptionPane.YES_OPTION) {
            Window[] windows = Window.getWindows();
            for (Window w : windows) {
                w.dispose();
            }
            Model newModel = new Model();
            new Init(newModel, graph_, gameMode_);
        }
    }

    private int calculateRackPenalty(ArrayList<Character> rack) {
        int penalty = 0;
        for (Character c : rack) {
            if (c != null) {
                penalty += Combineur.convert_cost(Character.toLowerCase(c));
            }
        }
        return penalty;
    }

    public ArrayList<String> convert_to_array(String s) {
        ArrayList<String> a = new ArrayList<String>();
        for (int i = 0; i < s.length(); i++) {
            a.add(Character.toString(s.charAt(i)));
        }
        return a;
    }

    public void to_conv_panel(int x, int y, String word) {
        for (int i = 0; i < word.length(); i++) {
            char to_add = word.charAt(i);
            grid_panel_[x + i][y].removeAll();
            Main.setPanel(grid_panel_[x + i][y], to_add);
            grid_panel_[x + i][y].updateUI();
        }
    }

    public ArrayList<String> conv(ArrayList<Character> l) {
        ArrayList<String> res = new ArrayList<String>();
        for (int i = 0; i < l.size(); i++) {
            res.add(Character.toString(l.get(i)));
        }
        return res;
    }
}
