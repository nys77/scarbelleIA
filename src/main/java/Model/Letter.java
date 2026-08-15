package Model;

public class Letter {

    private String url_;
    private Character value_;

    public Letter() {
    }

    public Letter(Character value) {
        this.value_ = value;
    }

    public Character get_value() {
        return value_ != null ? value_ : 'A';
    }
}
