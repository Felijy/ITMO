package pokemons;

import moves.MagicalLeaf;
import moves.TeeterDance;
import ru.ifmo.se.pokemon.Pokemon;
import ru.ifmo.se.pokemon.Type;


public class Bounsweet extends Pokemon {
    public Bounsweet (String name, int lvl) {
        super(name, lvl);
        setType(Type.GRASS);
        setStats(42, 30, 38, 30, 38, 32);
        setMove(new TeeterDance(), new MagicalLeaf());
    }
}
