package repco;

import repco.game.Reversi;
import repco.player.Computer;
import repco.player.Human;
import javax.swing.JOptionPane;
import java.awt.*;

public class Test {

    public static void main(String[] args){
        String[] type = {"Humain vs Humain", "Humain vs Ordinateur", "Ordinateur vs Ordinateur"};
        JOptionPane jop = new JOptionPane(), jop2 = new JOptionPane();
        String res = (String)jop.showInputDialog(null,
                "Selectionnez un type de Partie",
                "Reversi", JOptionPane.QUESTION_MESSAGE, null, type, type[0]);

        if (res==null){
            jop2.showMessageDialog(null, "Quelquechose s'est mal passé",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            System.exit(255);
        }
        jop2.showMessageDialog(null, "Vous avez choisi: " +
                res, "Selectionné", JOptionPane.INFORMATION_MESSAGE);


        if (res.equals("Humain vs Humain")){
            Reversi mod = new Reversi(new Human("e"), new Human("d"));

            Graphic f = new Graphic(mod);
        } else if (res.equals("Humain vs Ordinateur")){
            Reversi mod = new Reversi(new Human("e"), new Computer());

            Graphic f = new Graphic(mod);
        }else if (res.equals ("Ordinateur vs Ordinateur")){
            Reversi mod = new Reversi(new Computer(), new Computer());

            Graphic f = new Graphic(mod);
            ((Computer)mod.getPlayer()).action(mod);
        }else{
            jop2.showMessageDialog(null, "Quelquechose s'est mal passé",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }
}
