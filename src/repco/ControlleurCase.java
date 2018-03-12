package repco;

import repco.game.Reversi;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlleurCase implements ActionListener {

    private Reversi mod;

    public ControlleurCase(Reversi p){
        mod = p;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        JButton jb = (JButton)(e.getSource());
        int y =Integer.parseInt(jb.getName())%mod.cote();
        int x =Integer.parseInt(jb.getName())/mod.cote();
        if(mod.isActionPossible(x,y)){
            if(mod.getPlayer() == 1)
                jb.setBackground(Color.WHITE);
            else jb.setBackground(Color.BLACK);
            //TODO : update les toutes les cases concernées
            mod.play(x,y);
        }
    }
}
