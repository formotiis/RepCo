package repco.game;

import org.omg.CORBA.INTERNAL;
import org.omg.PortableInterceptor.INACTIVE;
import repco.player.Computer;
import repco.player.Player;

import javax.swing.*;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Scanner;

public class Reversi extends Observable {

    private int turn;

    private Player black;
    private Player white;

    private GameBoard gb;

    public Reversi(Player f, Player s) {
        this.turn = 0;
        this.gb = new GameBoard(8);
        this.black = f;
        f.setColor(Token.Black);
        this.white = s;
        s.setColor(Token.White);
    }


    public Token at(int x, int y){
        return gb.at(x,y);
    }

    public int cote(){
        return  gb.getSize();
    }
    private void nextPlayerTurn() {
        turn ++;
    }

    public boolean isPlayerHuman(){
        if (getTurnColor()==Token.Black){
            return black.isHuman();
        } else {
            return white.isHuman();
        }
    }

    public Player getPlayer(){
        if (getTurnColor()==Token.Black){
            return black;
        } else {
            return white;
        }
    }

    public int getTurnID(){
        return turn%2;
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
        if (isActionPossible(x,y)) {
            gb.place(x, y, getTurnColor());
            spread(x,y);
            nextPlayerTurn();
            updated();
            if (!isPlayerHuman()){
                ((Computer)getPlayer()).action(this);
            }
        }
    }

