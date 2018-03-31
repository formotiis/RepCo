package repco;

import repco.game.Reversi;
import repco.player.Computer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EventListener;

public class ActionController implements ActionListener {

    private Reversi rev;

    public ActionController(Reversi r) {
        this.rev = r;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!rev.isFinal()) {
            if (!rev.isPlayerHuman()) {
                ((Computer) rev.getPlayer()).action(rev);
            }
        }
    }
}
