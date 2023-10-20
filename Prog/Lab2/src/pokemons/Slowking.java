package pokemons;

import moves.FocusBlast;
import ru.ifmo.se.pokemon.Type;

public class Slowking extends Slowpoke {
    public Slowking(String name, int lvl) {
        super(name, lvl);
        setStats(95, 75, 80, 100, 110, 30);
        addMove(new FocusBlast());
    }
}
