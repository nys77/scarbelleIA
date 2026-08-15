package Model;

public class MoveBuilder {
    private int startX = 0;
    private int startY = 0;
    private boolean isHorizontal = true;
    private String word = "";
    private int score = 0;

    public MoveBuilder atPosition(int startX, int startY) {
        this.startX = startX;
        this.startY = startY;
        return this;
    }

    public MoveBuilder horizontally() {
        this.isHorizontal = true;
        return this;
    }

    public MoveBuilder vertically() {
        this.isHorizontal = false;
        return this;
    }

    public MoveBuilder direction(boolean isHorizontal) {
        this.isHorizontal = isHorizontal;
        return this;
    }

    public MoveBuilder withWord(String word) {
        this.word = word != null ? word.toUpperCase() : "";
        return this;
    }

    public MoveBuilder withScore(int score) {
        this.score = score;
        return this;
    }

    public Move build() {
        return new Move(startX, startY, isHorizontal, word, score);
    }
}
