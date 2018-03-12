package repco.game;

import repco.player.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Reversi {

    private Player first;
    private Player second;
    private int turn;

    private GameBoard gb;

    public Reversi(Player next, Player sec) {
        this.turn = 0;
        this.gb = new GameBoard(8);
        this.first = next; //Premier Joueur et Noirs
        this.second = sec;//Second Joueur et Blancs.
    }


    private void nextPlayerTurn() {
        turn ++;
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
            this.gb = new GameBoard(input.nextInt());
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

    /**
     * Place un pion de la bonne couleur, a la position donnée en parametre
     * @param x
     * @param y
     */
    public void play(int x, int y){
        if (turn%2 == 0){
            gb.place(x, y, Token.Black);
        } else {
            gb.place(x, y, Token.White);
        }
        nextPlayerTurn();
    }

    public ArrayList<Integer> moveList(){
        ArrayList<Integer> al= new ArrayList<>();

        for(int i =0; i<gb.getSize();i++){
            for (int j=0;j<gb.getSize();j++){
                if (isActionPossible(i,j)){
                    al.add(i);al.add(j);
                }
            }
        }

        return al;
    }

    //TODO: Temp
    public void displaymoves(){
        ArrayList<Integer> al = moveList();
        System.out.println(gb);
        System.out.println("Turn:"+turn+", size"+al.size());
        if (turn%2 == 0){
            System.out.println(Token.Black);
        } else {
            System.out.println(Token.White);
        }
        for (int i=0; i<al.size();i+=2){
            System.out.println("Option "+((i/2)+1)+": "+al.get(i)+", "+al.get(i+1));
        }
    }

    public boolean isActionPossible(int x, int y){
        boolean b =false;
        if (spreadCheck(x,y)){
            b= true;
        }
        return b;
    }

    public boolean spreadCheck(int x, int y) {
        boolean b = false;
        Token t = gb.at(x, y);
        if (t == Token.Empty) {
            if (turn % 2 == 0) {
                t = Token.Black;
            } else {
                t = Token.White;
            }
        }

        int chainLength;
        boolean c;
        /* directions */
        // up
        c = true;
        chainLength = 0;
        for (int i = x; ((i > 0) && c); i--) {
            if (!(i == x)) {
                if (gb.at(i, y) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, y) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // down

        c = true;
        chainLength = 0;
        for (int i = x; ((i < gb.getSize()) && c); i++) {
            if (!(i == x)) {
                if (gb.at(i, y) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, y) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }


        // left

        c = true;
        chainLength = 0;
        for (int i = y; ((i > 0) && c); i--) {
            if (!(i == y)) {
                if (gb.at(x, i) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(x, i) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // right

        c = true;
        chainLength = 0;
        for (int i = y; ((i < gb.getSize()) && c); i++) {
            if (!(i == y)) {
                if (gb.at(x, i) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(x, i) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // Left up

        for (int i = x, j = y; ((i > 0) && (j > 0) && c); i--, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // left-down

        for (int i = x, j = y; ((i < gb.getSize()) && (j > 0) && c); i++, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // right-up

        for (int i = x, j = y; ((i > 0) && (j < gb.getSize()) && c); i--, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // right-down
        for (int i = x, j = y; ((i < gb.getSize()) && (j < gb.getSize()) && c); i++, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }
        return b;
    }

    /**
     * Fonction retournant les pions autour d'un pion placé
     */
    public void spread(int x, int y){
        boolean b = false;
        Token t = gb.at(x, y);
        if (t == Token.Empty) {
            if (turn % 2 == 0) {
                t = Token.Black;
            } else {
                t = Token.White;
            }
        }

        int chainLength;
        boolean c;
        /* directions */
        // up
        c = true;
        chainLength = 0;
        for (int i = x; ((i > 0) && c); i--) {
            if (!(i == x)) {
                if (gb.at(i, y) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, y) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        if (b){
            for (int i = x; ((i > 0) && c); i--) {
                if (!(i == x)) {
                    if (gb.at(i, y) == t.opposit()) {
                        //TODO:UPDATE
                    } else if (gb.at(i, y) == t) {
                        if (chainLength > 0) {
                            b = true;
                            c = false;
                        }
                    } else {
                        c = false;
                    }
                }
            }
        }
        // down

        c = true;
        chainLength = 0;
        for (int i = x; ((i < gb.getSize()) && c); i++) {
            if (!(i == x)) {
                if (gb.at(i, y) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, y) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }


        // left

        c = true;
        chainLength = 0;
        for (int i = y; ((i > 0) && c); i--) {
            if (!(i == y)) {
                if (gb.at(x, i) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(x, i) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // right

        c = true;
        chainLength = 0;
        for (int i = y; ((i < gb.getSize()) && c); i++) {
            if (!(i == y)) {
                if (gb.at(x, i) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(x, i) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // Left up

        for (int i = x, j = y; ((i > 0) && (j > 0) && c); i--, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // left-down

        for (int i = x, j = y; ((i < gb.getSize()) && (j > 0) && c); i++, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // right-up

        for (int i = x, j = y; ((i > 0) && (j < gb.getSize()) && c); i--, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }

        // right-down
        for (int i = x, j = y; ((i < gb.getSize()) && (j < gb.getSize()) && c); i++, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                } else {
                    c = false;
                }
            }
        }
    }


}
