package repco.player;

public class Human extends Player {
   private String name;

    public Human(String name) {
        this.name = name;
    }

    @Override
    public boolean isHuman() {
        return true;
    }
}
