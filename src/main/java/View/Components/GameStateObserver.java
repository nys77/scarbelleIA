package View.Components;

public interface GameStateObserver {
    void onScoreUpdated(int player1Score, int player2Score);
    void onRemainingTilesChanged(int remainingCount);
    void onTurnChanged(String activePlayerName);
}
