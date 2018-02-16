package repco.game;

public enum Token {
    Black,
    White,
    Empty;

    @Override
    public String toString() {
        if (this == Token.Black){
            return "*";
        } else if (this == Token.White) {
            return "o";
        } else {
            return "X";
        }
    }


    public String toInt() {
        if (this == Token.Black){
            return "1";
        } else if (this == Token.White) {
            return "2";
        } else {
            return "0";
        }
    }
}