    public boolean skipTurn(){
        if (moveList().size()==0){
            nextPlayerTurn();
            updated();
            return true;
        }
        return false;
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

    //TODO: Fix placements deux cases de la même couleur sans autre lien
    public boolean isActionPossible(int x, int y){
        boolean b =false;
        if (spreadCheck(x,y)&&(at(x,y)==Token.Empty)){
            b= true;
        }
        return b;
    }

    public boolean spreadCheck(int x, int y) {
        boolean b = false;
        Token t = gb.at(x, y);

        //recuperation joueur en cour
        if (t == Token.Empty) {
            if (turn % 2 == 0) {
                t = Token.Black;
            } else {
                t = Token.White;
            }
        }

        //test case click valide
        boolean hasFriends= false;
        if (x+1<gb.getSize()){
            if ((!(at(x+1,y)==Token.Empty))&&!hasFriends)
                hasFriends = true;
        }
        if (y+1<gb.getSize()){
            if ((!(at(x,y+1)==Token.Empty))&&!hasFriends)
                hasFriends = true;
        }
        if (y-1>=0){
            if ((!(at(x,y-1)==Token.Empty))&&!hasFriends)
                hasFriends = true;
        }
        if (x-1>=0){
            if ((!(at(x-1,y)==Token.Empty))&&!hasFriends)
                hasFriends = true;
        }
        if (!hasFriends){
            return hasFriends;
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
                    else c = false;
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
                    else c = false;
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
                    else c = false;
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
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        // Left up
        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i > 0) && (j > 0) && c); i--, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        // left-down

        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i < gb.getSize()) && (j > 0) && c); i++, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        // right-up

        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i > 0) && (j < gb.getSize()) && c); i--, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        // right-down
        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i < gb.getSize()) && (j < gb.getSize()) && c); i++, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
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
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        c = true;
        if (b){
            for (int i = x; ((i > 0) && c); i--) {
                if (!(i == x)) {
                    if (gb.at(i, y) == t.opposit()) {
                        gb.set(i,y, t);
                    } else if (gb.at(i, y) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
        b= false;
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
                    else c = false;
                } else {
                    c = false;
                }
            }
        }
        c = true;
        if (b){
            for (int i = x; ((i < gb.getSize()) && c); i++) {
                if (!(i == x)) {
                    if (gb.at(i, y) == t.opposit()) {
                        gb.set(i,y, t);
                    } else if (gb.at(i, y) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
        b = false;

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
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        c = true;
        if (b){
            for (int i = y; ((i > 0) && c); i--) {
                if (!(i == y)) {
                    if (gb.at(x, i) == t.opposit()) {
                        gb.set(x,i, t);
                    } else if (gb.at(x, i) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
        b = false;

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
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        c = true;
        if (b){
            for (int i = y; ((i < gb.getSize()) && c); i++) {
                if (!(i == y)) {
                    if (gb.at(x, i) == t.opposit()) {
                        gb.set(x,i, t);
                    } else if (gb.at(x, i) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
        b = false;


        // Left up
        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i > 0) && (j > 0) && c); i--, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        c = true;
        if (b){
            for (int i = x, j = y; ((i > 0) && (j > 0) && c); i--, j--) {
                if (!(i == x)) {
                    if (gb.at(i, j) == t.opposit()) {
                        gb.set(i,j, t);
                    } else if (gb.at(i, j) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
        b = false;

        // left-down
        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i < gb.getSize()) && (j > 0) && c); i++, j--) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        c = true;
        if (b){
            for (int i = x, j = y; ((i < gb.getSize()) && (j > 0) && c); i++, j--) {
                if (!(i == x)) {
                    if (gb.at(i, j) == t.opposit()) {
                        gb.set(i,j, t);
                    } else if (gb.at(i, j) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
        b = false;




        // right-up
        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i > 0) && (j < gb.getSize()) && c); i--, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        c = true;
        if (b){
            for (int i = x, j = y; ((i > 0) && (j < gb.getSize()) && c); i--, j++) {
                if (!(i == x)) {
                    if (gb.at(i, j) == t.opposit()) {
                        gb.set(i,j, t);
                    } else if (gb.at(i, j) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
        b = false;


        // right-down
        c = true;
        chainLength = 0;
        for (int i = x, j = y; ((i < gb.getSize()) && (j < gb.getSize()) && c); i++, j++) {
            if (!(i == x)) {
                if (gb.at(i, j) == t.opposit()) {
                    chainLength += 1;
                } else if (gb.at(i, j) == t) {
                    if (chainLength > 0) {
                        b = true;
                        c = false;
                    }
                    else c = false;
                } else {
                    c = false;
                }
            }
        }

        c = true;
        if (b){
            for (int i = x, j = y; ((i < gb.getSize()) && (j < gb.getSize()) && c); i++, j++) {
                if (!(i == x)) {
                    if (gb.at(i, j) == t.opposit()) {
                        gb.set(i,j, t);
                    } else if (gb.at(i, j) == t) {
                        c= false;
                    } else {
                        c = false;
                    }
                }
            }
        }
    }

    public Token getTurnColor(){
        Token t;
        if (turn % 2 == 0) {
            t = Token.Black;
        } else {
            t = Token.White;
        }
        return t;
    }

    public boolean isFinal(){
        boolean b = false;
        if (moveList().size()==0){
            turn+=1;
            b= moveList().size()==0;
            turn-=1;
        }
        return b;
    }

    public int score(Token color){
        int s=0;
        for(int i=0; i<gb.getSize();i++) {
            for (int j = 0; j < gb.getSize(); j++) {
                if (color.equals(at(i,j)))
                    s+=1;
            }
        }
        return s;
    }



    private void updated(){
        setChanged();
        notifyObservers();
    }

    public int getTour(){
        return turn;
    }

    public boolean equals(Reversi rever){
        boolean res;

        //test numero tour et couleur joueur courant
        if((rever.getTurnColor() == this.getTurnColor()) && (this.getTour() == rever.getTour())){
            res = true;
        }
        else res = false;

        res = res && this.gb.equals(rever.getGameBoard());

        return res;
    }

    public GameBoard getGameBoard() {
        return gb;
    }

    public ArrayList<Reversi> generateNext(){

        ArrayList<Reversi> res = new ArrayList<>();

        int k = 0;
        ArrayList<Integer> ar = this.moveList();

        for(int i =0; i < ar.size();i+=2) {
            try {
                res.add((Reversi) this.clone());
                res.get(k).play(i,i+1);
                k++;
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public Reversi Minimax(int c, Token k){
        Reversi res = this; //TODO: ajouter passage de tour
        ArrayList<Reversi> rever = generateNext();

        //minimum
        int score = Integer.MIN_VALUE;
        int tmp;

        for(int i =0;i < rever.size();i++){
            tmp = eval(c,rever.get(i),k);
            if(tmp >= score){
                score = tmp;
                res = rever.get(i);
            }
        }

        return res;
    }

    private int eval(int c, Reversi r, Token k){
        int res = 0;

        int score_max = Integer.MIN_VALUE;
        int score_min = Integer.MAX_VALUE;


        if(r.isFinal()){
            if(score(k) == score(k.opposit())) return 0;
            if(score(k) > score(k.opposit())) return Integer.MAX_VALUE;
            if(score(k) < score(k.opposit())) return Integer.MIN_VALUE;
        }

        if(c == 0)
            return eval0(r,k);

        ArrayList<Reversi> rever = r.generateNext();

        if(r.getTurnColor() == k){
            for(int g = 0; g < rever.size();g++){
                score_max = Integer.max(score_max,eval(c-1,rever.get(g),k));
            }
            return score_max;
        }
        else{
            for(int h = 0;h < rever.size();h++){
                score_min = Integer.min(score_min,eval(c-1,rever.get(h),k));
            }
            return score_min;
        }
    }

    public int eval0(Reversi r, Token k) {
        int res = r.score(k) - r.score(k.opposit());
        return res;
    }

    public Reversi MinimaxAB(int c, Token k){
        Reversi res = this; //TODO: ajouter passage de tour
        ArrayList<Reversi> rever = generateNext();

        //minimum
        int score = Integer.MIN_VALUE;
        int tmp;

        for(int i =0;i < rever.size();i++) {
            tmp = evalAB(c, rever.get(i), k,Integer.MAX_VALUE, Integer.MIN_VALUE);
            if (tmp >= score) {
                score = tmp;
                res = rever.get(i);
            }
        }

        return res;
    }

    public int evalAB(int c, Reversi r, Token k, int a, int b){

        int score_max;
        int score_min;

        if(r.isFinal()){
            if(score(k) == score(k.opposit())) return 0;
            if(score(k) > score(k.opposit())) return Integer.MAX_VALUE;
            if(score(k) < score(k.opposit())) return Integer.MIN_VALUE;
        }

        if(c == 0)
            return eval0(r,k);

        ArrayList<Reversi> rever = r.generateNext();

        if(r.getTurnColor() == k){
            score_max = Integer.MIN_VALUE;
            for(int g = 0; g < rever.size();g++){
                score_max = Integer.max(score_max,evalAB(c-1,rever.get(g),k,a,b));
                if(score_max >= b)
                    return score_max;
                a = Integer.max(a,score_max);
            }
            return score_max;
        }
        else{
            score_min = Integer.MAX_VALUE;
            for(int h = 0;h < rever.size();h++){
                score_min = Integer.min(score_min,evalAB(c-1,rever.get(h),k,a,b));
                if(score_min <= a)
                    return score_min;
                b = Integer.min(b,score_min);
            }
            return score_min;
        }
    }
}
