package repco;

import repco.game.Reversi;
import repco.player.Human;

public class Test {

    public static void main(String[] args){
        new Reversi(new Human("Joueur1"),new Human("Joueur2"));
    }
}
