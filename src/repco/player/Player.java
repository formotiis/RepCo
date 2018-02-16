package repco.player;

import repco.game.GameBoard;
import repco.game.Reversi;

public abstract class Player {

    /**
     * methode de jeux, lit
     * @param g
     * @return
     */
    public abstract void play(GameBoard g);
}
