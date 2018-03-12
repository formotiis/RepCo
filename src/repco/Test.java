package repco;

import repco.game.Reversi;
import repco.player.Human;

public class Test {

    public static void main(String[] args){

        Reversi r= new Reversi(new Human("Joueur1"),new Human("Joueur2"));
        r.displaymoves();

    }
}
