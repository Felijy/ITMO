package pokemons;

import moves.PlayNice;

public class Steenee extends Bounsweet {
    public Steenee(String name, int lvl) {
        super(name, lvl);
        setStats(52, 40, 48, 40, 48, 62);
        addMove(new PlayNice());
    }
}
