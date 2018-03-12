package repco;

import repco.game.Reversi;
import repco.game.Token;
import repco.player.Human;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Graphic extends JFrame{

    private Reversi rev;
    private int taille;
    private JPanel pan;

    public Graphic(Reversi mod){

        rev = mod;

        taille = rev.cote();

        if(taille <= 0)
            taille = 4;

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
            }
        }
        add(pan);
        setPreferredSize(new Dimension(800,600));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    public static void main(String[] argv){
        Reversi mod = new Reversi(new Human("Michel"), new Human("Rachel"));
        Graphic f = new Graphic(mod);

    }
}
