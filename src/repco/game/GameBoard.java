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

    /**
     * joue le token c à l'emplacement [a][b]
     * @param a
     * @param b
     * @param c
     */
    public void play(int a, int b, Token c){
        board[a][b] = c;
        //a completer avec le changement d'etat des autres token
    }

    /**
     * verifie si le coup passé en parametre [a][b] pour la couleur c est valide
     * @param a
     * @param b
     * @param c
     * @return
     */
    public boolean valide(int a, int b, Token c){
        boolean res = false;
        int temp;

        if(a >= 0 && b >= 0 && a < board.length && b < board[0].length){
            if(board[a][b] == Token.Empty){
                // a completer avec test finaux
            }
        }

        return res;
    }

    /**
     * methode pour verifier qu'il existe des coups possible pour la couleur c
     * @param c
     * @return
     */
    public boolean canPlay(Token c){
        boolean res = false;
        for(int i = 0;i< board.length;i++){
            for(int j = 0;j< board.length;j++){
                //test a completer
            }
        }
        return res;
    }

}
