package repco.game;

import java.util.Arrays;

public class GameBoard {


    private Token[][] board;

    public GameBoard(int Size){

    }

    private void boardInit(int s){
        this.board = new Token[s][s];
        for(int i=0; i<s;i++) {
            for (int j = 0; j < s; j++) {
                board[i][j] = Token.Empty;
            }
        }
        board[board.length/2][board.length/2] = Token.Black;
    }

    @Override
    public String toString() {
        return "GameBoard{" +
                "board=" + Arrays.toString(board) +
                '}';
    }

    public static void main(String argv){
        GameBoard s = new GameBoard(8);
        System.out.println(s);
    }

}
