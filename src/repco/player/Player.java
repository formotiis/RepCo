package repco.player;

import repco.game.Token;

public abstract class Player {

    private Token color;

    public abstract boolean isHuman();

    public void setColor(Token t){
        color = t;
    }
}
