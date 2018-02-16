package repco.game;

import repco.player.Player;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Reversi {

    private Player first;
    private Player second;

    private GameBoard gb;

    public Reversi(Player next, Player sec) {
        this.first = next; //Premier Joueur et Noirs
        this.second = sec;//Second Joueur et Blancs.
    }


    private void nextPlayerTurn() {
        Player tmp = first;
        this.first = second;
        this.second = first;
    }

    public boolean equals(Reversi r) {
        boolean b = false;
        if (this.gb.equals(r.gb)) {
            b = true;
        }
        return b;
    }

    public void readState(String name) {
        FileReader flot;
        File file = new File(name);
        BufferedReader flotFiltre;
        try {
            flot = new FileReader(name);
            flotFiltre = new BufferedReader(flot);
            Scanner input = new Scanner(flotFiltre);
            while (input.hasNextInt()) {
                input.nextInt();
            }
        } catch (IOException e) {
        }
    }


    public void writeState(){
            FileWriter flot ;
            PrintWriter flotFiltre ;
            int size = gb.getSize();
            String name = "output.txt";
            try {
                flot = new FileWriter(name) ;
                flotFiltre = new PrintWriter(new BufferedWriter(flot)) ;
                flotFiltre.println(size);
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        flotFiltre.println(gb.at(i, j).toInt());
                    }
                }
                //TODO: Ajouter le prochain a jouer, type de joueur ?
                flotFiltre.close() ;
            } catch (IOException e)
            {}

    }

}
