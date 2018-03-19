package repco;

import javafx.beans.Observable;
import repco.game.Reversi;
import repco.game.Token;
import repco.player.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

public class Graphic extends JFrame implements Observer{

    private Reversi rev;
    private int taille;
    private JPanel pan;
    private JPanel info;
    JButton jbArr[][];
    private JLabel l;

    public Graphic(Reversi mod){

        rev = mod;
        rev.addObserver(this);

        taille = rev.cote();
         jbArr = new JButton[taille][taille];
        if(taille <= 0)
            taille = 4;

        info = new JPanel();
        l = new JLabel();
        info.add(l);

        pan = new JPanel();
        pan.setLayout(new GridLayout(taille,taille));
        Token t;
        JButton b;
        for(int i=0; i < taille;++i){
            for(int j =0;j < taille;++j) {

                b = new JButton();
                b.setName(i*taille+j+"");
                t = rev.at(i,j);
                if(t.toInt().equals("2"))
                    b.setBackground(Color.WHITE);
                if(t.toInt().equals("1"))
                    b.setBackground(Color.BLACK);

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
        Reversi mod = new Reversi(new Human("Michel"), new Human("Rachel"));
        Graphic f = new Graphic(mod);

    }

    @Override
    public void update(java.util.Observable o, Object arg) {
        if (rev.getTurn()==Token.Black){
            l.setText("Tour du Joueur Noir");
        } else {
            l.setText("Tour du Joueur Blanc");
        }
        Token t;
        for(int i=0; i < taille;++i){
            for(int j =0;j < taille;++j) {
                t = rev.at(i,j);
                if(t.toInt().equals("2"))
                    jbArr[i][j].setBackground(Color.WHITE);
                if(t.toInt().equals("1"))
                    jbArr[i][j].setBackground(Color.BLACK);
            }
        }
    }
}
