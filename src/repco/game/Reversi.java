package repco.game;

import repco.player.Player;

public class Reversi {

    private Player first;
    private Player second;

    public Reversi(Player next, Player sec) {
        this.first = next; //Premier Joueur et Noirs
        this.second = sec;//Second Joueur et Blancs.
    }




    private void nextPlayerTurn(){
        Player tmp = first;
        this.first = second;
        this.second = first;
    }

}
