package repco.game;

import repco.player.Human;
import repco.player.Player;

public class Reversi {

    private Player first;
    private Player second;
    private Player joueurCourant;
    private GameBoard g;


    /**
     * Initialisation standard d'une partie à 2 joueurs
     */
    public Reversi() {
        first = new Human(Token.Black);
        second = new Human(Token.White);
        joueurCourant = first;
        g = new GameBoard(8);
    }




    private void play(){
        joueurCourant.play(g);
        joueurCourant = next();
    }

    private Player next(){
        if(first.equals(joueurCourant)){
            return second;
        }
        else return first;
    }

    public static void main(String[] argv){

    }

}
