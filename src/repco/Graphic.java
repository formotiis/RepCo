package repco;

import repco.game.Reversi;
import repco.game.Token;
import repco.player.Human;

import javax.swing.*;
import java.awt.*;
import java.util.Observer;

public class Graphic extends JFrame implements Observer{

    private Reversi rev;
    private int taille;
    private JPanel pan;
    private JPanel info;
    JButton jbArr[][];
    private JLabel turn;
    private JLabel scB;
    private JLabel scW;

    public Graphic(Reversi mod){

        rev = mod;
        rev.addObserver(this);

        taille = rev.cote();
         jbArr = new JButton[taille][taille];
        if(taille <= 0)
            taille = 4;

        info = new JPanel();
        turn = new JLabel();
        scW = new JLabel();
        scB = new JLabel();
        info.add(turn);
        info.add(scB);
        info.add(scW);



        pan = new JPanel();
        pan.setLayout(new GridLayout(taille,taille));
        Token t;
        JButton b;
        for(int i=0; i < taille;++i){
            for(int j =0;j < taille;++j) {

                b = new JButton();
                b.setName(i*taille+j+"");
                t = rev.at(i,j);
                b.setBackground(Color.LIGHT_GRAY);
                if(t.toInt().equals("2")) {
                    b.setBackground(Color.WHITE);
                    b.setEnabled(false);
                }
                if(t.toInt().equals("1")){
                    b.setEnabled(false);
                    b.setBackground(Color.BLACK);
                }


                b.addActionListener(new ControlleurCase(rev));

                pan.add(b);
                jbArr[i][j]=b;
            }
        }
        this.add(info, BorderLayout.NORTH);
        add(pan);
        setPreferredSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        this.update(null,null);
    }

    public static void main(String[] argv){
        Reversi mod = new Reversi(new Human("s"), new Human("uh"));
        Graphic f = new Graphic(mod);

    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        if (rev.getTurnColor()==Token.Black){
            turn.setText("Tour du Joueur Noir");
        } else {
            turn.setText("Tour du Joueur Blanc");
        }
        scB.setText("Score Noir:"+rev.score(Token.Black));
        scW.setText("Score Blanc:"+rev.score(Token.White));
        Token t;
        for(int i=0; i < taille;++i){
            for(int j =0;j < taille;++j) {
                t = rev.at(i,j);
                if(t.toInt().equals("2")) {
                    jbArr[i][j].setBackground(Color.WHITE);
                    jbArr[i][j].setEnabled(false);
                }
                if(t.toInt().equals("1")) {
                    jbArr[i][j].setBackground(Color.BLACK);
                    jbArr[i][j].setEnabled(false);
                }
            }
        }



    }
}
