package repco.game;

import java.util.Arrays;

public class GameBoard {

    private Token[][] board;
    int size;


    /**
     * Constructeur Du Plateau de Jeu
     * @param s longeur et largeur du plateau (6, 8 et 10 recommandé)
     */
    public GameBoard(int s){
        this.size =s;
        boardInit();
    }

    /**
     * Initialisation du tableau du plateau
     */
    private void boardInit(){
        this.board = new Token[size][size];
        for(int i=0; i<size;i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = Token.Empty;
            }
        }
        int demi = board.length/2;
        board[demi][demi] = Token.Black;
        board[demi-1][demi-1] = Token.Black;
        board[demi-1][demi] = Token.White;
        board[demi][demi-1] = Token.White;
    }

    public boolean equals(GameBoard gb){
        boolean b= true;

        if (gb.size!=size) {
            b = false;
        }else {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] != gb.board[i][j]) {
                        b = false;
                    }
                }
            }
        }
        return b;
    }

    public Token at(int i, int j){
        return board[i][j];
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GameBoard: \n");
        for(int i=0; i<size;i++) {
            for (int j = 0; j < size; j++) {
                sb.append("--");
            }
            sb.append("\n");
            for (int j = 0; j < size; j++) {
                sb.append("|").append(board[i][j].toString());
            }
            sb.append("|\n");
        }

        return sb.toString();
    }

    public static void main(String[] args){
        GameBoard s = new GameBoard(8);
        System.out.println(s.toString());
    }

}
