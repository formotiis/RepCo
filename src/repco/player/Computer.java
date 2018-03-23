package repco.player;

import repco.game.Reversi;

import java.util.ArrayList;
import java.util.Random;

public class Computer extends Player{

    public Computer() {
    }

    @Override
    public boolean isHuman() {
        return false;
    }

    public void action(Reversi mod){
        Random r = new Random();
        ArrayList<Integer> a = mod.moveList();
        if (!mod.skipTurn()){
            int s= 0;
            mod.play(a.get(s), a.get(s+1));
        }

    }


}
