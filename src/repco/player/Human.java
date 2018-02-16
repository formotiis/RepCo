package repco.player;

import repco.game.GameBoard;
import repco.game.Token;
import java.util.Scanner;

public class Human extends Player {


    private Token color;
    /**
     * Constructeur de Human
     */
    public Human(Token c){
        color = c;
    }

    @Override
    public void play(GameBoard g) {
        if(g.canPlay(color)) {
            Scanner sc = new Scanner(System.in);
            System.out.println("Position x :\n");
            int x = sc.nextInt();
            System.out.println("Position y :\n");
            int y = sc.nextInt();
            boolean b = g.valide(x, y,color);
            while (!b) {
                g.play(x, y, color);
            }
        }
    }
}
