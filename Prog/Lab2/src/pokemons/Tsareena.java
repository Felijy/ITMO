package pokemons;

import moves.TropKick;

public class Tsareena extends Steenee {
    public Tsareena(String name, int lvl) {
        super(name, lvl);
        setStats(72, 120, 98, 50, 98, 72);
        addMove(new TropKick());
    }
}
