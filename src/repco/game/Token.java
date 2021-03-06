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

    public String verbose() {
        if (this == Token.Black){
            return "Black";
        } else if (this == Token.White) {
            return "White";
        } else {
            return " ";
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

    public Token opposit(){
        if (this == Token.Black){
            return Token.White;
        } else if (this == Token.White) {
            return Token.Black;
        } else {
            return Token.Empty;
        }
    }
}
